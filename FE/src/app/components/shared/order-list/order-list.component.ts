import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { DonHang } from '../../../core/models/donhang.model';
import { AuthService } from '../../../core/services/auth.service';
import { OrderManagerService } from '../../../core/services/order-manager.service';
import { NotificationService } from '../../../core/services/notification.service';
import { TaiKhoan } from '../../../core/models/taikhoan.model';
import { OrderDetailComponent } from '../order-detail/order-detail.component';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule, OrderDetailComponent],
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.scss'],
})
export class OrderListComponent implements OnInit, OnDestroy {
  orders: DonHang[] = [];
  filteredOrders: DonHang[] = [];
  selectedOrder: DonHang | null = null;
  showOrderDetailModal = false;
  isLoading = true;
  searchTerm = '';
  statusFilter = 'all';

  // Status options
  statusOptions = [
    { value: 'all', label: 'Tất cả' },
    { value: 'pending', label: 'Chờ xác nhận' },
    { value: 'processing', label: 'Đang xử lý' },
    { value: 'shipping', label: 'Đang giao hàng' },
    { value: 'delivered', label: 'Đã giao hàng' },
    { value: 'cancelled', label: 'Đã hủy' },
  ];

  private destroy$ = new Subject<void>();

  constructor(
    private authService: AuthService,
    private orderManagerService: OrderManagerService,
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

    // Load all orders from OrderManagerService
    this.orderManagerService
      .getAllOrders()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (orders) => {
          console.log('📋 Orders loaded:', orders);
          this.orders = orders;
          this.filteredOrders = [...orders];
          this.isLoading = false;
        },
        error: (error) => {
          console.error('❌ Error loading orders:', error);
          this.notificationService.error(
            'Lỗi',
            'Không thể tải danh sách đơn hàng'
          );
          this.isLoading = false;
        },
      });
  }

  filterOrders(): void {
    this.filteredOrders = this.orders.filter((order) => {
      const orderCode = `DH${order.id.toString().padStart(3, '0')}`;
      const customerName = order.khachhang?.hoten || '';
      const matchesSearch =
        orderCode.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        customerName.toLowerCase().includes(this.searchTerm.toLowerCase());

      let matchesStatus = true;
      if (this.statusFilter !== 'all') {
        const statusMap: { [key: string]: string } = {
          pending: 'CHO_XAC_NHAN',
          processing: 'DA_XAC_NHAN',
          shipping: 'DANG_GIAO',
          delivered: 'DA_GIAO',
          cancelled: 'DA_HUY',
        };
        matchesStatus = order.trangthaidonhang === statusMap[this.statusFilter];
      }

      return matchesSearch && matchesStatus;
    });
  }

  onSearchChange(): void {
    this.filterOrders();
  }

  onStatusFilterChange(): void {
    this.filterOrders();
  }

  getStatusLabel(status: string): string {
    return this.orderManagerService.getStatusLabel(status);
  }

  getStatusClass(status: string): string {
    return this.orderManagerService.getStatusClass(status);
  }

  getOrderCode(orderId: number): string {
    return this.orderManagerService.getOrderCode(orderId);
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(amount);
  }

  formatDate(date: Date | string): string {
    return new Date(date).toLocaleDateString('vi-VN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
    });
  }

  getPaymentMethodLabel(method: string): string {
    const methodMap: { [key: string]: string } = {
      COD: 'Thanh toán khi nhận hàng',
      BANK: 'Chuyển khoản ngân hàng',
      MOMO: 'Ví MoMo',
      VNPAY: 'VNPay',
    };
    return methodMap[method] || method;
  }

  viewOrderDetail(order: DonHang): void {
    this.selectedOrder = order;
    this.showOrderDetailModal = true;
  }

  closeOrderDetail(): void {
    this.showOrderDetailModal = false;
    this.selectedOrder = null;
  }

  reorder(order: DonHang): void {
    this.notificationService.info(
      'Thông báo',
      'Tính năng đặt lại đơn hàng đang được phát triển'
    );
  }

  cancelOrder(order: DonHang): void {
    if (
      order.trangthaidonhang === 'CHO_XAC_NHAN' ||
      order.trangthaidonhang === 'DA_XAC_NHAN'
    ) {
      this.notificationService.info(
        'Thông báo',
        'Tính năng hủy đơn hàng đang được phát triển'
      );
    } else {
      this.notificationService.warning(
        'Cảnh báo',
        'Không thể hủy đơn hàng ở trạng thái hiện tại'
      );
    }
  }

  goToShopping(): void {
    this.router.navigate(['/']);
  }

  formatPrice(price: number | undefined): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(price || 0);
  }
}
