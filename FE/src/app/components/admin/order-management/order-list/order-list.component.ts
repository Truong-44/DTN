import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { DonHang } from '../../../../core/models/donhang.model';
import { DonHangService } from '../../../../core/services/donhang.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { LoadingService } from '../../../../core/services/loading.service';

@Component({
  selector: 'app-admin-order-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.scss'],
})
export class AdminOrderListComponent implements OnInit {
  orders: DonHang[] = [];
  filteredOrders: DonHang[] = [];

  // Filters
  searchTerm = '';
  statusFilter = '';
  dateFilter = '';

  // Statistics
  totalOrders = 0;
  pendingOrders = 0;
  shippingOrders = 0;
  completedOrders = 0;
  totalRevenue = 0;

  // Pagination
  currentPage = 1;
  itemsPerPage = 10;
  totalPages = 0;

  // Loading
  loading = false;

  constructor(
    private donHangService: DonHangService,
    private router: Router,
    private notificationService: NotificationService,
    private loadingService: LoadingService
  ) {}

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders(): void {
    this.loading = true;
    this.loadingService.show();

    this.donHangService.getAllDonHang().subscribe({
      next: (response: any) => {
        this.orders = Array.isArray(response) ? response : response.items || [];
        this.calculateStatistics();
        this.applyFilters();
        this.loading = false;
        this.loadingService.hide();
      },
      error: (error) => {
        console.error('Error loading orders:', error);
        this.notificationService.error(
          'Lỗi',
          'Không thể tải danh sách đơn hàng'
        );
        this.loading = false;
        this.loadingService.hide();
      },
    });
  }

  calculateStatistics(): void {
    this.totalOrders = this.orders.length;
    this.pendingOrders = this.orders.filter(
      (order) => order.trangthaidonhang === 'pending'
    ).length;
    this.shippingOrders = this.orders.filter(
      (order) => order.trangthaidonhang === 'shipping'
    ).length;
    this.completedOrders = this.orders.filter(
      (order) => order.trangthaidonhang === 'completed'
    ).length;
    this.totalRevenue = this.orders
      .filter((order) => order.trangthaidonhang === 'completed')
      .reduce((sum, order) => sum + (order.tongtien || 0), 0);
  }

  applyFilters(): void {
    this.filteredOrders = this.orders.filter((order) => {
      const matchesSearch =
        !this.searchTerm ||
        order.id?.toString().includes(this.searchTerm) ||
        order.khachhang?.hoten
          ?.toLowerCase()
          .includes(this.searchTerm.toLowerCase());

      const matchesStatus =
        !this.statusFilter || order.trangthaidonhang === this.statusFilter;

      const matchesDate =
        !this.dateFilter ||
        new Date(order.ngaydat).toDateString() ===
          new Date(this.dateFilter).toDateString();

      return matchesSearch && matchesStatus && matchesDate;
    });

    this.updatePagination();
  }

  updatePagination(): void {
    this.totalPages = Math.ceil(this.filteredOrders.length / this.itemsPerPage);
    if (this.currentPage > this.totalPages) {
      this.currentPage = 1;
    }
  }

  getPaginatedOrders(): DonHang[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.filteredOrders.slice(startIndex, endIndex);
  }

  onSearchChange(): void {
    this.currentPage = 1;
    this.applyFilters();
  }

  onStatusFilterChange(): void {
    this.currentPage = 1;
    this.applyFilters();
  }

  onDateFilterChange(): void {
    this.currentPage = 1;
    this.applyFilters();
  }

  filterOrders(): void {
    this.currentPage = 1;
    this.applyFilters();
  }

  viewOrderDetail(orderOrId: DonHang | string): void {
    const orderId =
      typeof orderOrId === 'string' ? orderOrId : orderOrId.id.toString();
    this.router.navigate(['/admin/orders', orderId]);
  }

  updateOrderStatus(orderId: string, newStatus: string): void {
    this.donHangService
      .updateDonHangStatus(Number(orderId), newStatus)
      .subscribe({
        next: () => {
          this.notificationService.success(
            'Thành công',
            'Cập nhật trạng thái đơn hàng thành công'
          );
          this.loadOrders();
        },
        error: (error: any) => {
          console.error('Error updating order status:', error);
          this.notificationService.error(
            'Lỗi',
            'Không thể cập nhật trạng thái đơn hàng'
          );
        },
      });
  }

  deleteOrder(orderId: string | number): void {
    const id = typeof orderId === 'string' ? orderId : orderId.toString();
    if (confirm('Bạn có chắc chắn muốn xóa đơn hàng này?')) {
      // Since deleteDonHang method doesn't exist, use updateDonHangStatus to mark as cancelled
      this.donHangService
        .updateDonHangStatus(Number(id), 'cancelled')
        .subscribe({
          next: () => {
            this.notificationService.success(
              'Thành công',
              'Hủy đơn hàng thành công'
            );
            this.loadOrders();
          },
          error: (error: any) => {
            console.error('Error cancelling order:', error);
            this.notificationService.error('Lỗi', 'Không thể hủy đơn hàng');
          },
        });
    }
  }

  editOrder(order: DonHang): void {
    // Navigate to order edit page or open edit modal
    this.router.navigate(['/admin/orders/edit', order.id]);
  }

  exportOrders(): void {
    // Implementation for exporting orders to CSV/Excel
    this.notificationService.info(
      'Thông báo',
      'Tính năng xuất dữ liệu đang phát triển'
    );
  }

  refreshOrders(): void {
    this.loadOrders();
  }

  goToPage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
    }
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
    const labels: { [key: string]: string } = {
      pending: 'Chờ xử lý',
      processing: 'Đang xử lý',
      shipping: 'Đang giao hàng',
      completed: 'Hoàn thành',
      cancelled: 'Đã hủy',
    };
    return labels[status] || status;
  }

  getStatusClass(status: string): string {
    const classes: { [key: string]: string } = {
      pending: 'status-pending',
      processing: 'status-processing',
      shipping: 'status-shipping',
      completed: 'status-completed',
      cancelled: 'status-cancelled',
    };
    return classes[status] || 'status-default';
  }

  getPhoneNumber(order: DonHang): string {
    return order.khachhang?.taikhoan?.sodienthoai || 'N/A';
  }
}
