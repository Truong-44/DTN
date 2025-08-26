import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

interface HoaDon {
  id: number;
  maHoaDon: string;
  donHang?: any;
  khachHang?: any;
  ngayTaoHoaDon: string;
  tongTien: number;
  trangThai: string;
  ghiChu?: string;
}

@Component({
  selector: 'app-invoice-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="invoice-management">
      <!-- Page Header -->
      <div class="page-header">
        <h1>Quản lý hóa đơn</h1>
        <div class="header-actions">
          <button class="btn btn-secondary" (click)="refreshData()">Làm mới</button>
          <button class="btn btn-outline" (click)="exportInvoices()">Xuất Excel</button>
        </div>
      </div>

      <!-- Statistics Cards -->
      <div class="stats-grid">
        <div class="stat-card">
          <div class="stat-value">{{ totalInvoices }}</div>
          <div class="stat-label">Tổng hóa đơn</div>
        </div>
        <div class="stat-card">
          <div class="stat-value">{{ formatCurrency(totalRevenue) }}</div>
          <div class="stat-label">Tổng doanh thu</div>
        </div>
        <div class="stat-card">
          <div class="stat-value">{{ paidInvoices }}</div>
          <div class="stat-label">Đã thanh toán</div>
        </div>
        <div class="stat-card">
          <div class="stat-value">{{ unpaidInvoices }}</div>
          <div class="stat-label">Chưa thanh toán</div>
        </div>
      </div>

      <!-- Filters -->
      <div class="filters-section">
        <div class="filter-group">
          <input 
            type="text" 
            [(ngModel)]="searchTerm" 
            (ngModelChange)="applyFilters()"
            placeholder="Tìm kiếm theo mã hóa đơn hoặc tên khách hàng..."
            class="search-input">
        </div>
        
        <div class="filter-group">
          <select [(ngModel)]="statusFilter" (ngModelChange)="applyFilters()" class="filter-select">
            <option value="">Tất cả trạng thái</option>
            <option value="Đã thanh toán">Đã thanh toán</option>
            <option value="Chưa thanh toán">Chưa thanh toán</option>
            <option value="Đã hủy">Đã hủy</option>
          </select>
        </div>

        <div class="filter-group">
          <input type="date" [(ngModel)]="dateFrom" (ngModelChange)="applyFilters()" class="filter-select">
        </div>

        <div class="filter-group">
          <input type="date" [(ngModel)]="dateTo" (ngModelChange)="applyFilters()" class="filter-select">
        </div>
      </div>

      <!-- Loading State -->
      <div *ngIf="isLoading" class="loading-container">
        <div class="loading-spinner"></div>
        <p>Đang tải dữ liệu...</p>
      </div>

      <!-- Invoices Table -->
      <div *ngIf="!isLoading" class="table-container">
        <table class="data-table" *ngIf="filteredInvoices.length > 0">
          <thead>
            <tr>
              <th>Mã hóa đơn</th>
              <th>Khách hàng</th>
              <th>Ngày tạo</th>
              <th>Tổng tiền</th>
              <th>Trạng thái</th>
              <th>Ghi chú</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let invoice of filteredInvoices; trackBy: trackByInvoiceId">
              <td class="invoice-code">{{ invoice.maHoaDon }}</td>
              <td>{{ getCustomerName(invoice) }}</td>
              <td>{{ formatDate(invoice.ngayTaoHoaDon) }}</td>
              <td class="amount">{{ formatCurrency(invoice.tongTien) }}</td>
              <td>
                <span [class]="getStatusClass(invoice.trangThai)">
                  {{ invoice.trangThai }}
                </span>
              </td>
              <td>{{ invoice.ghiChu || 'Không có' }}</td>
              <td class="actions">
                <button class="btn btn-sm btn-info" (click)="viewInvoice(invoice)">Xem</button>
                <button class="btn btn-sm btn-primary" (click)="printInvoice(invoice)">In hóa đơn</button>
              </td>
            </tr>
          </tbody>
        </table>

        <!-- Empty State -->
        <div *ngIf="filteredInvoices.length === 0" class="empty-state">
          <p>Không tìm thấy hóa đơn nào phù hợp với bộ lọc.</p>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .invoice-management {
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
      font-size: 24px;
      font-weight: bold;
      color: #ff6600;
      margin-bottom: 8px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
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

    .invoice-code {
      font-weight: 600;
      color: #ff6600;
    }

    .amount {
      font-weight: 600;
      color: #28a745;
    }

    .status-paid {
      background: #28a745;
      color: white;
      padding: 4px 8px;
      border-radius: 15px;
      font-size: 12px;
      font-weight: 500;
    }

    .status-unpaid {
      background: #ffc107;
      color: #212529;
      padding: 4px 8px;
      border-radius: 15px;
      font-size: 12px;
      font-weight: 500;
    }

    .status-cancelled {
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
    }
  `]
})
export class InvoiceListComponent implements OnInit {
  invoices: HoaDon[] = [];
  filteredInvoices: HoaDon[] = [];
  isLoading = true;

  // Filters
  searchTerm = '';
  statusFilter = '';
  dateFrom = '';
  dateTo = '';

  // Statistics
  totalInvoices = 0;
  totalRevenue = 0;
  paidInvoices = 0;
  unpaidInvoices = 0;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadInvoices();
  }

  loadInvoices(): void {
    this.isLoading = true;
    
    this.http.get<any>('http://localhost:8080/api/hoadon')
      .subscribe({
        next: (response) => {
          this.invoices = response.data || [];
          this.calculateStatistics();
          this.applyFilters();
          this.isLoading = false;
        },
        error: (error: any) => {
          console.error('Error loading invoices:', error);
          this.invoices = this.getMockInvoices();
          this.calculateStatistics();
          this.applyFilters();
          this.isLoading = false;
        }
      });
  }

  getMockInvoices(): HoaDon[] {
    return [
      {
        id: 1,
        maHoaDon: 'HD001',
        khachHang: { hovaten: 'Nguyễn Văn A' },
        ngayTaoHoaDon: '2025-08-26T10:30:00',
        tongTien: 15000000,
        trangThai: 'Đã thanh toán',
        ghiChu: 'Thanh toán bằng chuyển khoản'
      },
      {
        id: 2,
        maHoaDon: 'HD002',
        khachHang: { hovaten: 'Trần Thị B' },
        ngayTaoHoaDon: '2025-08-25T14:15:00',
        tongTien: 8500000,
        trangThai: 'Chưa thanh toán'
      },
      {
        id: 3,
        maHoaDon: 'HD003',
        khachHang: { hovaten: 'Lê Văn C' },
        ngayTaoHoaDon: '2025-08-24T09:20:00',
        tongTien: 12000000,
        trangThai: 'Đã hủy',
        ghiChu: 'Khách hàng yêu cầu hủy'
      }
    ];
  }

  calculateStatistics(): void {
    this.totalInvoices = this.invoices.length;
    this.totalRevenue = this.invoices
      .filter(invoice => invoice.trangThai === 'Đã thanh toán')
      .reduce((sum, invoice) => sum + invoice.tongTien, 0);
    this.paidInvoices = this.invoices.filter(invoice => invoice.trangThai === 'Đã thanh toán').length;
    this.unpaidInvoices = this.invoices.filter(invoice => invoice.trangThai === 'Chưa thanh toán').length;
  }

  applyFilters(): void {
    this.filteredInvoices = this.invoices.filter(invoice => {
      const matchesSearch = !this.searchTerm || 
        invoice.maHoaDon.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        this.getCustomerName(invoice).toLowerCase().includes(this.searchTerm.toLowerCase());

      const matchesStatus = !this.statusFilter || invoice.trangThai === this.statusFilter;

      const invoiceDate = new Date(invoice.ngayTaoHoaDon);
      const matchesDateFrom = !this.dateFrom || invoiceDate >= new Date(this.dateFrom);
      const matchesDateTo = !this.dateTo || invoiceDate <= new Date(this.dateTo);

      return matchesSearch && matchesStatus && matchesDateFrom && matchesDateTo;
    });
  }

  getCustomerName(invoice: HoaDon): string {
    return invoice.khachHang?.hovaten || 'Không có thông tin';
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'Đã thanh toán': return 'status-paid';
      case 'Chưa thanh toán': return 'status-unpaid';
      case 'Đã hủy': return 'status-cancelled';
      default: return 'status-unpaid';
    }
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

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND'
    }).format(amount);
  }

  viewInvoice(invoice: HoaDon): void {
    alert(`Xem chi tiết hóa đơn ${invoice.maHoaDon}`);
  }

  printInvoice(invoice: HoaDon): void {
    alert(`In hóa đơn ${invoice.maHoaDon}`);
  }

  refreshData(): void {
    this.loadInvoices();
  }

  exportInvoices(): void {
    alert('Tính năng xuất dữ liệu đang được phát triển!');
  }

  trackByInvoiceId(index: number, invoice: HoaDon): number {
    return invoice.id;
  }
}
