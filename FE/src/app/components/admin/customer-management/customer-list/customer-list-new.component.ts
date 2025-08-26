import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

interface KhachHang {
  id: number;
  hovaten: string;
  email: string;
  sodienthoai: string;
  diachi?: string;
  gioitinh?: string;
  ngaysinh?: string;
  trangthai: boolean;
  ngaytao?: string;
  ngaycapnhat?: string;
}

@Component({
  selector: 'app-customer-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './customer-list-new.component.html',
  styleUrls: ['./customer-list-new.component.scss']
})
export class CustomerListComponent implements OnInit {
  customers: KhachHang[] = [];
  filteredCustomers: KhachHang[] = [];
  isLoading = true;

  // Filters
  searchTerm = '';
  statusFilter = '';
  genderFilter = '';

  // Statistics
  totalCustomers = 0;
  activeCustomers = 0;
  inactiveCustomers = 0;

  // Modals
  showAddForm = false;
  showEditForm = false;
  showViewModal = false;
  showDeleteConfirm = false;
  currentCustomer: KhachHang | null = null;

  // Form data
  customerForm = {
    hovaten: '',
    email: '',
    sodienthoai: '',
    diachi: '',
    gioitinh: '',
    ngaysinh: '',
    trangthai: true
  };

  constructor(
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.loadCustomers();
  }

  loadCustomers(): void {
    this.isLoading = true;
    
    this.http.get<any>('http://localhost:8080/api/khachhang')
      .subscribe({
        next: (response) => {
          this.customers = response.data || [];
          this.calculateStatistics();
          this.applyFilters();
          this.isLoading = false;
        },
        error: (error: any) => {
          console.error('Error loading customers:', error);
          // Use mock data on error
          this.customers = this.getMockCustomers();
          this.calculateStatistics();
          this.applyFilters();
          this.isLoading = false;
        }
      });
  }

  getMockCustomers(): KhachHang[] {
    return [
      {
        id: 1,
        hovaten: 'Nguyễn Văn An',
        email: 'nguyenvanan@email.com',
        sodienthoai: '0901234567',
        diachi: '123 Đường ABC, Quận 1, TP.HCM',
        gioitinh: 'Nam',
        ngaysinh: '1990-05-15',
        trangthai: true,
        ngaytao: '2025-08-20T10:00:00',
        ngaycapnhat: '2025-08-26T14:30:00'
      },
      {
        id: 2,
        hovaten: 'Trần Thị Bình',
        email: 'tranthibinh@email.com',
        sodienthoai: '0902345678',
        diachi: '456 Đường XYZ, Quận 2, TP.HCM',
        gioitinh: 'Nữ',
        ngaysinh: '1985-12-20',
        trangthai: true,
        ngaytao: '2025-08-21T09:15:00'
      },
      {
        id: 3,
        hovaten: 'Lê Văn Cường',
        email: 'levancuong@email.com',
        sodienthoai: '0903456789',
        diachi: '789 Đường DEF, Quận 3, TP.HCM',
        gioitinh: 'Nam',
        ngaysinh: '1992-08-10',
        trangthai: false,
        ngaytao: '2025-08-22T16:45:00',
        ngaycapnhat: '2025-08-25T11:20:00'
      }
    ];
  }

  calculateStatistics(): void {
    this.totalCustomers = this.customers.length;
    this.activeCustomers = this.customers.filter(c => c.trangthai).length;
    this.inactiveCustomers = this.totalCustomers - this.activeCustomers;
  }

  applyFilters(): void {
    this.filteredCustomers = this.customers.filter(customer => {
      const matchesSearch = !this.searchTerm || 
        customer.hovaten.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        customer.email.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        customer.sodienthoai.includes(this.searchTerm);

      const matchesStatus = !this.statusFilter || 
        customer.trangthai.toString() === this.statusFilter;

      const matchesGender = !this.genderFilter || 
        customer.gioitinh === this.genderFilter;

      return matchesSearch && matchesStatus && matchesGender;
    });
  }

  onSearchChange(): void {
    this.applyFilters();
  }

  onStatusFilterChange(): void {
    this.applyFilters();
  }

  onGenderFilterChange(): void {
    this.applyFilters();
  }

  openAddForm(): void {
    this.resetForm();
    this.showAddForm = true;
  }

  openEditForm(customer: KhachHang): void {
    this.currentCustomer = customer;
    this.customerForm = {
      hovaten: customer.hovaten,
      email: customer.email,
      sodienthoai: customer.sodienthoai,
      diachi: customer.diachi || '',
      gioitinh: customer.gioitinh || '',
      ngaysinh: customer.ngaysinh || '',
      trangthai: customer.trangthai
    };
    this.showEditForm = true;
  }

  openViewModal(customer: KhachHang): void {
    this.currentCustomer = customer;
    this.showViewModal = true;
  }

  openDeleteConfirm(customer: KhachHang): void {
    this.currentCustomer = customer;
    this.showDeleteConfirm = true;
  }

  saveCustomer(): void {
    if (!this.validateForm()) return;

    const customerData = {
      hovaten: this.customerForm.hovaten,
      email: this.customerForm.email,
      sodienthoai: this.customerForm.sodienthoai,
      diachi: this.customerForm.diachi,
      gioitinh: this.customerForm.gioitinh,
      ngaysinh: this.customerForm.ngaysinh,
      trangthai: this.customerForm.trangthai
    };

    if (this.showEditForm && this.currentCustomer) {
      this.updateCustomer(this.currentCustomer.id, customerData);
    } else {
      this.createCustomer(customerData);
    }
  }

  createCustomer(customerData: any): void {
    this.http.post('http://localhost:8080/api/khachhang', customerData)
      .subscribe({
        next: () => {
          this.showNotification('Thêm khách hàng thành công!', 'success');
          this.loadCustomers();
          this.closeModals();
        },
        error: (error: any) => {
          console.error('Error creating customer:', error);
          this.showNotification('Có lỗi khi thêm khách hàng!', 'error');
        }
      });
  }

  updateCustomer(id: number, customerData: any): void {
    this.http.put(`http://localhost:8080/api/khachhang/${id}`, customerData)
      .subscribe({
        next: () => {
          this.showNotification('Cập nhật khách hàng thành công!', 'success');
          this.loadCustomers();
          this.closeModals();
        },
        error: (error: any) => {
          console.error('Error updating customer:', error);
          this.showNotification('Có lỗi khi cập nhật khách hàng!', 'error');
        }
      });
  }

  deleteCustomer(): void {
    if (!this.currentCustomer) return;

    this.http.delete(`http://localhost:8080/api/khachhang/${this.currentCustomer.id}`)
      .subscribe({
        next: () => {
          this.showNotification('Xóa khách hàng thành công!', 'success');
          this.loadCustomers();
          this.closeModals();
        },
        error: (error: any) => {
          console.error('Error deleting customer:', error);
          this.showNotification('Có lỗi khi xóa khách hàng!', 'error');
        }
      });
  }

  toggleCustomerStatus(customer: KhachHang): void {
    const newStatus = !customer.trangthai;
    
    this.http.patch(`http://localhost:8080/api/khachhang/${customer.id}/status`, { trangthai: newStatus })
      .subscribe({
        next: () => {
          customer.trangthai = newStatus;
          this.calculateStatistics();
          this.applyFilters();
          this.showNotification(`${newStatus ? 'Kích hoạt' : 'Vô hiệu hóa'} khách hàng thành công!`, 'success');
        },
        error: (error: any) => {
          console.error('Error updating customer status:', error);
          this.showNotification('Có lỗi khi cập nhật trạng thái!', 'error');
        }
      });
  }

  validateForm(): boolean {
    if (!this.customerForm.hovaten.trim()) {
      this.showNotification('Vui lòng nhập họ tên!', 'error');
      return false;
    }
    if (!this.customerForm.email.trim()) {
      this.showNotification('Vui lòng nhập email!', 'error');
      return false;
    }
    if (!this.customerForm.sodienthoai.trim()) {
      this.showNotification('Vui lòng nhập số điện thoại!', 'error');
      return false;
    }
    return true;
  }

  resetForm(): void {
    this.customerForm = {
      hovaten: '',
      email: '',
      sodienthoai: '',
      diachi: '',
      gioitinh: '',
      ngaysinh: '',
      trangthai: true
    };
    this.currentCustomer = null;
  }

  closeModals(): void {
    this.showAddForm = false;
    this.showEditForm = false;
    this.showViewModal = false;
    this.showDeleteConfirm = false;
    this.currentCustomer = null;
  }

  formatDate(dateString?: string): string {
    if (!dateString) return 'Chưa có';
    return new Date(dateString).toLocaleDateString('vi-VN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit'
    });
  }

  getStatusClass(status: boolean): string {
    return status ? 'status-active' : 'status-inactive';
  }

  getStatusLabel(status: boolean): string {
    return status ? 'Hoạt động' : 'Vô hiệu hóa';
  }

  getGenderLabel(gender?: string): string {
    if (!gender) return 'Chưa xác định';
    return gender === 'Nam' ? 'Nam' : gender === 'Nữ' ? 'Nữ' : 'Khác';
  }

  getActivePercentage(): number {
    if (this.totalCustomers === 0) return 0;
    return Math.round((this.activeCustomers / this.totalCustomers) * 100);
  }

  refreshData(): void {
    this.loadCustomers();
  }

  exportCustomers(): void {
    this.showNotification('Tính năng xuất dữ liệu đang được phát triển!', 'info');
  }

  trackByCustomerId(index: number, customer: KhachHang): number {
    return customer.id;
  }

  private showNotification(message: string, type: 'success' | 'error' | 'info'): void {
    alert(message);
  }
}
