import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminDataService, DonHang } from '../../shared/services/admin-data.service';
import { AdminNotificationService } from '../../shared/services/admin-notification.service';
import { AdminLoadingService } from '../../shared/services/admin-loading.service';

@Component({
  selector: 'app-admin-order-detail',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.scss'],
})
export class AdminOrderDetailComponent implements OnInit {
  order: DonHang | null = null;
  selectedStatus = '';
  loading = true;

  orderStatuses = [
    { value: 'pending', label: 'Chờ xử lý' },
    { value: 'processing', label: 'Đang xử lý' },
    { value: 'shipping', label: 'Đang giao hàng' },
    { value: 'completed', label: 'Hoàn thành' },
    { value: 'cancelled', label: 'Đã hủy' },
  ];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private adminDataService: AdminDataService,
    private notificationService: AdminNotificationService,
    private loadingService: AdminLoadingService
  ) {}

  ngOnInit() {
    const orderId = this.route.snapshot.paramMap.get('id');
    if (orderId) {
      this.loadOrderDetail(+orderId);
    }
  }

  loadOrderDetail(orderId: number) {
    this.loading = true;
    this.loadingService.setLoading('order-detail', true);

    // Mock order detail for now since AdminDataService doesn't have getOrderById
    const mockOrder: DonHang = {
      id: orderId,
      khachhangid: 1,
      ngaydat: '2025-01-15',
      diachinhan: '123 Đường ABC, Quận 1, TP.HCM',
      trangthaidonhang: 'pending',
      tongtien: 1500000,
      phuongthucthanhtoan: 'cash',
      trangthaithanhtoan: false,
      khachhang: {
        id: 1,
        hoten: 'Nguyễn Văn An',
        email: 'nguyenvanan@email.com',
        sodienthoai: '0901234567',
        diachi: '123 Đường ABC, Quận 1, TP.HCM'
      }
    };

    // Simulate API call
    setTimeout(() => {
      this.order = mockOrder;
      this.selectedStatus = mockOrder.trangthaidonhang || '';
      this.loading = false;
      this.loadingService.setLoading('order-detail', false);
    }, 500);
  }

  updateOrderStatus() {
    if (!this.order || !this.selectedStatus) {
      this.notificationService.error('Lỗi', 'Vui lòng chọn trạng thái');
      return;
    }

    if (confirm(`Bạn có chắc muốn cập nhật trạng thái đơn hàng #${this.order.id}?`)) {
      this.loadingService.setLoading('order-update', true);

      // Use AdminDataService updateOrderStatus
      this.adminDataService.updateOrderStatus(this.order.id, this.selectedStatus)
        .subscribe({
          next: () => {
            this.order!.trangthaidonhang = this.selectedStatus;
            this.notificationService.success('Thành công', 'Đã cập nhật trạng thái đơn hàng');
            this.loadingService.setLoading('order-update', false);
          },
          error: (error: any) => {
            console.error('Error updating order status:', error);
            this.notificationService.error('Lỗi', 'Không thể cập nhật trạng thái đơn hàng');
            this.loadingService.setLoading('order-update', false);
          }
        });
    }
  }

  updateStatus() {
    this.updateOrderStatus();
  }

  isStatusActive(status: string): boolean {
    if (!this.order) return false;
    const currentStatus = this.order.trangthaidonhang || '';

    switch (status) {
      case 'CHO_XAC_NHAN':
        return currentStatus === 'pending';
      case 'DA_XAC_NHAN':
        return (
          currentStatus === 'processing' ||
          currentStatus === 'shipping' ||
          currentStatus === 'completed'
        );
      case 'DANG_GIAO':
        return currentStatus === 'shipping' || currentStatus === 'completed';
      case 'DA_GIAO':
        return currentStatus === 'completed';
      default:
        return false;
    }
  }

  isStatusCompleted(status: string): boolean {
    if (!this.order) return false;
    const currentStatus = this.order.trangthaidonhang || '';

    switch (status) {
      case 'CHO_XAC_NHAN':
        return currentStatus !== 'pending';
      case 'DA_XAC_NHAN':
        return currentStatus === 'shipping' || currentStatus === 'completed';
      case 'DANG_GIAO':
        return currentStatus === 'completed';
      case 'DA_GIAO':
        return currentStatus === 'completed';
      default:
        return false;
    }
  }

  getPhoneNumber(order: DonHang): string {
    return order.khachhang?.sodienthoai || 'Chưa có';
  }

  formatDateTime(date: string | Date): string {
    return new Date(date).toLocaleString('vi-VN');
  }

  getPaymentMethodLabel(method: string): string {
    const paymentMethods: { [key: string]: string } = {
      cash: 'Tiền mặt',
      card: 'Thẻ tín dụng',
      bank_transfer: 'Chuyển khoản',
      momo: 'Ví MoMo',
      vnpay: 'VNPay',
    };
    return paymentMethods[method] || method;
  }

  goBack() {
    this.router.navigate(['/admin/order-management']);
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(amount);
  }

  formatDate(date: string | Date): string {
    return new Date(date).toLocaleDateString('vi-VN');
  }

  getStatusLabel(status: string): string {
    const statusLabels: { [key: string]: string } = {
      pending: 'Chờ xử lý',
      processing: 'Đang xử lý',
      shipping: 'Đang giao hàng',
      completed: 'Hoàn thành',
      cancelled: 'Đã hủy',
    };
    return statusLabels[status] || status;
  }

  getStatusClass(status: string): string {
    const statusClasses: { [key: string]: string } = {
      pending: 'status-pending',
      processing: 'status-processing',
      shipping: 'status-shipping',
      completed: 'status-completed',
      cancelled: 'status-cancelled',
    };
    return statusClasses[status] || '';
  }
}
