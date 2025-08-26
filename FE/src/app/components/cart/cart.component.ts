import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { GioHangService, CartItem } from '../../core/services/giohang.service';
import { NotificationService } from '../../core/services/notification.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-cart',
  standalone: true,
  templateUrl: './cart.component.html',
  imports: [CommonModule, FormsModule],
  styleUrls: ['./cart.component.scss'],
})
export class CartComponent implements OnInit, OnDestroy {
  cartItems: CartItem[] = [];
  totalPrice = 0;
  isLoading = true;
  error: string | null = null;
  showRemoveModal = false;
  showClearCartModal = false;
  itemToRemove: CartItem | null = null;
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
    if (item.soluong >= 99) {
      this.notificationService.warning('Cảnh báo', 'Số lượng tối đa là 99');
      return;
    }

    const newQuantity = item.soluong + 1;
    this.updateItemQuantity(item, newQuantity);
  }

  decreaseQuantity(item: CartItem): void {
    if (item.soluong <= 1) {
      this.notificationService.warning(
        'Cảnh báo',
        'Số lượng tối thiểu là 1. Sử dụng nút xóa để xóa sản phẩm.'
      );
      return;
    }

    const newQuantity = item.soluong - 1;
    this.updateItemQuantity(item, newQuantity);
  }

  private updateItemQuantity(item: CartItem, newQuantity: number): void {
    try {
      this.gioHangService.updateQuantity(item.chitietsanphamid, newQuantity);
      // Update local item immediately for better UX
      item.soluong = newQuantity;
      this.updateTotalPrice();
    } catch (error) {
      console.error('Error updating quantity:', error);
      this.notificationService.error(
        'Lỗi',
        'Không thể cập nhật số lượng sản phẩm'
      );
    }
  }

  removeItem(item: CartItem): void {
    this.itemToRemove = item;
    this.showRemoveModal = true;
  }

  confirmRemoveItem(): void {
    if (this.itemToRemove) {
      try {
        this.gioHangService.removeFromCart(this.itemToRemove.chitietsanphamid);
        this.notificationService.success(
          'Đã xóa sản phẩm',
          `${this.itemToRemove.tensanpham} đã được xóa khỏi giỏ hàng`
        );
      } catch (error) {
        console.error('Error removing item:', error);
        this.notificationService.error(
          'Lỗi',
          'Không thể xóa sản phẩm khỏi giỏ hàng'
        );
      }
    }
    this.closeRemoveModal();
  }

  closeRemoveModal(): void {
    this.showRemoveModal = false;
    this.itemToRemove = null;
  }

  clearCart(): void {
    if (this.cartItems.length === 0) {
      this.notificationService.warning(
        'Giỏ hàng trống',
        'Không có sản phẩm nào để xóa'
      );
      return;
    }

    this.showClearCartModal = true;
  }

  confirmClearCart(): void {
    try {
      this.gioHangService.clearCart();
      // No need to manually reload as we're subscribed to cartItems$
      this.notificationService.success(
        'Đã xóa giỏ hàng',
        'Toàn bộ sản phẩm đã được xóa khỏi giỏ hàng'
      );
    } catch (error) {
      console.error('Error clearing cart:', error);
      this.notificationService.error(
        'Lỗi',
        'Không thể xóa giỏ hàng. Vui lòng thử lại.'
      );
    } finally {
      this.closeClearCartModal();
    }
  }

  closeClearCartModal(): void {
    this.showClearCartModal = false;
  }

  onQuantityChange(item: CartItem, event: any): void {
    const input = event.target;
    const newQuantity = parseInt(input.value, 10);
    
    // Validate input
    if (isNaN(newQuantity) || newQuantity < 1) {
      input.value = item.soluong; // Reset to current value
      this.notificationService.warning('Cảnh báo', 'Số lượng phải là số nguyên dương');
      return;
    }
    
    if (newQuantity > 99) {
      input.value = 99;
      this.notificationService.warning('Cảnh báo', 'Số lượng tối đa là 99');
      this.updateItemQuantity(item, 99);
      return;
    }

    if (newQuantity !== item.soluong) {
      this.updateItemQuantity(item, newQuantity);
    }
  }

  onEnterKey(event: Event): void {
    const target = event.target as HTMLInputElement;
    if (target) {
      target.blur();
    }
  }

  validateQuantity(item: CartItem): void {
    // Ensure quantity is within valid range on blur
    if (item.soluong < 1) {
      this.updateItemQuantity(item, 1);
      this.notificationService.warning('Cảnh báo', 'Số lượng tối thiểu là 1');
    } else if (item.soluong > 99) {
      this.updateItemQuantity(item, 99);
      this.notificationService.warning('Cảnh báo', 'Số lượng tối đa là 99');
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
    return item.hinhchinh || 'assets/img/default-product.jpg';
  }

  trackByItem(index: number, item: CartItem): number {
    return item.chitietsanphamid;
  }

  // Utility methods
  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(amount);
  }

  getTotalItems(): number {
    return this.cartItems.reduce((sum, item) => sum + item.soluong, 0);
  }

  getItemSubtotal(item: CartItem): number {
    return (item.dongia || 0) * (item.soluong || 0);
  }

  isCartEmpty(): boolean {
    return this.cartItems.length === 0;
  }

  hasItems(): boolean {
    return this.cartItems.length > 0;
  }
}
