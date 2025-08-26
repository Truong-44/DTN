import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../../../core/services/api.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { KhoHang } from '../../../../core/models/khohang.model';
import { SanPham } from '../../../../core/models/sanpham.model';
import { DanhMuc } from '../../../../core/models/danhmuc.model';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-stock-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './stock-list.component.html',
  styleUrls: ['./stock-list.component.scss'],
})
export class StockListComponent implements OnInit {
  stocks: KhoHang[] = [];
  filteredStocks: KhoHang[] = [];
  categories: DanhMuc[] = [];
  loading = false;

  // Filter properties
  searchKeyword = '';
  categoryFilter = '';
  statusFilter = '';

  // Stats
  totalProducts = 0;
  lowStockProducts = 0;
  outOfStockProducts = 0;
  totalStockValue = 0;

  constructor(
    private apiService: ApiService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.loading = true;

    forkJoin({
      stocks: this.apiService.get<KhoHang[]>('api/khohang'),
      categories: this.apiService.get<DanhMuc[]>('api/danhmuc'),
    }).subscribe({
      next: (data) => {
        this.stocks = data.stocks || [];
        this.categories = data.categories || [];
        this.calculateStats();
        this.applyFilters();
        this.loading = false;
      },
      error: (error) => {
        console.error('Lỗi tải dữ liệu:', error);
        this.notificationService.error('Lỗi', 'Lỗi tải dữ liệu kho hàng');
        this.loading = false;
      },
    });
  }

  calculateStats(): void {
    this.totalProducts = this.stocks.length;
    this.lowStockProducts = this.stocks.filter(
      (s) => (s.soluongton || 0) > 0 && (s.soluongton || 0) <= 10
    ).length;
    this.outOfStockProducts = this.stocks.filter(
      (s) => (s.soluongton || 0) === 0
    ).length;
    this.totalStockValue = this.stocks.reduce((total, stock) => {
      return total + (stock.soluongton || 0) * (stock.gianhap || 0);
    }, 0);
  }

  applyFilters(): void {
    this.filteredStocks = this.stocks.filter((stock) => {
      const matchesSearch =
        !this.searchKeyword ||
        stock.sanpham?.tensanpham
          ?.toLowerCase()
          .includes(this.searchKeyword.toLowerCase()) ||
        stock.sanpham?.id?.toString().includes(this.searchKeyword);

      const matchesCategory =
        !this.categoryFilter ||
        stock.sanpham?.danhmucid?.toString() === this.categoryFilter;

      const matchesStatus =
        !this.statusFilter ||
        this.getStockStatus(stock.soluongton || 0) === this.statusFilter;

      return matchesSearch && matchesCategory && matchesStatus;
    });
  }

  getStockStatus(quantity: number): string {
    if (quantity === 0) return 'OUT_OF_STOCK';
    if (quantity <= 10) return 'LOW_STOCK';
    return 'IN_STOCK';
  }

  getStockStatusClass(quantity: number): string {
    const status = this.getStockStatus(quantity);
    return (
      {
        OUT_OF_STOCK: 'text-danger',
        LOW_STOCK: 'text-warning',
        IN_STOCK: 'text-success',
      }[status] || ''
    );
  }

  getStockStatusText(quantity: number): string {
    const status = this.getStockStatus(quantity);
    return (
      {
        OUT_OF_STOCK: 'Hết hàng',
        LOW_STOCK: 'Sắp hết',
        IN_STOCK: 'Còn hàng',
      }[status] || 'Không xác định'
    );
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(amount);
  }

  importStock(): void {
    // TODO: Implement import stock functionality
    this.notificationService.info(
      'Thông báo',
      'Chức năng nhập kho đang phát triển'
    );
  }

  editStock(stock: KhoHang): void {
    // TODO: Implement edit stock functionality
    this.notificationService.info(
      'Thông báo',
      'Chức năng chỉnh sửa kho đang phát triển'
    );
  }

  updateStock(stockId: number, newQuantity: number): void {
    if (newQuantity < 0) {
      this.notificationService.error('Lỗi', 'Số lượng không được âm');
      return;
    }

    const updateData = { soluongton: newQuantity };

    this.apiService
      .put<KhoHang>(`api/khohang/${stockId}`, updateData)
      .subscribe({
        next: (updatedStock) => {
          const index = this.stocks.findIndex((s) => s.id === stockId);
          if (index !== -1) {
            this.stocks[index] = updatedStock;
            this.calculateStats();
            this.applyFilters();
            this.notificationService.success(
              'Thành công',
              'Cập nhật số lượng thành công'
            );
          }
        },
        error: (error) => {
          console.error('Lỗi cập nhật số lượng:', error);
          this.notificationService.error('Lỗi', 'Lỗi cập nhật số lượng');
        },
      });
  }

  deleteStock(stockId: number): void {
    if (confirm('Bạn có chắc chắn muốn xóa mục kho này?')) {
      this.apiService.delete<void>(`api/khohang/${stockId}`).subscribe({
        next: () => {
          this.stocks = this.stocks.filter((s) => s.id !== stockId);
          this.calculateStats();
          this.applyFilters();
          this.notificationService.success(
            'Thành công',
            'Xóa mục kho thành công'
          );
        },
        error: (error) => {
          console.error('Lỗi xóa mục kho:', error);
          this.notificationService.error('Lỗi', 'Lỗi xóa mục kho');
        },
      });
    }
  }
}
