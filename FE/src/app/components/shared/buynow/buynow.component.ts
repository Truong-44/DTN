import {
  Component,
  Input,
  Output,
  EventEmitter,
  OnInit,
  OnDestroy,
  OnChanges,
  SimpleChanges,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ChiTietSanPham } from '../../../core/models/chitietsanpham.model';
import { KhachHang } from '../../../core/models/khachhang.model';
import { DonHang } from '../../../core/models/donhang.model';
import { NotificationService } from '../../../core/services/notification.service';
import {
  OrderManagerService,
  CreateOrderRequest,
} from '../../../core/services/order-manager.service';
import { AuthService } from '../../../core/services/auth.service';
import { SanPham } from '../../../core/models/sanpham.model';
import { TaiKhoan } from '../../../core/models/taikhoan.model';
import { Subject, of } from 'rxjs';
import {
  takeUntil,
  switchMap,
  catchError,
  tap,
  filter,
  take,
  finalize,
} from 'rxjs/operators';

@Component({
  selector: 'app-buynow',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './buynow.component.html',
  styleUrls: ['./buynow.component.scss'],
})
export class BuynowComponent implements OnInit, OnDestroy, OnChanges {
  @Input() isVisible = true;
  @Input() productDetail: ChiTietSanPham | null = null;
  @Output() closeModal = new EventEmitter<void>();
  @Output() orderCompleted = new EventEmitter<void>();

  private destroy$ = new Subject<void>();
  currentUser: TaiKhoan | null = null;
  quantity = 1;
  isLoading = false;
  paymentMethods = [
    { value: 'COD', label: 'Thanh toán khi nhận hàng' },
    { value: 'BANK', label: 'Chuyển khoản ngân hàng' },
    { value: 'MOMO', label: 'Ví MoMo' },
    { value: 'VNPAY', label: 'VNPay' },
  ];

  customer: KhachHang = {
    id: 0,
    taikhoanId: 0,
    hoten: '',
    diachi: '',
  };
  customerPhone: string = '';
  diachinhan: string = '';
  phuongthucthanhtoan: string = 'COD';

  constructor(
    private notificationService: NotificationService,
    private orderManagerService: OrderManagerService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnChanges(changes: SimpleChanges) {
    if (changes['productDetail']) {
      console.log('🔄 [BuyNow] ProductDetail changed:', {
        previous: changes['productDetail'].previousValue,
        current: changes['productDetail'].currentValue
      });
      
      if (this.productDetail) {
        console.log('📊 [BuyNow] New productDetail structure:', this.productDetail);
        console.log('🏷️ [BuyNow] SanPham in productDetail:', (this.productDetail as any)?.sanpham);
        
        // Debug full structure
        this.debugProductData();
        
        // Test price calculation immediately
        const price = this.getCurrentPrice();
        console.log('💰 [BuyNow] Calculated price:', price);
        console.log('💲 [BuyNow] Formatted price:', this.formatCurrency(price));
      }
    }
  }

  debugProductData() {
    console.group('🔍 [BuyNow] Product Data Debug');
    console.log('ProductDetail:', this.productDetail);
    console.log('ProductDetail keys:', this.productDetail ? Object.keys(this.productDetail) : 'null');
    
    const sanpham = this.getSanPham();
    console.log('SanPham:', sanpham);
    console.log('SanPham keys:', sanpham ? Object.keys(sanpham) : 'null');
    
    if (sanpham) {
      console.log('GiaCu:', sanpham.giacu);
      console.log('GiaMoi:', sanpham.giamoi);
      console.log('TenSanPham:', sanpham.tensanpham);
    }
    
    // Check if price data exists directly in productDetail
    const directPrice = (this.productDetail as any);
    console.log('Direct price fields in productDetail:', {
      gia: directPrice?.gia,
      giaban: directPrice?.giaban,
      giacu: directPrice?.giacu,
      giamoi: directPrice?.giamoi
    });
    
    console.groupEnd();
  }

  ngOnInit() {
    console.log('🛒 [BuyNow] Component initializing...');
    console.log('📦 [BuyNow] Initial productDetail:', this.productDetail);
    
    this.authService.currentUser$
      .pipe(takeUntil(this.destroy$))
      .subscribe((user) => {
        console.log('🔄 [BuyNow] User updated:', user);
        this.currentUser = user;
        if (user) {
          this.customer = {
            id: user.khachhang?.id || 0,
            taikhoanId: user.id,
            hoten: user.khachhang?.hoten || user.tendangnhap,
            diachi: user.khachhang?.diachi || '',
          };
          this.customerPhone = user.sodienthoai || '';
          this.diachinhan = this.customer.diachi || '';
        } else {
          this.customer = {
            id: 0,
            taikhoanId: 0,
            hoten: '',
            diachi: '',
          };
          this.customerPhone = '';
          this.diachinhan = '';
        }
      });
    
    // Log productDetail structure
    setTimeout(() => {
      console.log('🔍 [BuyNow] ProductDetail after timeout:', this.productDetail);
      if (this.productDetail) {
        console.log('📊 [BuyNow] SanPham data:', (this.productDetail as any)?.sanpham);
        console.log('💰 [BuyNow] Current price:', this.getCurrentPrice());
      }
    }, 100);
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  // ... (keep all other methods like getProductImages, formatCurrency, etc., the same)
  getProductImages(): string[] {
    // console.log(this.productDetail?.hinhchinh);
    // console.log(this.productDetail?.hinhphu);
    if (!this.productDetail) return [];
    const images: string[] = [];
    if (this.productDetail.hinhchinh) {
      images.push('assets/img' + this.productDetail.hinhchinh);
    }
    if (this.productDetail.hinhphu) {
      images.push(
        ...this.productDetail.hinhphu
          .split(';')
          .map((img) => img.trim())
          .filter((img) => img)
          .map((img) => 'assets/img' + img)
      );
    }
    return images;
  }

  onImageError(event: Event) {
    (event.target as HTMLImageElement).src = 'assets/img/default.png';
  }

  getSanPham(): SanPham | undefined {
    console.log('🔍 [BuyNow] Getting SanPham from productDetail:', this.productDetail);
    const sanpham = (this.productDetail as any)?.sanpham;
    console.log('📊 [BuyNow] SanPham data:', sanpham);
    return sanpham;
  }

  getCurrentPrice(): number {
    const sanpham = this.getSanPham();
    console.log('💰 [BuyNow] SanPham for price calculation:', sanpham);
    
    if (!sanpham) {
      console.warn('⚠️ [BuyNow] No SanPham data found, checking productDetail directly');
      // Fallback: try to get price directly from productDetail
      const fallbackPrice = (this.productDetail as any)?.gia || (this.productDetail as any)?.giaban || 0;
      console.log('🔄 [BuyNow] Fallback price from productDetail:', fallbackPrice);
      return fallbackPrice;
    }
    
    const currentPrice = sanpham?.giamoi ?? sanpham?.giacu ?? 0;
    console.log('💵 [BuyNow] Current price:', currentPrice, 'giamoi:', sanpham?.giamoi, 'giacu:', sanpham?.giacu);
    return currentPrice;
  }

  getOldPrice(): number {
    const sanpham = this.getSanPham();
    const oldPrice = sanpham?.giacu ?? 0;
    console.log('🏷️ [BuyNow] Old price:', oldPrice);
    return oldPrice;
  }

  hasDiscount(): boolean {
    const sanpham = this.getSanPham();
    return !!(
      sanpham &&
      sanpham.giamoi !== undefined &&
      sanpham.giacu !== undefined &&
      sanpham.giamoi < sanpham.giacu
    );
  }

  getDiscountPercentage(): number {
    const sanpham = this.getSanPham();
    if (
      !this.hasDiscount() ||
      !sanpham ||
      sanpham.giacu === undefined ||
      sanpham.giamoi === undefined
    )
      return 0;
    return Math.round(((sanpham.giacu - sanpham.giamoi) / sanpham.giacu) * 100);
  }

  formatCurrency(amount: number): string {
    console.log('💲 [BuyNow] Formatting currency for amount:', amount);
    
    if (amount === null || amount === undefined || isNaN(amount)) {
      console.warn('⚠️ [BuyNow] Invalid amount for currency formatting:', amount);
      return '0đ';
    }
    
    try {
      const formatted = new Intl.NumberFormat('vi-VN').format(amount) + 'đ';
      console.log('✅ [BuyNow] Formatted currency:', formatted);
      return formatted;
    } catch (error) {
      console.error('❌ [BuyNow] Error formatting currency:', error);
      return amount.toLocaleString() + 'đ';
    }
  }

  getStockStatusMessage(): string {
    if (!this.productDetail) return '';
    if (this.productDetail.soluong && this.productDetail.soluong > 0)
      return 'Còn hàng';
    return 'Hết hàng';
  }

  isProductAvailable(): boolean {
    return !!(
      this.productDetail &&
      this.productDetail.soluong &&
      this.productDetail.soluong > 0
    );
  }

  isFormValid(): boolean {
    return !!(this.diachinhan && this.quantity > 0 && this.productDetail);
  }

  onClose() {
    this.closeModal.emit();
  }

  onSubmitOrder() {
    if (!this.isFormValid()) {
      this.notificationService.error(
        'Lỗi',
        'Vui lòng nhập đầy đủ thông tin giao hàng.'
      );
      return;
    }

    if (!this.productDetail) {
      this.notificationService.error('Lỗi', 'Thông tin sản phẩm không hợp lệ.');
      return;
    }

    this.isLoading = true;
    console.log('🚀 Starting order submission...');

    // Create order request
    const orderRequest: CreateOrderRequest = {
      khachhangid: this.currentUser?.khachhang?.id || undefined,
      diachinhan: this.diachinhan,
      phuongthucthanhtoan: this.phuongthucthanhtoan,
      customerName: this.customer.hoten || 'Khách hàng',
      customerPhone: this.customerPhone || '0000000000',
      ghichu: '',
      chitietdonhang: [
        {
          chitietsanphamid: this.productDetail.id,
          soluong: this.quantity,
          dongia: this.getCurrentPrice(),
          productDetail: this.productDetail,
        },
      ],
    };

    console.log('📦 Order request:', orderRequest);

    this.orderManagerService.createOrder(orderRequest).subscribe({
      next: (newOrder) => {
        console.log('✅ Order created successfully:', newOrder);
        this.notificationService.success('Thành công', 'Đặt hàng thành công!');

        // Simulate order progress for demo
        this.orderManagerService.simulateOrderProgress(newOrder.id);

        this.orderCompleted.emit();
        this.onClose();
        this.resetForm();
        this.isLoading = false;

        // Navigate to order list after a short delay
        setTimeout(() => {
          this.router.navigate(['/order-list']);
        }, 1500);
      },
      error: (err) => {
        console.error('❌ Error creating order:', err);
        this.notificationService.error(
          'Lỗi',
          'Có lỗi xảy ra khi đặt hàng. Vui lòng thử lại.'
        );
        this.isLoading = false;
      },
    });
  }

  getTotalPrice(): number {
    if (!this.productDetail) {
      console.warn('⚠️ [BuyNow] No productDetail for total price calculation');
      return 0;
    }
    
    const currentPrice = this.getCurrentPrice();
    const total = currentPrice * this.quantity;
    
    console.log('🧮 [BuyNow] Total price calculation:', {
      currentPrice,
      quantity: this.quantity,
      total
    });
    
    return total;
  }

  resetForm() {
    this.quantity = 1;
    this.phuongthucthanhtoan = 'COD';
    this.diachinhan = this.customer.diachi || '';
  }

  // Các hàm cho template nếu cần chỉnh sửa thông tin khách hàng
  isEditingCustomerInfo = false;
  toggleEditCustomerInfo() {
    this.isEditingCustomerInfo = !this.isEditingCustomerInfo;
  }
  cancelEditCustomerInfo() {
    this.isEditingCustomerInfo = false;
  }
  saveCustomerInfo() {
    this.isEditingCustomerInfo = false;
  }
  onEmailBlur() {}
  onPhoneInput(event: any) {}
  onPhoneBlur() {}
  decreaseQuantity() {
    if (this.quantity > 1) this.quantity--;
  }
  increaseQuantity() {
    this.quantity++;
  }
  onQuantityChange() {
    if (this.quantity < 1) this.quantity = 1;
  }
  onPaymentMethodChange(value: string) {
    this.phuongthucthanhtoan = value;
  }
  getSubmitButtonText(): string {
    if (this.isLoading) return 'Đang xử lý...';
    if (!this.isProductAvailable()) return 'Hết hàng';
    return 'Đặt hàng';
  }
}
