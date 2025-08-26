import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

interface DonHang {
  id: number;
  khachHangId: number;
  customerName?: string;
  tongTien: number;
  trangThai: string;
  ngayTao: string;
  diaChi?: string;
  soDienThoai?: string;
}

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
  isLoading = true;

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

  // Status options
  statusOptions = [
    { value: '', label: 'Tất cả trạng thái' },
    { value: 'pending', label: 'Chờ xử lý' },
    { value: 'confirmed', label: 'Đã xác nhận' },
    { value: 'shipping', label: 'Đang giao' },
    { value: 'completed', label: 'Hoàn thành' },
    { value: 'cancelled', label: 'Đã hủy' }
  ];

  constructor(
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders(): void {
    this.isLoading = true;
    
    this.http.get<any>('http://localhost:8080/api/donhang')
      .subscribe({
        next: (response) => {
          this.orders = response.data || [];
          this.calculateStatistics();
          this.applyFilters();
          this.isLoading = false;
        },
        error: (error: any) => {
          console.error('Error loading orders:', error);
          // Use mock data on error
          this.orders = this.getMockData();
          this.calculateStatistics();
          this.applyFilters();
          this.isLoading = false;
        }
      });
  }

  getMockData(): DonHang[] {
    return [
      {
        id: 1001,
        khachHangId: 1,
        customerName: 'Nguyễn Văn An',
        tongTien: 15000000,
        trangThai: 'completed',
        ngayTao: '2025-08-26T10:30:00',
        diaChi: '123 Đường ABC, Quận 1, TP.HCM',
        soDienThoai: '0901234567'
      },
      {
        id: 1002,
        khachHangId: 2,
        customerName: 'Trần Thị Bình',
        tongTien: 8500000,
        trangThai: 'pending',
        ngayTao: '2025-08-26T09:15:00',
        diaChi: '456 Đường XYZ, Quận 2, TP.HCM',
        soDienThoai: '0902345678'
      },
      {
        id: 1003,
        khachHangId: 3,
        customerName: 'Lê Văn Cường',
        tongTien: 12000000,
        trangThai: 'shipping',
        ngayTao: '2025-08-25T14:20:00',
        diaChi: '789 Đường DEF, Quận 3, TP.HCM',
        soDienThoai: '0903456789'
      }
    ];
  }

  calculateStatistics(): void {
    this.totalOrders = this.orders.length;
    this.pendingOrders = this.orders.filter(order => order.trangThai === 'pending').length;
    this.shippingOrders = this.orders.filter(order => order.trangThai === 'shipping').length;
    this.completedOrders = this.orders.filter(order => order.trangThai === 'completed').length;
    this.totalRevenue = this.orders
      .filter(order => order.trangThai === 'completed')
      .reduce((sum, order) => sum + (order.tongTien || 0), 0);
  }

  applyFilters(): void {
    this.filteredOrders = this.orders.filter(order => {
      const matchesSearch = !this.searchTerm || 
        order.customerName?.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        order.id.toString().includes(this.searchTerm) ||
        order.soDienThoai?.includes(this.searchTerm);

      const matchesStatus = !this.statusFilter || order.trangThai === this.statusFilter;

      const matchesDate = !this.dateFilter || 
        new Date(order.ngayTao).toDateString() === new Date(this.dateFilter).toDateString();

      return matchesSearch && matchesStatus && matchesDate;
    });
  }

  onSearchChange(): void {
    this.applyFilters();
  }

  onStatusFilterChange(): void {
    this.applyFilters();
  }

  onDateFilterChange(): void {
    this.applyFilters();
  }

  viewOrderDetail(orderId: number): void {
    this.router.navigate(['/admin/order-management/detail', orderId]);
  }

  updateOrderStatus(orderId: number, newStatus: string): void {
    this.http.patch(`http://localhost:8080/api/donhang/${orderId}/status`, { status: newStatus })
      .subscribe({
        next: () => {
          const order = this.orders.find(o => o.id === orderId);
          if (order) {
            order.trangThai = newStatus;
            this.calculateStatistics();
            this.applyFilters();
          }
          this.showNotification('Cập nhật trạng thái thành công!', 'success');
        },
        error: (error: any) => {
          console.error('Error updating status:', error);
          this.showNotification('Có lỗi khi cập nhật trạng thái!', 'error');
        }
      });
  }

  deleteOrder(orderId: number): void {
    if (confirm('Bạn có chắc chắn muốn xóa đơn hàng này?')) {
      this.http.delete(`http://localhost:8080/api/donhang/${orderId}`)
        .subscribe({
          next: () => {
            this.orders = this.orders.filter(o => o.id !== orderId);
            this.calculateStatistics();
            this.applyFilters();
            this.showNotification('Xóa đơn hàng thành công!', 'success');
          },
          error: (error: any) => {
            console.error('Error deleting order:', error);
            this.showNotification('Có lỗi khi xóa đơn hàng!', 'error');
          }
        });
    }
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND'
    }).format(amount);
  }

  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleDateString('vi-VN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'completed':
        return 'status-completed';
      case 'pending':
        return 'status-pending';
      case 'shipping':
        return 'status-shipping';
      case 'confirmed':
        return 'status-confirmed';
      case 'cancelled':
        return 'status-cancelled';
      default:
        return 'status-default';
    }
  }

  getStatusLabel(status: string): string {
    const option = this.statusOptions.find(opt => opt.value === status);
    return option ? option.label : status;
  }

  refreshData(): void {
    this.loadOrders();
  }

  exportOrders(): void {
    // Implementation for export functionality
    this.showNotification('Tính năng xuất dữ liệu đang được phát triển!', 'info');
  }

  trackByOrderId(index: number, order: DonHang): number {
    return order.id;
  }

  private showNotification(message: string, type: 'success' | 'error' | 'info'): void {
    // Simple notification implementation
    alert(message);
  }
}
