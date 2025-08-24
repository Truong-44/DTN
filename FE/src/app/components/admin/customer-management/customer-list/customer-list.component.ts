import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { TaiKhoanService } from '../../../../core/services/taikhoan.service';
import { KhachHang } from '../../../../core/models/khachhang.model';
import { TaiKhoan } from '../../../../core/models/taikhoan.model';
import { NotificationService } from '../../../../core/services/notification.service';
import { LoadingService } from '../../../../core/services/loading.service';

@Component({
  selector: 'app-customer-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.scss'],
})
export class CustomerListComponent implements OnInit {
  customers: (KhachHang & { taikhoan?: TaiKhoan })[] = [];
  filteredCustomers: (KhachHang & { taikhoan?: TaiKhoan })[] = [];
  loading = false;

  // Filters
  searchTerm = '';
  statusFilter = '';
  genderFilter = '';

  // Pagination
  currentPage = 1;
  pageSize = 10;
  totalPages = 0;

  // Modal states
  showDetailModal = false;
  showEditModal = false;
  showAddModal = false;
  showDeleteModal = false;
  selectedCustomer: (KhachHang & { taikhoan?: TaiKhoan }) | null = null;

  // Form data
  newCustomer: Partial<KhachHang> = {};
  editError = '';
  addError = '';

  constructor(
    private taiKhoanService: TaiKhoanService,
    private notificationService: NotificationService,
    private loadingService: LoadingService
  ) {}

  ngOnInit() {
    this.loadCustomers();
  }

  loadCustomers() {
    this.loading = true;
    this.loadingService.show();

    forkJoin({
      khachhangs: this.taiKhoanService.getAllKhachHang().pipe(
        catchError((error) => {
          console.error('Error loading customers:', error);
          return of([]);
        })
      ),
      taikhoans: this.taiKhoanService.getAllTaiKhoan().pipe(
        catchError((error) => {
          console.error('Error loading accounts:', error);
          return of([]);
        })
      ),
    }).subscribe({
      next: ({ khachhangs, taikhoans }) => {
        // Handle different response types
        const customerData = Array.isArray(khachhangs)
          ? khachhangs
          : (khachhangs as any).items || [];
        const accountData = Array.isArray(taikhoans)
          ? taikhoans
          : (taikhoans as any).items || [];

        this.customers = customerData.map((customer: KhachHang) => ({
          ...customer,
          taikhoan: accountData.find(
            (account: TaiKhoan) => account.id === customer.taikhoanid
          ),
        }));

        this.applyFilters();
        this.loading = false;
        this.loadingService.hide();
      },
      error: (error) => {
        console.error('Error loading customer data:', error);
        this.notificationService.error(
          'Lỗi',
          'Không thể tải dữ liệu khách hàng'
        );
        this.loading = false;
        this.loadingService.hide();
      },
    });
  }

  applyFilters() {
    this.filteredCustomers = this.customers.filter((customer) => {
      const matchesSearch =
        !this.searchTerm ||
        customer.hoten?.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        customer.taikhoan?.email
          ?.toLowerCase()
          .includes(this.searchTerm.toLowerCase()) ||
        customer.taikhoan?.sodienthoai?.includes(this.searchTerm);

      const matchesStatus =
        !this.statusFilter ||
        (this.statusFilter === 'active' && customer.taikhoan?.trangthai) ||
        (this.statusFilter === 'inactive' && !customer.taikhoan?.trangthai);

      const matchesGender =
        !this.genderFilter || customer.gioitinh === this.genderFilter;

      return matchesSearch && matchesStatus && matchesGender;
    });

    this.totalPages = Math.ceil(this.filteredCustomers.length / this.pageSize);
    this.currentPage = 1;
  }

  onSearchChange() {
    this.applyFilters();
  }

  onStatusFilterChange() {
    this.applyFilters();
  }

  onGenderFilterChange() {
    this.applyFilters();
  }

  getPaginatedCustomers() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    return this.filteredCustomers.slice(startIndex, endIndex);
  }

  goToPage(page: number) {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }

  viewCustomerDetail(customer: KhachHang & { taikhoan?: TaiKhoan }) {
    this.selectedCustomer = customer;
    this.showDetailModal = true;
  }

  editCustomer(customer: KhachHang & { taikhoan?: TaiKhoan }) {
    this.selectedCustomer = { ...customer };
    this.showEditModal = true;
  }

  updateCustomer() {
    if (!this.selectedCustomer || !this.selectedCustomer.hoten) {
      this.notificationService.error('Lỗi', 'Vui lòng nhập họ tên');
      return;
    }

    this.loadingService.show();

    const updateData = {
      hoten: this.selectedCustomer.hoten,
      diachi: this.selectedCustomer.diachi,
      ngaysinh: this.selectedCustomer.ngaysinh,
      gioitinh: this.selectedCustomer.gioitinh,
    };

    this.taiKhoanService
      .updateKhachHang(this.selectedCustomer.id, updateData)
      .subscribe({
        next: () => {
          // Update account info if exists
          if (this.selectedCustomer?.taikhoan?.id) {
            const accountUpdateData = {
              email: this.selectedCustomer.taikhoan.email,
              sodienthoai: this.selectedCustomer.taikhoan.sodienthoai,
            };

            this.taiKhoanService
              .updateTaiKhoan(
                this.selectedCustomer.taikhoan.id,
                accountUpdateData
              )
              .subscribe({
                next: () => {
                  this.notificationService.success(
                    'Thành công',
                    'Đã cập nhật thông tin khách hàng'
                  );
                  this.showEditModal = false;
                  this.loadCustomers();
                },
                error: (error) => {
                  console.error('Error updating account:', error);
                  this.notificationService.error(
                    'Lỗi',
                    'Không thể cập nhật tài khoản'
                  );
                  this.loadingService.hide();
                },
              });
          } else {
            this.notificationService.success(
              'Thành công',
              'Đã cập nhật thông tin khách hàng'
            );
            this.showEditModal = false;
            this.loadCustomers();
          }
        },
        error: (error) => {
          console.error('Error updating customer:', error);
          this.notificationService.error(
            'Lỗi',
            'Không thể cập nhật khách hàng'
          );
          this.loadingService.hide();
        },
      });
  }

  toggleCustomerStatus(customer: KhachHang & { taikhoan?: TaiKhoan }) {
    if (!customer.taikhoan?.id) {
      this.notificationService.error('Lỗi', 'Không tìm thấy tài khoản');
      return;
    }

    const action = customer.taikhoan.trangthai ? 'khóa' : 'mở khóa';
    if (confirm(`Bạn có chắc muốn ${action} tài khoản này?`)) {
      const updateData = { trangthai: !customer.taikhoan.trangthai };

      this.taiKhoanService
        .updateTaiKhoan(customer.taikhoan.id, updateData)
        .subscribe({
          next: () => {
            customer.taikhoan!.trangthai = !customer.taikhoan!.trangthai;
            this.notificationService.success(
              'Thành công',
              `Đã ${action} tài khoản`
            );
          },
          error: (error) => {
            console.error('Error updating account status:', error);
            this.notificationService.error(
              'Lỗi',
              `Không thể ${action} tài khoản`
            );
          },
        });
    }
  }

  closeModal() {
    this.showDetailModal = false;
    this.showEditModal = false;
    this.showAddModal = false;
    this.showDeleteModal = false;
    this.selectedCustomer = null;
    this.newCustomer = {};
    this.editError = '';
    this.addError = '';
  }

  openAddModal() {
    this.newCustomer = {};
    this.addError = '';
    this.showAddModal = true;
  }

  openDetailModal(customer: KhachHang & { taikhoan?: TaiKhoan }) {
    this.selectedCustomer = customer;
    this.showDetailModal = true;
  }

  openEditModal(customer: KhachHang & { taikhoan?: TaiKhoan }) {
    this.selectedCustomer = { ...customer };
    this.editError = '';
    this.showEditModal = true;
  }

  addCustomer() {
    if (!this.newCustomer.hoten?.trim()) {
      this.addError = 'Vui lòng nhập họ tên';
      return;
    }

    this.loadingService.show();

    this.taiKhoanService.createKhachHang(this.newCustomer).subscribe({
      next: () => {
        this.notificationService.success(
          'Thành công',
          'Đã thêm khách hàng mới'
        );
        this.closeModal();
        this.loadCustomers();
      },
      error: (error) => {
        console.error('Error creating customer:', error);
        this.addError = 'Không thể thêm khách hàng';
        this.loadingService.hide();
      },
    });
  }

  deleteCustomer(customerId: number) {
    this.selectedCustomer =
      this.customers.find((c) => c.id === customerId) || null;
    this.showDeleteModal = true;
  }

  confirmDeleteCustomer() {
    if (!this.selectedCustomer) return;

    this.loadingService.show();

    this.taiKhoanService.deleteKhachHang(this.selectedCustomer.id).subscribe({
      next: () => {
        this.notificationService.success('Thành công', 'Đã xóa khách hàng');
        this.closeModal();
        this.loadCustomers();
      },
      error: (error) => {
        console.error('Error deleting customer:', error);
        this.notificationService.error('Lỗi', 'Không thể xóa khách hàng');
        this.loadingService.hide();
      },
    });
  }

  formatDate(date: string | Date | undefined): string {
    if (!date) return 'N/A';
    return new Date(date).toLocaleDateString('vi-VN');
  }

  getGenderLabel(gender: string | undefined): string {
    const genderMap: { [key: string]: string } = {
      Nam: 'Nam',
      Nữ: 'Nữ',
      Khác: 'Khác',
    };
    return genderMap[gender || ''] || 'N/A';
  }
}
