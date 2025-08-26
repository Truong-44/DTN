import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormsModule,
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { GioHangService, CartItem } from '../../core/services/giohang.service';
import { NotificationService } from '../../core/services/notification.service';
import { AuthService } from '../../core/services/auth.service';
import {
  OrderManagerService,
  CreateOrderRequest,
} from '../../core/services/order-manager.service';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss'],
})
export class CheckoutComponent implements OnInit, OnDestroy {
  checkoutForm: FormGroup;
  cartItems: CartItem[] = [];
  totalPrice = 0;
  isLoading = false;
  currentStep = 1;
  maxSteps = 3;

  private cartSub?: Subscription;

  paymentMethods = [
    { id: 'cod', name: 'Thanh toán khi nhận hàng (COD)', icon: '💵' },
    { id: 'banking', name: 'Chuyển khoản ngân hàng', icon: '🏦' },
    { id: 'momo', name: 'Ví MoMo', icon: '📱' },
    { id: 'zalopay', name: 'ZaloPay', icon: '⚡' },
  ];

  shippingMethods = [
    {
      id: 'standard',
      name: 'Giao hàng tiêu chuẩn',
      price: 30000,
      duration: '3-5 ngày',
    },
    {
      id: 'express',
      name: 'Giao hàng nhanh',
      price: 50000,
      duration: '1-2 ngày',
    },
    {
      id: 'same-day',
      name: 'Giao hàng trong ngày',
      price: 80000,
      duration: 'Trong ngày',
    },
  ];

  constructor(
    private fb: FormBuilder,
    private gioHangService: GioHangService,
    private authService: AuthService,
    private notificationService: NotificationService,
    private router: Router,
    private orderManagerService: OrderManagerService
  ) {
    this.checkoutForm = this.createForm();
  }

  ngOnInit(): void {
    this.cartSub = this.gioHangService.cartItems$.subscribe((items) => {
      this.cartItems = items;
      this.calculateTotal();

      if (items.length === 0) {
        this.notificationService.warning(
          'Giỏ hàng trống',
          'Vui lòng thêm sản phẩm vào giỏ hàng'
        );
        this.router.navigate(['/']);
      }
    });

    this.loadUserInfo();
  }

  ngOnDestroy(): void {
    this.cartSub?.unsubscribe();
  }

  private createForm(): FormGroup {
    return this.fb.group({
      // Thông tin người nhận
      fullName: ['', [Validators.required, Validators.minLength(2)]],
      phone: ['', [Validators.required, Validators.pattern(/^[0-9]{10,11}$/)]],
      email: ['', [Validators.required, Validators.email]],

      // Địa chỉ giao hàng
      address: ['', [Validators.required, Validators.minLength(20)]],

      // Phương thức thanh toán và giao hàng
      paymentMethod: ['cod', Validators.required],
      shippingMethod: ['standard', Validators.required],

      // Ghi chú
      note: [''],
    });
  }

  private loadUserInfo(): void {
    const user = this.authService.getCurrentUser();
    if (user) {
      this.checkoutForm.patchValue({
        fullName: user.khachhang?.hoten || '',
        phone: user.sodienthoai || '',
        email: user.email || '',
      });
    }
  }

  private calculateTotal(): void {
    const subtotal = this.cartItems.reduce(
      (sum, item) => sum + item.soluong * item.dongia,
      0
    );

    const shippingMethod = this.shippingMethods.find(
      (method) => method.id === this.checkoutForm.get('shippingMethod')?.value
    );

    const shippingCost = shippingMethod?.price || 0;
    this.totalPrice = subtotal + shippingCost;
  }

  onShippingMethodChange(): void {
    this.calculateTotal();
  }

  nextStep(): void {
    if (this.currentStep < this.maxSteps) {
      if (this.validateCurrentStep()) {
        this.currentStep++;
      }
    }
  }

  prevStep(): void {
    if (this.currentStep > 1) {
      this.currentStep--;
    }
  }

  private validateCurrentStep(): boolean {
    switch (this.currentStep) {
      case 1:
        return !!(
          this.checkoutForm.get('fullName')?.valid &&
          this.checkoutForm.get('phone')?.valid &&
          this.checkoutForm.get('email')?.valid
        );
      case 2:
        return !!(
          this.checkoutForm.get('address')?.valid
        );
      default:
        return true;
    }
  }

  submitOrder(): void {
    if (this.checkoutForm.valid && this.cartItems.length > 0) {
      this.isLoading = true;

      const formValues = this.checkoutForm.value;

      // Prepare order request for OrderManagerService
      const orderRequest: CreateOrderRequest = {
        diachinhan: formValues.address,
        phuongthucthanhtoan: this.mapPaymentMethod(formValues.paymentMethod),
        customerName: formValues.fullName,
        customerPhone: formValues.phone,
        ghichu: formValues.notes || '',
        chitietdonhang: this.cartItems.map((item) => ({
          chitietsanphamid: item.chitietsanphamid,
          soluong: item.soluong,
          dongia: item.dongia,
          productDetail: {
            id: item.chitietsanphamid,
            sanphamId: item.chitietsanphamid, // Use chitietsanphamid as fallback
            tensanpham: item.tensanpham,
            tenmau: item.tenmau,
            soluong: item.soluong,
            hinhchinh: item.hinhchinh,
            hinhphu: undefined,
            mamau: undefined,
            chatlieu: undefined,
            kichthuoc: undefined,
            trongluong: undefined,
          },
        })),
      };

      console.log('🛒 Submitting checkout order:', orderRequest);

      this.orderManagerService.createOrder(orderRequest).subscribe({
        next: (newOrder) => {
          console.log('✅ Checkout order created successfully:', newOrder);
          this.isLoading = false;

          this.notificationService.success(
            'Đặt hàng thành công!',
            'Đơn hàng của bạn đã được ghi nhận. Chúng tôi sẽ liên hệ với bạn sớm nhất.'
          );

          // Simulate order progress for demo
          this.orderManagerService.simulateOrderProgress(newOrder.id);

          // Clear cart after successful order
          setTimeout(() => {
            this.gioHangService.clearCart();
            console.log('🛒 Cart cleared after successful order');
          }, 500);

          // Navigate to order list after a short delay
          setTimeout(() => {
            this.router.navigate(['/order-list']);
          }, 1500);
        },
        error: (err) => {
          console.error('❌ Error creating checkout order:', err);
          this.isLoading = false;
          this.notificationService.error(
            'Lỗi đặt hàng',
            'Có lỗi xảy ra khi đặt hàng. Vui lòng thử lại.'
          );
        },
      });
    } else {
      this.notificationService.error(
        'Thông tin không hợp lệ',
        'Vui lòng kiểm tra lại thông tin đặt hàng'
      );
    }
  }

  // Helper method to map payment method
  private mapPaymentMethod(method: string): string {
    const methodMap: { [key: string]: string } = {
      cod: 'COD',
      banking: 'BANK',
      momo: 'MOMO',
      zalopay: 'VNPAY',
    };
    return methodMap[method] || 'COD';
  }

  backToCart(): void {
    this.router.navigate(['/cart']);
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(amount);
  }

  getSubtotal(): number {
    return this.cartItems.reduce(
      (sum, item) => sum + item.soluong * item.dongia,
      0
    );
  }

  getShippingCost(): number {
    const shippingMethod = this.shippingMethods.find(
      (method) => method.id === this.checkoutForm.get('shippingMethod')?.value
    );
    return shippingMethod?.price || 0;
  }

  getFieldError(fieldName: string): string {
    const field = this.checkoutForm.get(fieldName);
    if (field?.errors && field.touched) {
      if (field.errors['required']) return `${fieldName} là bắt buộc`;
      if (field.errors['email']) return 'Email không hợp lệ';
      if (field.errors['pattern']) return 'Số điện thoại không hợp lệ';
      if (field.errors['minlength']) return `${fieldName} quá ngắn`;
    }
    return '';
  }
}
