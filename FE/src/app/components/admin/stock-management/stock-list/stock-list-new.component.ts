import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

interface KhoHang {
  id: number;
  sanPham?: any;
  soLuongTon: number;
  soLuongToiThieu: number;
  soLuongToiDa: number;
  viTri?: string;
  ngayCapNhat: string;
  ghiChu?: string;
}

@Component({
  selector: 'app-stock-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="stock-management">
      <!-- Page Header -->
      <div class="page-header">
        <h1>Quản lý kho hàng</h1>
        <div class="header-actions">
          <button class="btn btn-primary" (click)="addStock()">Thêm sản phẩm vào kho</button>
          <button class="btn btn-secondary" (click)="refreshData()">Làm mới</button>
          <button class="btn btn-outline" (click)="exportStock()">Xuất Excel</button>
        </div>
      </div>

      <!-- Statistics Cards -->
      <div class="stats-grid">
        <div class="stat-card">
          <div class="stat-value">{{ totalProducts }}</div>
          <div class="stat-label">Tổng sản phẩm</div>
        </div>
        <div class="stat-card">
          <div class="stat-value">{{ totalStock }}</div>
          <div class="stat-label">Tổng tồn kho</div>
        </div>
        <div class="stat-card">
          <div class="stat-value">{{ lowStockCount }}</div>
          <div class="stat-label">Sắp hết hàng</div>
        </div>
        <div class="stat-card">
          <div class="stat-value">{{ outOfStockCount }}</div>
          <div class="stat-label">Hết hàng</div>
        </div>
      </div>

      <!-- Filters -->
      <div class="filters-section">
        <div class="filter-group">
          <input 
            type="text" 
            [(ngModel)]="searchTerm" 
            (ngModelChange)="applyFilters()"
            placeholder="Tìm kiếm theo tên sản phẩm hoặc vị trí..."
            class="search-input">
        </div>
        
        <div class="filter-group">
          <select [(ngModel)]="stockFilter" (ngModelChange)="applyFilters()" class="filter-select">
            <option value="">Tất cả trạng thái</option>
            <option value="available">Còn hàng</option>
            <option value="low">Sắp hết</option>
            <option value="out">Hết hàng</option>
          </select>
        </div>

        <div class="filter-group">
          <select [(ngModel)]="categoryFilter" (ngModelChange)="applyFilters()" class="filter-select">
            <option value="">Tất cả danh mục</option>
            <option *ngFor="let category of getUniqueCategories()" [value]="category">{{ category }}</option>
          </select>
        </div>

        <div class="filter-group">
          <select [(ngModel)]="locationFilter" (ngModelChange)="applyFilters()" class="filter-select">
            <option value="">Tất cả vị trí</option>
            <option *ngFor="let location of getUniqueLocations()" [value]="location">{{ location }}</option>
          </select>
        </div>
      </div>

      <!-- Loading State -->
      <div *ngIf="isLoading" class="loading-container">
        <div class="loading-spinner"></div>
        <p>Đang tải dữ liệu...</p>
      </div>

      <!-- Stock Table -->
      <div *ngIf="!isLoading" class="table-container">
        <table class="data-table" *ngIf="filteredStocks.length > 0">
          <thead>
            <tr>
              <th>ID</th>
              <th>Sản phẩm</th>
              <th>Danh mục</th>
              <th>Số lượng tồn</th>
              <th>Tối thiểu</th>
              <th>Tối đa</th>
              <th>Vị trí</th>
              <th>Trạng thái</th>
              <th>Cập nhật cuối</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let stock of filteredStocks; trackBy: trackByStockId" [class]="getRowClass(stock)">
              <td>{{ stock.id }}</td>
              <td class="product-name">{{ getProductName(stock) }}</td>
              <td>{{ getProductCategory(stock) }}</td>
              <td class="quantity">{{ stock.soLuongTon }}</td>
              <td>{{ stock.soLuongToiThieu }}</td>
              <td>{{ stock.soLuongToiDa }}</td>
              <td>{{ stock.viTri || 'Chưa có' }}</td>
              <td>
                <span [class]="getStockStatusClass(stock)">
                  {{ getStockStatusLabel(stock) }}
                </span>
              </td>
              <td>{{ formatDate(stock.ngayCapNhat) }}</td>
              <td class="actions">
                <button class="btn btn-sm btn-info" (click)="viewStock(stock)">Xem</button>
                <button class="btn btn-sm btn-primary" (click)="editStock(stock)">Sửa</button>
                <button class="btn btn-sm btn-warning" (click)="adjustStock(stock)">Điều chỉnh</button>
              </td>
            </tr>
          </tbody>
        </table>

        <!-- Empty State -->
        <div *ngIf="filteredStocks.length === 0" class="empty-state">
          <p>Không tìm thấy sản phẩm nào trong kho phù hợp với bộ lọc.</p>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .stock-management {
      padding: 20px;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    .page-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 30px;
      padding-bottom: 15px;
      border-bottom: 2px solid #f0f0f0;
    }

    .page-header h1 {
      color: #ff6600;
      margin: 0;
      font-size: 28px;
      font-weight: 600;
    }

    .header-actions {
      display: flex;
      gap: 10px;
    }

    .stats-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: 20px;
      margin-bottom: 30px;
    }

    .stat-card {
      background: white;
      padding: 25px;
      border-radius: 10px;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
      text-align: center;
      border-top: 4px solid #ff6600;
      transition: transform 0.3s ease;
    }

    .stat-card:hover {
      transform: translateY(-5px);
    }

    .stat-value {
      font-size: 32px;
      font-weight: bold;
      color: #ff6600;
      margin-bottom: 8px;
    }

    .stat-label {
      color: #666;
      font-size: 14px;
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }

    .filters-section {
      background: white;
      padding: 20px;
      border-radius: 10px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      margin-bottom: 20px;
      display: grid;
      grid-template-columns: 2fr 1fr 1fr 1fr;
      gap: 15px;
      align-items: center;
    }

    .filter-group {
      display: flex;
      flex-direction: column;
    }

    .search-input, .filter-select {
      width: 100%;
      padding: 12px 15px;
      border: 2px solid #e0e0e0;
      border-radius: 8px;
      font-size: 14px;
      transition: border-color 0.3s ease;
    }

    .search-input:focus, .filter-select:focus {
      outline: none;
      border-color: #ff6600;
      box-shadow: 0 0 0 3px rgba(255, 102, 0, 0.1);
    }

    .loading-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 60px 20px;
      background: white;
      border-radius: 10px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .loading-spinner {
      width: 50px;
      height: 50px;
      border: 4px solid #f0f0f0;
      border-top: 4px solid #ff6600;
      border-radius: 50%;
      animation: spin 1s linear infinite;
      margin-bottom: 15px;
    }

    @keyframes spin {
      0% { transform: rotate(0deg); }
      100% { transform: rotate(360deg); }
    }

    .table-container {
      background: white;
      border-radius: 10px;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
      overflow: hidden;
    }

    .data-table {
      width: 100%;
      border-collapse: collapse;
      font-size: 14px;
    }

    .data-table th {
      background: #ff6600;
      color: white;
      padding: 15px 12px;
      text-align: left;
      font-weight: 600;
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }

    .data-table td {
      padding: 15px 12px;
      border-bottom: 1px solid #f0f0f0;
      vertical-align: middle;
    }

    .data-table tr:hover {
      background-color: #fff5f0;
    }

    .data-table tr.low-stock {
      background-color: #fff3cd;
    }

    .data-table tr.out-of-stock {
      background-color: #f8d7da;
    }

    .product-name {
      font-weight: 600;
      color: #333;
    }

    .quantity {
      font-weight: 600;
      font-size: 16px;
    }

    .status-available {
      background: #28a745;
      color: white;
      padding: 4px 8px;
      border-radius: 15px;
      font-size: 12px;
      font-weight: 500;
    }

    .status-low {
      background: #ffc107;
      color: #212529;
      padding: 4px 8px;
      border-radius: 15px;
      font-size: 12px;
      font-weight: 500;
    }

    .status-out {
      background: #dc3545;
      color: white;
      padding: 4px 8px;
      border-radius: 15px;
      font-size: 12px;
      font-weight: 500;
    }

    .actions {
      white-space: nowrap;
    }

    .actions .btn {
      margin-right: 5px;
      font-size: 12px;
      padding: 6px 12px;
    }

    .btn {
      padding: 10px 20px;
      border: none;
      border-radius: 6px;
      cursor: pointer;
      font-size: 14px;
      font-weight: 500;
      transition: all 0.3s ease;
      text-decoration: none;
      display: inline-block;
      text-align: center;
    }

    .btn-primary {
      background: #ff6600;
      color: white;
    }

    .btn-primary:hover {
      background: #e55a00;
    }

    .btn-secondary {
      background: #6c757d;
      color: white;
    }

    .btn-secondary:hover {
      background: #5a6268;
    }

    .btn-outline {
      background: transparent;
      color: #ff6600;
      border: 2px solid #ff6600;
    }

    .btn-outline:hover {
      background: #ff6600;
      color: white;
    }

    .btn-info {
      background: #17a2b8;
      color: white;
    }

    .btn-info:hover {
      background: #138496;
    }

    .btn-warning {
      background: #ffc107;
      color: #212529;
    }

    .btn-warning:hover {
      background: #e0a800;
    }

    .btn-sm {
      padding: 6px 12px;
      font-size: 12px;
    }

    .empty-state {
      text-align: center;
      padding: 60px 20px;
      color: #666;
    }

    @media (max-width: 768px) {
      .filters-section {
        grid-template-columns: 1fr;
      }
      
      .stats-grid {
        grid-template-columns: 1fr;
      }
      
      .page-header {
        flex-direction: column;
        gap: 15px;
      }

      .data-table {
        font-size: 12px;
      }
    }
  `]
})
export class StockListNewComponent implements OnInit {
  stocks: KhoHang[] = [];
  filteredStocks: KhoHang[] = [];
  isLoading = true;

  // Filters
  searchTerm = '';
  stockFilter = '';
  categoryFilter = '';
  locationFilter = '';

  // Statistics
  totalProducts = 0;
  totalStock = 0;
  lowStockCount = 0;
  outOfStockCount = 0;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadStocks();
  }

  loadStocks(): void {
    this.isLoading = true;
    
    this.http.get<any>('http://localhost:8080/api/khohang')
      .subscribe({
        next: (response) => {
          this.stocks = response.data || [];
          this.calculateStatistics();
          this.applyFilters();
          this.isLoading = false;
        },
        error: (error: any) => {
          console.error('Error loading stocks:', error);
          this.stocks = this.getMockStocks();
          this.calculateStatistics();
          this.applyFilters();
          this.isLoading = false;
        }
      });
  }

  getMockStocks(): KhoHang[] {
    return [
      {
        id: 1,
        sanPham: { ten: 'Ghế sofa da', danhMuc: { ten: 'Sofa' } },
        soLuongTon: 15,
        soLuongToiThieu: 5,
        soLuongToiDa: 50,
        viTri: 'Khu A - Kệ 01',
        ngayCapNhat: '2025-08-26T14:30:00',
        ghiChu: 'Hàng mới nhập'
      },
      {
        id: 2,
        sanPham: { ten: 'Bàn ăn gỗ tự nhiên', danhMuc: { ten: 'Bàn ăn' } },
        soLuongTon: 3,
        soLuongToiThieu: 5,
        soLuongToiDa: 30,
        viTri: 'Khu B - Kệ 05',
        ngayCapNhat: '2025-08-25T10:15:00',
        ghiChu: 'Sắp hết hàng'
      },
      {
        id: 3,
        sanPham: { ten: 'Tủ quần áo 3 cánh', danhMuc: { ten: 'Tủ' } },
        soLuongTon: 0,
        soLuongToiThieu: 3,
        soLuongToiDa: 20,
        viTri: 'Khu C - Kệ 08',
        ngayCapNhat: '2025-08-24T16:45:00',
        ghiChu: 'Hết hàng - cần nhập'
      },
      {
        id: 4,
        sanPham: { ten: 'Giường ngủ 1m8', danhMuc: { ten: 'Giường' } },
        soLuongTon: 25,
        soLuongToiThieu: 10,
        soLuongToiDa: 40,
        viTri: 'Khu D - Kệ 12',
        ngayCapNhat: '2025-08-26T09:20:00'
      }
    ];
  }

  calculateStatistics(): void {
    this.totalProducts = this.stocks.length;
    this.totalStock = this.stocks.reduce((sum, stock) => sum + stock.soLuongTon, 0);
    this.lowStockCount = this.stocks.filter(stock => 
      stock.soLuongTon > 0 && stock.soLuongTon <= stock.soLuongToiThieu
    ).length;
    this.outOfStockCount = this.stocks.filter(stock => stock.soLuongTon === 0).length;
  }

  applyFilters(): void {
    this.filteredStocks = this.stocks.filter(stock => {
      const productName = this.getProductName(stock);
      const location = stock.viTri || '';
      
      const matchesSearch = !this.searchTerm || 
        productName.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        location.toLowerCase().includes(this.searchTerm.toLowerCase());

      const matchesStock = !this.stockFilter || this.matchesStockFilter(stock);
      
      const matchesCategory = !this.categoryFilter || 
        this.getProductCategory(stock) === this.categoryFilter;

      const matchesLocation = !this.locationFilter || stock.viTri === this.locationFilter;

      return matchesSearch && matchesStock && matchesCategory && matchesLocation;
    });
  }

  matchesStockFilter(stock: KhoHang): boolean {
    switch (this.stockFilter) {
      case 'available':
        return stock.soLuongTon > stock.soLuongToiThieu;
      case 'low':
        return stock.soLuongTon > 0 && stock.soLuongTon <= stock.soLuongToiThieu;
      case 'out':
        return stock.soLuongTon === 0;
      default:
        return true;
    }
  }

  getProductName(stock: KhoHang): string {
    return stock.sanPham?.ten || 'Sản phẩm không xác định';
  }

  getProductCategory(stock: KhoHang): string {
    return stock.sanPham?.danhMuc?.ten || 'Chưa phân loại';
  }

  getUniqueCategories(): string[] {
    const categories = this.stocks
      .map(stock => this.getProductCategory(stock))
      .filter(category => category !== 'Chưa phân loại')
      .filter((category, index, arr) => arr.indexOf(category) === index);
    return categories;
  }

  getUniqueLocations(): string[] {
    const locations = this.stocks
      .map(stock => stock.viTri)
      .filter(location => location && location.trim() !== '')
      .filter((location, index, arr) => arr.indexOf(location) === index);
    return locations as string[];
  }

  getStockStatusClass(stock: KhoHang): string {
    if (stock.soLuongTon === 0) return 'status-out';
    if (stock.soLuongTon <= stock.soLuongToiThieu) return 'status-low';
    return 'status-available';
  }

  getStockStatusLabel(stock: KhoHang): string {
    if (stock.soLuongTon === 0) return 'Hết hàng';
    if (stock.soLuongTon <= stock.soLuongToiThieu) return 'Sắp hết';
    return 'Còn hàng';
  }

  getRowClass(stock: KhoHang): string {
    if (stock.soLuongTon === 0) return 'out-of-stock';
    if (stock.soLuongTon <= stock.soLuongToiThieu) return 'low-stock';
    return '';
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

  viewStock(stock: KhoHang): void {
    alert(`Xem chi tiết kho hàng ID: ${stock.id}`);
  }

  editStock(stock: KhoHang): void {
    alert(`Chỉnh sửa thông tin kho hàng: ${this.getProductName(stock)}`);
  }

  adjustStock(stock: KhoHang): void {
    const newQuantity = prompt(
      `Điều chỉnh số lượng tồn kho cho ${this.getProductName(stock)}:\nSố lượng hiện tại: ${stock.soLuongTon}`,
      stock.soLuongTon.toString()
    );
    
    if (newQuantity !== null && !isNaN(Number(newQuantity))) {
      const quantity = Number(newQuantity);
      if (quantity >= 0) {
        stock.soLuongTon = quantity;
        stock.ngayCapNhat = new Date().toISOString();
        this.calculateStatistics();
        this.applyFilters();
        alert('Điều chỉnh số lượng thành công!');
      } else {
        alert('Số lượng không thể âm!');
      }
    }
  }

  addStock(): void {
    alert('Tính năng thêm sản phẩm vào kho đang được phát triển!');
  }

  refreshData(): void {
    this.loadStocks();
  }

  exportStock(): void {
    alert('Tính năng xuất dữ liệu đang được phát triển!');
  }

  trackByStockId(index: number, stock: KhoHang): number {
    return stock.id;
  }
}
