import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-chatbox',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chatbox.component.html',
  styleUrls: ['./chatbox.component.scss'],
})
export class ChatboxComponent {
  isOpen = false;
  userInput = '';
  messages: { text: string; isUser: boolean }[] = [];

  toggleChatbox(): void {
    this.isOpen = !this.isOpen;
  }

  sendMessage(): void {
    const input = this.userInput.trim();
    if (!input) return;

    this.messages.push({ text: input, isUser: true });
    this.userInput = '';

    const botReply = this.getBotReply(input);
    setTimeout(() => {
      this.messages.push({ text: botReply, isUser: false });
    }, 600);
  }

  getBotReply(input: string): string {
    const text = input.toLowerCase();

    if (text.includes('giảm giá') || text.includes('sale')) {
      return 'Chúng tôi đang có chương trình giảm giá tới 50% cho nhiều sản phẩm nội thất như bàn ghế, giường, tủ. Bạn muốn xem sản phẩm nào?';
    } else if (text.includes('giao hàng')) {
      return 'Chúng tôi giao hàng toàn quốc. Nội thành TP.HCM miễn phí, tỉnh thành khác có hỗ trợ cước vận chuyển.';
    } else if (text.includes('tư vấn')) {
      return 'Bạn cần tư vấn về loại sản phẩm nào? Ghế, bàn, giường hay tủ? Hãy để lại thông tin để chuyên viên liên hệ.';
    } else if (text.includes('trả góp')) {
      return 'Chúng tôi hỗ trợ trả góp 0% qua thẻ tín dụng của hầu hết các ngân hàng. Áp dụng cho đơn từ 3 triệu.';
    } else if (text.includes('bảo hành')) {
      return 'Tất cả sản phẩm đều được bảo hành chính hãng 12 tháng. Một số dòng cao cấp bảo hành đến 24 tháng.';
    } else if (text.includes('màu sắc')) {
      return 'Chúng tôi có sẵn nhiều lựa chọn màu sắc cho ghế, sofa và bàn. Vui lòng nêu rõ bạn quan tâm sản phẩm nào.';
    } else if (
      text.includes('sofa') ||
      text.includes('ghế') ||
      text.includes('bàn') ||
      text.includes('tủ')
    ) {
      return `Bạn đang tìm ${
        text.includes('sofa')
          ? 'sofa'
          : text.includes('ghế')
          ? 'ghế'
          : text.includes('bàn')
          ? 'bàn'
          : 'tủ'
      }? Chúng tôi có nhiều mẫu với nhiều mức giá. Truy cập mục sản phẩm để xem chi tiết.`;
    } else if (text.includes('địa chỉ') || text.includes('mua trực tiếp')) {
      return 'Bạn có thể đến showroom của chúng tôi tại Quận 10, TP.HCM hoặc đặt hàng online tại website.';
    } else {
      return 'Cảm ơn bạn đã liên hệ. Chúng tôi sẽ phản hồi thêm trong thời gian sớm nhất. Bạn có thể hỏi về sản phẩm, bảo hành, giao hàng, trả góp hoặc chương trình khuyến mãi.';
    }
  }
}
