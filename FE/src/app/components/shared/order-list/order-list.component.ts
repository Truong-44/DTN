import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { DonHang } from '../../../core/models/donhang.model';
import { AuthService } from '../../../core/services/auth.service';
import { DonHangService } from '../../../core/services/donhang.service';
import { NotificationService } from '../../../core/services/notification.service';
import { TaiKhoan } from '../../../core/models/taikhoan.model';
import { OrderDetailComponent } from '../order-detail/order-detail.component';
import { ConfirmDialogComponent } from '../../../shared/components/confirm-dialog/confirm-dialog.component';
import { Subject } from 'rxjs';
import { switchMap, takeUntil, filter } from 'rxjs/operators';

@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    OrderDetailComponent,
    ConfirmDialogComponent,
  ],
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.scss'],
})
export class OrderListComponent implements OnInit, OnDestroy {
  orders: DonHang[] = [];
  selectedOrder: DonHang | null = null;
  showOrderDetailModal = false;
  isLoading = true;

  // Confirm dialog state
  showConfirmDialog = false;
  orderToCancel: DonHang | null = null;

  private destroy$ = new Subject<void>();

  constructor(
    private authService: AuthService,
    private donHangService: DonHangService,
    private notificationService: NotificationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadOrders();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadOrders(): void {
    this.isLoading = true;

    const currentUser = this.authService.getCurrentUser();
    if (!currentUser || !currentUser.khachhang) {
      console.error(
        '❌ [OrderList] No authenticated user or customer data found.'
      );
      this.notificationService.error(
        'Lỗi',
        'Không thể tải danh sách đơn hàng. Vui lòng đăng nhập lại.'
      );
      this.isLoading = false;
      this.orders = [];
      return;
    }

    console.log(
      '🚀 [OrderList] Loading orders for customer ID:',
      currentUser.khachhang.id
    );

    this.donHangService
      .getDonHangByKhachHangId(currentUser.khachhang.id)
      .subscribe({
        next: (response) => {
          console.log('✅ [OrderList] Orders loaded successfully:', response);
          this.orders = response.content.sort(
            (a, b) =>
              new Date(b.ngaydat).getTime() - new Date(a.ngaydat).getTime()
          );
          this.isLoading = false;
        },
        error: (err) => {
          console.error('❌ [OrderList] Error loading orders:', err);
          this.notificationService.error(
            'Lỗi',
            'Có lỗi xảy ra khi tải danh sách đơn hàng.'
          );
          this.isLoading = false;
        },
      });
  }

  viewDetail(order: DonHang): void {
    this.selectedOrder = order;
    this.showOrderDetailModal = true;
  }

  closeOrderDetail(): void {
    this.selectedOrder = null;
    this.showOrderDetailModal = false;
  }

  canCancelOrder(order: DonHang): boolean {
    const status = order.trangthaidonhang;
    // Add more cancellable statuses if needed
    return status === 'CHO_XAC_NHAN';
  }

  promptCancelOrder(order: DonHang, event?: Event): void {
    if (event) {
      event.stopPropagation();
    }

    if (!this.canCancelOrder(order)) {
      this.notificationService.info('Thông báo', 'Đơn hàng này không thể hủy.');
      return;
    }

    this.orderToCancel = order;
    this.showConfirmDialog = true;
  }

  confirmCancelOrder(): void {
    if (!this.orderToCancel || !this.orderToCancel.id) {
      return;
    }

    const orderId = this.orderToCancel.id;
    this.showConfirmDialog = false;
    this.orderToCancel = null;
    this.isLoading = true; // Bắt đầu loading

    this.donHangService
      .cancelDonHang(orderId, 'Khách hàng yêu cầu hủy')
      .subscribe({
        next: () => {
          this.notificationService.success(
            'Thành công',
            'Đơn hàng đã được hủy thành công.'
          );
          this.loadOrders(); // Tải lại danh sách, sẽ tự tắt loading khi xong
        },
        error: (err) => {
          console.error('Error canceling order:', err);
          this.notificationService.error(
            'Lỗi',
            err.error?.message || 'Hủy đơn hàng thất bại. Vui lòng thử lại.'
          );
          this.isLoading = false; // Tắt loading khi có lỗi
        },
      });
  }

  cancelDialog(): void {
    this.showConfirmDialog = false;
    this.orderToCancel = null;
  }

  getPhoneNumber(order: DonHang): string {
    return order.khachhang?.taikhoan?.sodienthoai || 'N/A';
  }

  getPaymentLabel(method: string | undefined): string {
    switch (method) {
      case 'COD':
        return 'Thanh toán khi nhận hàng';
      case 'BANK':
        return 'Chuyển khoản ngân hàng';
      case 'MOMO':
        return 'Ví MoMo';
      case 'VNPAY':
        return 'VNPay';
      default:
        return method || '';
    }
  }

  getOrderStatusLabel(status: string): string {
    switch (status) {
      case 'CHO_XAC_NHAN':
        return 'Chờ xác nhận';
      case 'DANG_GIAO':
        return 'Đang giao';
      case 'DA_GIAO':
        return 'Đã giao';
      case 'DA_HUY':
        return 'Đã hủy';
      default:
        return status;
    }
  }

  formatCurrency(amount: number | undefined): string {
    return new Intl.NumberFormat('vi-VN').format(amount ?? 0) + 'đ';
  }

  formatDate(date: string | Date): string {
    const dateObj = new Date(date);
    return dateObj.toLocaleDateString('vi-VN', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'CHO_XAC_NHAN':
        return 'pending';
      case 'DA_XAC_NHAN':
        return 'confirmed';
      case 'DANG_GIAO':
        return 'shipping';
      case 'DA_GIAO':
        return 'delivered';
      case 'DA_HUY':
        return 'cancelled';
      default:
        return 'pending';
    }
  }

  goToProducts(): void {
    this.router.navigate(['/']);
  }
}
