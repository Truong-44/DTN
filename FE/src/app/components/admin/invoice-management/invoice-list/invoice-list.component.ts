import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HoaDon } from '../../../../core/models/hoadon.model';
import { KhachHang } from '../../../../core/models/khachhang.model';
import { ApiService } from '../../../../core/services/api.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { catchError, forkJoin, of } from 'rxjs';

@Component({
  selector: 'app-invoice-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './invoice-list.component.html',
  styleUrls: ['./invoice-list.component.scss'],
})
export class InvoiceListComponent implements OnInit {
  invoices: (HoaDon & { khachhang?: KhachHang; trangthai?: string })[] = [];
  filteredInvoices: (HoaDon & { khachhang?: KhachHang; trangthai?: string })[] =
    [];
  loading = false;

  searchKeyword = '';
  statusFilter = '';
  dateFilter = '';

  totalInvoices = 0;
  paidInvoices = 0;
  unpaidInvoices = 0;
  totalAmount = 0;

  constructor(
    private apiService: ApiService,
    private notificationService: NotificationService
  ) {}

  ngOnInit() {
    this.loadInvoices();
  }

  loadInvoices() {
    this.loading = true;

    // Load invoices and orders from backend
    forkJoin({
      hoaDons: this.apiService.get<HoaDon[]>('/api/hoadon').pipe(
        catchError((error) => {
          console.error('Error loading invoices:', error);
          return of([]);
        })
      ),
      donHangs: this.apiService.get<any[]>('/api/donhang').pipe(
        catchError((error) => {
          console.error('Error loading orders:', error);
          return of([]);
        })
      ),
    }).subscribe({
      next: (data) => {
        // Combine invoice data with order data to get customer info
        this.invoices = data.hoaDons.map((hoaDon) => {
          const donHang = data.donHangs.find(
            (dh) => dh.id === hoaDon.donhangid
          );
          return {
            ...hoaDon,
            khachhang: donHang?.khachhang,
            trangthai: donHang?.trangthaithanhtoan ? 'PAID' : 'UNPAID',
          };
        });
        this.calculateStats();
        this.applyFilters();
        this.loading = false;
      },
      error: (error) => {
        console.error('Error loading invoice data:', error);
        this.notificationService.error('Lỗi', 'Không thể tải dữ liệu hóa đơn');
        this.loading = false;
      },
    });
  }

  applyFilters() {
    this.filteredInvoices = this.invoices.filter((invoice) => {
      const matchesSearch =
        !this.searchKeyword ||
        invoice.id?.toString().includes(this.searchKeyword) ||
        invoice.khachhang?.hoten
          ?.toLowerCase()
          .includes(this.searchKeyword.toLowerCase());

      const matchesStatus =
        !this.statusFilter || invoice.trangthai === this.statusFilter;

      const matchesDate =
        !this.dateFilter ||
        invoice.ngayxuathoadon?.toString().includes(this.dateFilter);

      return matchesSearch && matchesStatus && matchesDate;
    });
  }

  calculateStats() {
    this.totalInvoices = this.invoices.length;
    this.paidInvoices = this.invoices.filter(
      (i) => i.trangthai === 'PAID'
    ).length;
    this.unpaidInvoices = this.invoices.filter(
      (i) => i.trangthai !== 'PAID'
    ).length;
    this.totalAmount = this.invoices.reduce(
      (sum, i) => sum + (i.tongtien || 0),
      0
    );
  }

  createInvoice() {
    // Navigate to invoice creation page or open modal
    this.notificationService.info(
      'Thông báo',
      'Tính năng tạo hóa đơn đang được phát triển'
    );
  }

  viewInvoice(invoice: HoaDon) {
    // Navigate to invoice detail page
    this.notificationService.info(
      'Thông báo',
      `Xem chi tiết hóa đơn #${invoice.id}`
    );
  }

  printInvoice(invoice: HoaDon) {
    // Print invoice functionality
    this.notificationService.info('Thông báo', `In hóa đơn #${invoice.id}`);
  }

  markAsPaid(invoice: HoaDon & { trangthai?: string }) {
    if (confirm('Xác nhận đánh dấu hóa đơn này đã thanh toán?')) {
      // Update the order's payment status instead of invoice
      this.apiService
        .put(`/api/donhang/${invoice.donhangid}`, {
          trangthaithanhtoan: true,
        })
        .subscribe({
          next: () => {
            invoice.trangthai = 'PAID';
            this.calculateStats();
            this.applyFilters();
            this.notificationService.success(
              'Thành công',
              'Đã cập nhật trạng thái hóa đơn'
            );
          },
          error: (error) => {
            console.error('Error updating payment status:', error);
            this.notificationService.error(
              'Lỗi',
              'Không thể cập nhật trạng thái thanh toán'
            );
          },
        });
    }
  }

  deleteInvoice(invoice: HoaDon) {
    if (
      confirm(
        'Bạn có chắc muốn xóa hóa đơn này? Hành động này không thể hoàn tác.'
      )
    ) {
      this.apiService.delete(`/api/hoadon/${invoice.id}`).subscribe({
        next: () => {
          this.invoices = this.invoices.filter((i) => i.id !== invoice.id);
          this.calculateStats();
          this.applyFilters();
          this.notificationService.success('Thành công', 'Đã xóa hóa đơn');
        },
        error: (error) => {
          console.error('Error deleting invoice:', error);
          this.notificationService.error('Lỗi', 'Không thể xóa hóa đơn');
        },
      });
    }
  }

  formatDate(dateString: string | Date | undefined): string {
    if (!dateString) return 'N/A';
    return new Date(dateString).toLocaleDateString('vi-VN');
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(amount);
  }

  getStatusLabel(status: string): string {
    const statusMap: { [key: string]: string } = {
      PAID: 'Đã thanh toán',
      UNPAID: 'Chưa thanh toán',
      OVERDUE: 'Quá hạn',
    };
    return statusMap[status] || 'Chưa thanh toán';
  }
}
