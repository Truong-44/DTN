import {
  Component,
  OnInit,
  OnDestroy,
  ViewChild,
  ElementRef,
  AfterViewChecked,
  ChangeDetectorRef,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';
import { ChatService } from '../../core/services/chat.service';
import { SanPhamService } from '../../core/services/sanpham.service';
import {
  ChatSession,
  ChatMessage,
  BotSuggestion,
  ProductSuggestionResponse,
} from '../../core/models/chat.model';

interface QuickAction {
  text: string;
  message: string;
  icon: string;
}

@Component({
  selector: 'app-chatbot',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chatbot.component.html',
  styleUrls: ['./chatbot.component.scss'],
})
export class ChatbotComponent implements OnInit, OnDestroy, AfterViewChecked {
  @ViewChild('messagesContainer', { static: false })
  messagesContainer!: ElementRef<HTMLDivElement>;

  @ViewChild('messageInput', { static: false })
  messageInput!: ElementRef<HTMLTextAreaElement>;

  // ===================== STATE MANAGEMENT =====================
  isOpen = false;
  isMinimized = false;
  isLoading = false;
  isTyping = false;
  isInitialized = false;
  hasScrolledToBottom = false;

  // ===================== CHAT DATA =====================
  currentSession: ChatSession | null = null;
  messages: ChatMessage[] = [];
  newMessage = '';
  suggestions: BotSuggestion[] = [];
  productSuggestions: ProductSuggestionResponse[] = [];

  // ===================== SUBSCRIPTIONS =====================
  private subscriptions: Subscription[] = [];

  // ===================== CONFIGURATION =====================
  private readonly MAX_MESSAGE_LENGTH = 1000;
  private readonly MAX_RETRIES = 3;
  private retryCount = 0;

  // ===================== QUICK ACTIONS =====================
  quickActions: QuickAction[] = [
    {
      text: 'Sản phẩm hot',
      message: 'Cho tôi xem những sản phẩm bán chạy nhất',
      icon: '🔥',
    },
    {
      text: 'Tư vấn thiết kế',
      message: 'Tôi cần tư vấn thiết kế nội thất cho phòng khách',
      icon: '💡',
    },
    {
      text: 'Khuyến mãi',
      message: 'Hiện tại có chương trình khuyến mãi gì không?',
      icon: '🎁',
    },
    {
      text: 'Liên hệ & đặt hàng',
      message: 'Hướng dẫn tôi cách đặt hàng và liên hệ',
      icon: '📞',
    },
  ];

  constructor(
    private chatService: ChatService,
    private sanPhamService: SanPhamService,
    private cdr: ChangeDetectorRef
  ) {
    this.initializeComponent();
  }

  ngOnInit(): void {
    this.loadInitialData();
  }

  ngOnDestroy(): void {
    this.cleanup();
  }

  ngAfterViewChecked(): void {
    this.handleAutoScroll();
  }

  // ===================== INITIALIZATION =====================
  private initializeComponent(): void {
    this.messages = [];
    this.suggestions = [];
    this.productSuggestions = [];
    this.retryCount = 0;
  }

  private loadInitialData(): void {
    // No subscriptions needed for local mode
  }

  private cleanup(): void {
    // Simple cleanup for local mode
    this.subscriptions.forEach((sub) => sub?.unsubscribe());
    this.subscriptions = [];
  }

  // ===================== CHAT INITIALIZATION =====================
  initializeChat(): void {
    if (this.isInitialized || this.isLoading) return;

    this.isLoading = true;

    // Create local session immediately
    this.createLocalSession();
  }

  private createLocalSession(): void {
    const localSession: ChatSession = {
      id: Date.now(),
      sessionType: 'GUEST',
      status: 'ACTIVE',
      createdAt: new Date(),
    };

    this.currentSession = localSession;
    this.addWelcomeMessage();
    this.isInitialized = true;
    this.isLoading = false;

    console.log('Local chat session created:', localSession.id);
  }

  // ===================== UI CONTROLS =====================
  toggleChat(): void {
    this.isOpen = !this.isOpen;

    if (this.isOpen) {
      this.isMinimized = false;
      if (!this.isInitialized) {
        this.initializeChat();
      }
      this.focusInput();
    }
  }

  minimizeChat(): void {
    this.isMinimized = true;
  }

  closeChat(): void {
    this.isOpen = false;
    this.isMinimized = false;
  }

  private focusInput(): void {
    setTimeout(() => {
      if (this.messageInput?.nativeElement) {
        this.messageInput.nativeElement.focus();
      }
    }, 100);
  }

  // ===================== MESSAGE HANDLING =====================
  sendMessage(): void {
    if (!this.canSendMessage || this.isLoading) return;

    const messageContent = this.newMessage.trim();
    if (messageContent.length > this.MAX_MESSAGE_LENGTH) {
      this.addErrorMessage(
        `Tin nhắn không được vượt quá ${this.MAX_MESSAGE_LENGTH} ký tự.`
      );
      return;
    }

    // Add user message immediately
    const userMessage: ChatMessage = {
      id: Date.now(),
      sessionId: this.currentSession!.id!,
      content: messageContent,
      messageType: 'USER',
      timestamp: new Date(),
    };

    this.messages.push(userMessage);

    // Clear input and set loading state
    this.newMessage = '';
    this.isLoading = true;

    // Generate local bot response
    this.generateLocalBotResponse(messageContent);
  }

  private generateLocalBotResponse(userInput: string): void {
    this.isTyping = true;
    const response = this.getSmartLocalResponse(userInput);

    setTimeout(() => {
      const botMessage: ChatMessage = {
        id: Date.now() + 1,
        sessionId: this.currentSession!.id!,
        content: response,
        messageType: 'BOT',
        timestamp: new Date(),
      };

      this.messages.push(botMessage);
      this.isLoading = false;
      this.isTyping = false;

      // Check for product-related queries
      if (this.isProductQuery(userInput)) {
        this.getProductSuggestions(userInput);
      }

      this.cdr.detectChanges();
    }, 1000 + Math.random() * 1000); // Realistic typing delay
  }

  private getSmartLocalResponse(userInput: string): string {
    const input = userInput.toLowerCase();

    // Thông tin liên hệ và shop
    if (
      input.includes('liên hệ') ||
      input.includes('địa chỉ') ||
      input.includes('phone') ||
      input.includes('số điện thoại') ||
      input.includes('hotline')
    ) {
      return `📞 **THÔNG TIN LIÊN HỆ DTN FURNITURE**

🏪 **Cửa hàng chính:**
📍 123 Nguyễn Văn Cừ, Quận 5, TP.HCM
🕐 Giờ mở cửa: 8:00 - 22:00 (Thứ 2 - Chủ nhật)

📱 **Hotline:** 1900-1234 (Miễn phí)
📧 **Email:** info@dtnfurniture.com
🌐 **Website:** www.dtnfurniture.com
📘 **Facebook:** DTN Furniture Official

💬 **Zalo/WhatsApp:** 0901-234-567
🚗 **Showroom 2:** 456 Lê Văn Việt, Quận 9, TP.HCM

Bạn muốn được tư vấn trực tiếp qua kênh nào?`;
    }

    if (input.includes('email') || input.includes('mail')) {
      return `📧 **THÔNG TIN EMAIL DTN FURNITURE**

✉️ **Email chính:** info@dtnfurniture.com
💼 **Tư vấn bán hàng:** sales@dtnfurniture.com  
🛠️ **Hỗ trợ kỹ thuật:** support@dtnfurniture.com
📦 **Giao hàng:** delivery@dtnfurniture.com

⏰ **Thời gian phản hồi:** Trong 2-4 giờ
📝 **Hoặc liên hệ trực tiếp:** 1900-1234

Bạn cần hỗ trợ gì cụ thể để tôi chuyển đúng bộ phận?`;
    }

    // Greeting responses
    if (
      input.includes('chào') ||
      input.includes('hello') ||
      input.includes('hi')
    ) {
      return `🏠 **Xin chào! Chào mừng đến DTN Furniture!**

Tôi là AI Assistant, sẵn sàng hỗ trợ bạn 24/7! 

🎯 **Tôi có thể giúp bạn:**
• 🔍 Tìm sản phẩm theo tên/giá
• 💰 Tư vấn giá tốt nhất  
• 📞 Thông tin liên hệ shop
• 🚚 Chính sách giao hàng
• 🛡️ Bảo hành và hỗ trợ

**🏷️ Hãy thử:** "Tìm sofa dưới 5 triệu" hoặc "Thông tin liên hệ"`;
    }

    // Tìm sản phẩm theo giá
    if (
      input.includes('dưới') ||
      input.includes('under') ||
      input.includes('giá rẻ') ||
      input.includes('rẻ nhất')
    ) {
      let priceRange = '';
      if (input.includes('1 triệu') || input.includes('1tr'))
        priceRange = 'dưới 1 triệu';
      else if (input.includes('2 triệu') || input.includes('2tr'))
        priceRange = 'dưới 2 triệu';
      else if (input.includes('3 triệu') || input.includes('3tr'))
        priceRange = 'dưới 3 triệu';
      else if (input.includes('5 triệu') || input.includes('5tr'))
        priceRange = 'dưới 5 triệu';
      else if (input.includes('10 triệu') || input.includes('10tr'))
        priceRange = 'dưới 10 triệu';
      else priceRange = 'giá tốt';

      return `💰 **SẢN PHẨM ${priceRange.toUpperCase()}**

🏷️ **Gợi ý sản phẩm hot:**

📺 **Kệ TV gỗ MDF:** 1.2 - 2.5 triệu
🪑 **Bàn học sinh viên:** 800k - 1.5 triệu  
🛋️ **Ghế sofa mini:** 1.5 - 3 triệu
🗄️ **Tủ quần áo 2 cánh:** 2 - 4 triệu
🛏️ **Giường ngủ đơn:** 1.8 - 3.5 triệu

🎁 **KHUYẾN MÃI:** Giảm 15% + Freeship
📞 **Gọi ngay:** 1900-1234 để được tư vấn chi tiết!

Bạn quan tâm loại nào cụ thể?`;
    }

    // Tìm sản phẩm theo tên
    if (
      input.includes('tìm') ||
      input.includes('có') ||
      input.includes('bán')
    ) {
      if (input.includes('sofa') || input.includes('ghế sofa')) {
        return `🛋️ **BỘ SƯU TẬP SOFA DTN**

✨ **Sofa da thật cao cấp:**
• Sofa da Italia 3+2+1: 25-35 triệu
• Sofa da Hàn Quốc: 15-25 triệu

🏡 **Sofa vải hiện đại:**  
• Sofa vải Hàn Quốc: 8-15 triệu
• Sofa góc L-shape: 12-20 triệu
• Sofa giường đa năng: 6-12 triệu

💎 **Sofa mini/1 người:**
• Ghế thư giãn: 2-5 triệu
• Sofa đơn Scandinavian: 3-7 triệu

🎁 **ƯU ĐÃI:** Free giao hàng + Bảo hành 3 năm
📞 **Tư vấn:** 1900-1234`;
      }

      if (input.includes('bàn')) {
        return `🪑 **BỘ SƯU TẬP BÀN DTN**

🍽️ **Bàn ăn gia đình:**
• Bàn ăn gỗ sồi 6 ghế: 8-15 triệu
• Bàn ăn mặt kính: 5-10 triệu
• Bàn ăn thông minh gấp gọn: 3-7 triệu

💻 **Bàn làm việc:**
• Bàn office hiện đại: 1.5-4 triệu  
• Bàn gaming RGB: 3-8 triệu
• Bàn học sinh: 800k-2 triệu

☕ **Bàn trang trí:**
• Bàn coffee gỗ tự nhiên: 2-6 triệu
• Bàn trà sofa: 1-3 triệu

🎁 **COMBO:** Bàn + Ghế giảm 20%`;
      }

      if (input.includes('tủ')) {
        return `🗄️ **HỆ THỐNG TỦ KỆ DTN**

👔 **Tủ quần áo:**
• Tủ 2 cánh gỗ MDF: 2-4 triệu
• Tủ 3 cánh có gương: 4-8 triệu  
• Tủ âm tường cao cấp: 8-15 triệu

📺 **Tủ TV & Kệ sách:**
• Kệ TV gỗ hiện đại: 1.2-3 triệu
• Tủ sách 5 tầng: 1.5-4 triệu
• Combo TV + tủ: 3-8 triệu

👠 **Tủ giày thông minh:**
• Tủ giày 2 tầng: 800k-1.5 triệu
• Tủ giày 4 tầng: 1.2-2.5 triệu

🎁 **QUÀ TẶNG:** Miễn phí lắp đặt tại nhà`;
      }

      if (input.includes('giường')) {
        return `🛏️ **BỘ GIƯỜNG NGỦ CAO CẤP**

👨‍👩‍👧‍👦 **Giường đôi (1m6-1m8):**
• Giường gỗ sồi tự nhiên: 8-15 triệu
• Giường MDF phủ melamine: 3-7 triệu
• Giường có hộc tủ: 5-10 triệu

🧒 **Giường đơn (1m2):**
• Giường trẻ em: 1.5-3 triệu
• Giường tầng 2 tầng: 4-8 triệu
• Giường sinh viên: 1.8-3.5 triệu

✨ **Nệm cao cấp:**
• Nệm foam độc lập: 2-5 triệu  
• Nệm lò xo Bonnell: 1.5-4 triệu

📞 **Hotline:** 1900-1234 để xem trực tiếp`;
      }

      return `🔍 **TÌM KIẾM SẢN PHẨM**

Tôi có thể giúp bạn tìm:
• 🛋️ **Sofa & Ghế thư giãn**
• 🪑 **Bàn ăn & Bàn làm việc**  
• 🗄️ **Tủ quần áo & Kệ TV**
• 🛏️ **Giường ngủ & Nệm**
• 💡 **Đèn trang trí**
• 🏺 **Phụ kiện decor**

💰 **Theo giá:** "Tìm sofa dưới 5 triệu"
📝 **Theo tên:** "Có bàn ăn gỗ sồi không?"

Bạn đang tìm sản phẩm gì cụ thể?`;
    }

    // Product category responses
    if (input.includes('sofa') || input.includes('ghế')) {
      return `🛋️ **SOFA & GHẾ THƯƠNG HIỆU DTN**

💎 **Dòng cao cấp (15-35 triệu):**
• Sofa da Italia Toscana
• Sofa da Hàn Quốc Premium  
• Sofa góc chữ L + Ottoman

🏡 **Dòng gia đình (6-15 triệu):**
• Sofa vải Hàn Quốc 3+2+1
• Sofa giường đa năng
• Sofa góc hiện đại

💼 **Dòng văn phòng (2-8 triệu):**
• Ghế giám đốc massage
• Sofa văn phòng 3 chỗ
• Ghế thư giãn đọc sách

🎁 **KHUYẾN MÃI T8:** Giảm 20% + Free ship + Bảo hành 3 năm
📱 **Đặt ngay:** 1900-1234`;
    }

    // Khuyến mãi
    if (
      input.includes('khuyến mãi') ||
      input.includes('giảm giá') ||
      input.includes('sale') ||
      input.includes('ưu đãi')
    ) {
      return `🎁 **CHƯƠNG TRÌNH KHUYẾN MÃI THÁNG 8**

🔥 **SALE CỰC SỐC:**
• 🛋️ **Sofa:** Giảm 25% + Tặng bàn coffee
• 🪑 **Bàn ăn:** Giảm 20% + Tặng 4 ghế
• 🗄️ **Tủ quần áo:** Giảm 30% + Free lắp đặt
• 🛏️ **Giường ngủ:** Giảm 15% + Tặng nệm

💎 **COMBO SIÊU TIẾT KIỆM:**
🏠 **Combo phòng khách:** Sofa + Bàn + Kệ TV = Giảm 35%
🛏️ **Combo phòng ngủ:** Giường + Tủ + Bàn trang điểm = Giảm 30%
🍽️ **Combo phòng ăn:** Bàn ăn + 6 ghế + Tủ rượu = Giảm 28%

🎯 **ƯU ĐÃI ĐẶC BIỆT:**
✅ Freeship toàn quốc  
✅ Trả góp 0% trong 12 tháng
✅ Tặng voucher 500k cho lần mua sau

⏰ **Thời hạn:** Đến hết 31/8/2025
📞 **Đặt hàng:** 1900-1234`;
    }

    // Price inquiries
    if (
      input.includes('giá') ||
      input.includes('bao nhiêu') ||
      input.includes('cost')
    ) {
      return `💰 **BẢNG GIÁ DTN FURNITURE 2024**

🛋️ **Sofa:** 1.5 triệu - 35 triệu
🪑 **Bàn ghế:** 800k - 15 triệu  
🗄️ **Tủ kệ:** 1.2 triệu - 15 triệu
🛏️ **Giường ngủ:** 1.8 triệu - 15 triệu
💡 **Đèn trang trí:** 200k - 3 triệu

💳 **THANH TOÁN:**
• Tiền mặt: Giảm thêm 3%
• Trả góp 0%: 6-12 tháng
• Chuyển khoản: Giảm 2%

🎁 **KHUYẾN MÃI HIỆN TẠI:**
✅ Giảm 15-25% toàn bộ sản phẩm
✅ Freeship nội thành  
✅ Tặng phụ kiện trang trí

**Bạn quan tâm sản phẩm nào để tôi báo giá chi tiết?**`;
    }

    // Thanks
    if (input.includes('cảm ơn') || input.includes('thanks')) {
      return `😊 **Cảm ơn bạn đã tin tương DTN Furniture!**

🎯 **Chúng tôi cam kết:**
✅ Sản phẩm chất lượng cao
✅ Giá cả cạnh tranh nhất  
✅ Dịch vụ tận tâm 24/7
✅ Bảo hành uy tín lâu dài

📞 **Liên hệ bất cứ khi nào:**
• **Hotline:** 1900-1234
• **Zalo:** 0901-234-567  
• **Email:** info@dtnfurniture.com

**🏠 DTN Furniture - Kiến tạo ngôi nhà trong mơ!**
Hẹn gặp bạn tại showroom! 🤝`;
    }

    // Default response for unrecognized input
    return `🤔 Tôi hiểu bạn đang quan tâm đến "${userInput}". 

🎯 **TÔI CÓ THỂ HỖ TRỢ:**
• 📞 **Thông tin liên hệ** - "Số điện thoại shop"
• 🔍 **Tìm sản phẩm** - "Tìm sofa dưới 5 triệu"  
• 💰 **Bảng giá** - "Giá bàn ăn"
• 🎁 **Khuyến mãi** - "Có giảm giá không?"
• 🚚 **Giao hàng** - "Ship đến Hà Nội"
• 🛡️ **Bảo hành** - "Bảo hành bao lâu?"

💬 **Thử hỏi:** 
"Có ghế sofa da thật không?"
"Tủ quần áo 3 cánh giá bao nhiêu?"
"Địa chỉ showroom ở đâu?"

**📞 Hotline:** 1900-1234 | **🏪 Showroom:** 123 Nguyễn Văn Cừ, Q5`;
  }

  // ===================== SUGGESTIONS =====================
  getProductSuggestions(query: string): void {
    // Simple local product suggestions
    this.productSuggestions = [];
    console.log('Product search for:', query);
  }

  selectSuggestion(suggestion: BotSuggestion): void {
    if (!suggestion?.content) return;
    this.newMessage = suggestion.content;
    this.sendMessage();
  }

  // ===================== QUICK ACTIONS =====================
  handleQuickAction(action: QuickAction): void {
    if (!action?.message) return;
    this.newMessage = action.message;
    this.sendMessage();
  }

  // ===================== UTILITY METHODS =====================
  private addWelcomeMessage(): void {
    if (!this.currentSession?.id) return;

    const welcomeMessage: ChatMessage = {
      id: Date.now(),
      sessionId: this.currentSession.id,
      content: `🏠 **Chào mừng đến với DTN Furniture!**

Tôi là **AI Assistant** thông minh, sẵn sàng hỗ trợ bạn 24/7! 

**🎯 Tôi có thể giúp bạn:**
• 🔍 **Tìm kiếm sản phẩm** theo nhu cầu
• 💡 **Tư vấn thiết kế** nội thất  
• 💰 **Thông tin giá cả** và khuyến mãi
• 🚚 **Hướng dẫn mua hàng** và giao hàng
• 🛡️ **Chính sách bảo hành** và hỗ trợ

**🏷️ SẢN PHẨM HOT:**
Sofa • Bàn ghế • Tủ kệ • Giường ngủ • Đèn trang trí

Hãy cho tôi biết bạn đang tìm gì nhé! 😊`,
      messageType: 'BOT',
      timestamp: new Date(),
    };

    this.messages.push(welcomeMessage);
  }

  private addErrorMessage(errorText: string): void {
    if (!this.currentSession?.id) return;

    const errorMessage: ChatMessage = {
      id: Date.now(),
      sessionId: this.currentSession.id,
      content: `⚠️ ${errorText}`,
      messageType: 'BOT',
      timestamp: new Date(),
    };

    this.messages.push(errorMessage);
  }

  private isProductQuery(input: string): boolean {
    if (!input) return false;
    const productKeywords = [
      'sản phẩm',
      'tìm',
      'mua',
      'giá',
      'bàn',
      'ghế',
      'tủ',
      'giường',
      'sofa',
      'nội thất',
      'kệ',
      'đèn',
    ];
    const lowerInput = input.toLowerCase();
    return productKeywords.some((keyword) => lowerInput.includes(keyword));
  }

  private handleAutoScroll(): void {
    if (this.isOpen && !this.isMinimized && this.hasMessages) {
      this.scrollToBottom();
    }
  }

  private scrollToBottom(): void {
    try {
      if (this.messagesContainer?.nativeElement) {
        const element = this.messagesContainer.nativeElement;
        setTimeout(() => {
          element.scrollTop = element.scrollHeight;
          this.hasScrolledToBottom = true;
        }, 100);
      }
    } catch (error) {
      console.warn('Scroll error:', error);
    }
  }

  // ===================== EVENT HANDLERS =====================
  onKeyPress(event: KeyboardEvent): void {
    if (event.key === 'Enter' && !event.shiftKey) {
      event.preventDefault();
      this.sendMessage();
    }
  }

  onInputFocus(): void {
    this.hasScrolledToBottom = false;
    setTimeout(() => this.scrollToBottom(), 100);
  }

  // ===================== COMPUTED PROPERTIES =====================
  get hasMessages(): boolean {
    return Array.isArray(this.messages) && this.messages.length > 0;
  }

  get canSendMessage(): boolean {
    return (
      this.newMessage?.trim().length > 0 &&
      this.newMessage.trim().length <= this.MAX_MESSAGE_LENGTH &&
      !this.isLoading &&
      !this.isTyping &&
      this.currentSession !== null &&
      this.isInitialized
    );
  }

  get sessionStatus(): string {
    if (!this.currentSession) return 'Chưa kết nối';
    if (this.isLoading) return 'Đang kết nối...';
    return this.currentSession.status === 'ACTIVE'
      ? 'Đang hoạt động'
      : 'Đã kết thúc';
  }

  // ===================== FORMATTERS =====================
  formatTimestamp(timestamp: Date | undefined): string {
    if (!timestamp) return '';

    try {
      const date = new Date(timestamp);
      const now = new Date();
      const diffMs = now.getTime() - date.getTime();
      const diffMinutes = Math.floor(diffMs / 60000);

      if (diffMinutes < 1) return 'Vừa xong';
      if (diffMinutes < 60) return `${diffMinutes} phút trước`;

      const diffHours = Math.floor(diffMinutes / 60);
      if (diffHours < 24) return `${diffHours} giờ trước`;

      return date.toLocaleDateString('vi-VN', {
        day: '2-digit',
        month: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
      });
    } catch (error) {
      return '';
    }
  }

  formatPrice(price: number): string {
    try {
      return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
      }).format(price || 0);
    } catch (error) {
      return '0 VND';
    }
  }

  formatMessageContent(content: string): string {
    if (!content) return '';

    try {
      return content
        .replace(/\n/g, '<br>')
        .replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
        .replace(/\*(.*?)\*/g, '<em>$1</em>');
    } catch (error) {
      return content;
    }
  }

  // ===================== TRACK BY FUNCTIONS =====================
  trackByMessageId(index: number, message: ChatMessage): any {
    return message?.id || `${message?.timestamp}-${index}`;
  }

  trackByActionText(index: number, action: QuickAction): string {
    return action?.text || index.toString();
  }

  // ===================== VISIBILITY CONTROL =====================
  shouldShowChatBot(): boolean {
    try {
      const currentRoute = window.location.pathname;
      const hiddenRoutes = [
        '/admin',
        '/login',
        '/register',
        '/checkout/payment',
        '/dashboard',
      ];
      return !hiddenRoutes.some((route) => currentRoute.startsWith(route));
    } catch (error) {
      return true;
    }
  }
}
