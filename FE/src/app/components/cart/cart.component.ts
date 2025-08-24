import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { GioHangService, CartItem } from '../../core/services/giohang.service';
import { NotificationService } from '../../core/services/notification.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cart',
  standalone: true,
  templateUrl: './cart.component.html',
  imports: [CommonModule],
  styleUrls: ['./cart.component.scss'],
})
export class CartComponent implements OnInit, OnDestroy {
  cartItems: CartItem[] = [];
  totalPrice = 0;
  isLoading = true;
  private sub?: Subscription;

  constructor(
    private gioHangService: GioHangService,
    private notificationService: NotificationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.sub = this.gioHangService.cartItems$.subscribe((items) => {
      this.cartItems = items;
      this.updateTotalPrice();
      this.isLoading = false;
    });
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }

  updateTotalPrice(): void {
    this.totalPrice = this.cartItems.reduce(
      (sum, item) => sum + (item.soluong || 0) * (item.dongia || 0),
      0
    );
  }

  increaseQuantity(item: CartItem): void {
    if (item.soluong < 99) {
      this.gioHangService.updateQuantity(
        item.chitietsanphamid,
        item.soluong + 1
      );
      this.notificationService.success(
        'Thành công',
        'Đã tăng số lượng sản phẩm'
      );
    }
  }

  decreaseQuantity(item: CartItem): void {
    if (item.soluong > 1) {
      this.gioHangService.updateQuantity(
        item.chitietsanphamid,
        item.soluong - 1
      );
      this.notificationService.success(
        'Thành công',
        'Đã giảm số lượng sản phẩm'
      );
    } else {
      this.removeItem(item);
    }
  }

  removeItem(item: CartItem): void {
    if (confirm('Bạn có chắc muốn xóa sản phẩm này khỏi giỏ hàng?')) {
      this.gioHangService.removeFromCart(item.chitietsanphamid);
      this.notificationService.success(
        'Đã xóa sản phẩm',
        'Sản phẩm đã được xóa khỏi giỏ hàng'
      );
    }
  }

  clearCart(): void {
    if (confirm('Bạn có chắc muốn xóa toàn bộ giỏ hàng?')) {
      this.gioHangService.clearCart();
      this.notificationService.success(
        'Đã xóa giỏ hàng',
        'Toàn bộ sản phẩm đã được xóa khỏi giỏ hàng'
      );
    }
  }

  proceedToCheckout(): void {
    if (this.cartItems.length === 0) {
      this.notificationService.warning(
        'Giỏ hàng trống',
        'Vui lòng thêm sản phẩm vào giỏ hàng trước khi thanh toán'
      );
      return;
    }
    this.router.navigate(['/checkout']);
  }

  continueShopping(): void {
    this.router.navigate(['/']);
  }

  getProductImage(item: CartItem): string {
    return item.hinhchinh || '/assets/img/default-product.jpg';
  }

  trackByItem(index: number, item: CartItem): number {
    return item.chitietsanphamid;
  }
}
