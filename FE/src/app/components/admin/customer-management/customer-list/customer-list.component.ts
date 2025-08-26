import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { KhachHangService } from '../../../../core/services/khachhang.service';
import { KhachHang } from '../../../../core/models/khachhang.model';
import { NotificationService } from '../../../../core/services/notification.service';
import { LoadingService } from '../../../../core/services/loading.service';

@Component({
  selector: 'app-customer-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.scss'],
})
export class CustomerListComponent implements OnInit {
  customers: KhachHang[] = [];
  filteredCustomers: KhachHang[] = [];
  loading = false;

  // Filters
  searchTerm = '';
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
  selectedCustomer: KhachHang | null = null;

  // Form data
  newCustomer: Partial<KhachHang> = {
    hoten: '',
    diachi: '',
    gioitinh: 'Nam',
  };
  editError = '';
  addError = '';

  constructor(
    private router: Router,
    private khachHangService: KhachHangService,
    private notificationService: NotificationService,
    private loadingService: LoadingService
  ) {}

  ngOnInit() {
    this.loadCustomers();
  }

  loadCustomers() {
    this.loading = true;
    this.loadingService.show();

    this.khachHangService
      .getAll()
      .pipe(
        catchError((error) => {
          console.error('Error loading customers:', error);
          this.notificationService.error(
            'Lỗi',
            'Không thể tải dữ liệu khách hàng'
          );
          return of([]);
        })
      )
      .subscribe({
        next: (customers) => {
          this.customers = customers;
          this.applyFilters();
          this.loading = false;
          this.loadingService.hide();
        },
        error: (error: any) => {
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
        customer.diachi
          ?.toLowerCase()
          .includes(this.searchTerm.toLowerCase()) ||
        customer.tendangnhap
          ?.toLowerCase()
          .includes(this.searchTerm.toLowerCase());

      const matchesGender =
        !this.genderFilter || customer.gioitinh === this.genderFilter;

      return matchesSearch && matchesGender;
    });

    this.totalPages = Math.ceil(this.filteredCustomers.length / this.pageSize);
    this.currentPage = 1;
  }

  get paginatedCustomers() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    return this.filteredCustomers.slice(startIndex, endIndex);
  }

  onSearchChange() {
    this.applyFilters();
  }

  onGenderFilterChange() {
    this.applyFilters();
  }

  goToPage(page: number) {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }

  viewCustomerDetail(customer: KhachHang) {
    this.selectedCustomer = customer;
    this.showDetailModal = true;
  }

  editCustomer(customer: KhachHang) {
    this.selectedCustomer = { ...customer };
    this.showEditModal = true;
    this.editError = '';
  }

  updateCustomer() {
    if (!this.selectedCustomer || !this.selectedCustomer.hoten?.trim()) {
      this.editError = 'Vui lòng nhập họ tên';
      return;
    }

    this.loadingService.show();

    this.khachHangService
      .update(this.selectedCustomer.id, this.selectedCustomer)
      .subscribe({
        next: () => {
          this.notificationService.success(
            'Thành công',
            'Đã cập nhật thông tin khách hàng'
          );
          this.showEditModal = false;
          this.loadCustomers();
        },
        error: (error: any) => {
          console.error('Error updating customer:', error);
          this.editError = 'Không thể cập nhật khách hàng';
          this.loadingService.hide();
        },
      });
  }

  deleteCustomer(customer: KhachHang) {
    this.selectedCustomer = customer;
    this.showDeleteModal = true;
  }

  confirmDelete() {
    if (!this.selectedCustomer) return;

    this.loadingService.show();

    this.khachHangService.delete(this.selectedCustomer.id).subscribe({
      next: () => {
        this.notificationService.success('Thành công', 'Đã xóa khách hàng');
        this.showDeleteModal = false;
        this.loadCustomers();
      },
      error: (error: any) => {
        console.error('Error deleting customer:', error);
        this.notificationService.error('Lỗi', 'Không thể xóa khách hàng');
        this.loadingService.hide();
      },
    });
  }

  addCustomer() {
    if (!this.newCustomer.hoten?.trim()) {
      this.addError = 'Vui lòng nhập họ tên';
      return;
    }

    this.loadingService.show();

    this.khachHangService.create(this.newCustomer).subscribe({
      next: () => {
        this.notificationService.success(
          'Thành công',
          'Đã thêm khách hàng mới'
        );
        this.showAddModal = false;
        this.resetNewCustomer();
        this.loadCustomers();
      },
      error: (error: any) => {
        console.error('Error creating customer:', error);
        this.addError = 'Không thể thêm khách hàng';
        this.loadingService.hide();
      },
    });
  }

  // Modal methods
  openDetailModal(customer: KhachHang) {
    this.viewCustomerDetail(customer);
  }

  openEditModal(customer: KhachHang) {
    this.editCustomer(customer);
  }

  openAddModal() {
    this.resetNewCustomer();
    this.showAddModal = true;
    this.addError = '';
  }

  closeModal() {
    this.showDetailModal = false;
    this.showEditModal = false;
    this.showAddModal = false;
    this.showDeleteModal = false;
    this.selectedCustomer = null;
    this.editError = '';
    this.addError = '';
  }

  resetNewCustomer() {
    this.newCustomer = {
      hoten: '',
      diachi: '',
      gioitinh: 'Nam',
    };
  }

  formatDate(date: Date | string | undefined): string {
    if (!date) return 'Chưa có';

    try {
      const d = typeof date === 'string' ? new Date(date) : date;
      return d.toLocaleDateString('vi-VN');
    } catch {
      return 'Không hợp lệ';
    }
  }

  getGenderDisplay(gender: string | undefined): string {
    return gender || 'Chưa xác định';
  }

  // Utility method for generating initials (used in template)
  getInitials(name: string | undefined): string {
    if (!name) return '?';

    const words = name.trim().split(' ');
    if (words.length === 1) {
      return words[0].charAt(0).toUpperCase();
    }

    return (
      words[0].charAt(0) + words[words.length - 1].charAt(0)
    ).toUpperCase();
  }

  // Utility method for tracking table rows (performance optimization)
  trackByCustomerId(index: number, customer: KhachHang): number {
    return customer.id;
  }

  // Navigation methods
  navigateToDetail(customerId: number) {
    this.router.navigate(['/admin/customer-management/detail', customerId]);
  }

  navigateToEdit(customerId: number) {
    this.router.navigate(['/admin/customer-management/edit', customerId]);
  }

  navigateToAdd() {
    this.router.navigate(['/admin/customer-management/add']);
  }
}
