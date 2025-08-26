import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

interface NhanVien {
  id: number;
  hovaten: string;
  email: string;
  sodienthoai: string;
  diachi?: string;
  gioitinh?: string;
  ngaysinh?: string;
  chucvu?: string;
  luong?: number;
  trangthai: boolean;
  ngaytao?: string;
  ngaycapnhat?: string;
}

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employee-list-new.component.html',
  styleUrls: ['./employee-list-new.component.scss']
})
export class EmployeeListComponent implements OnInit {
  employees: NhanVien[] = [];
  filteredEmployees: NhanVien[] = [];
  isLoading = true;

  // Filters
  searchTerm = '';
  statusFilter = '';
  positionFilter = '';

  // Statistics
  totalEmployees = 0;
  activeEmployees = 0;
  inactiveEmployees = 0;
  averageSalary = 0;

  // Modals
  showAddForm = false;
  showEditForm = false;
  showViewModal = false;
  showDeleteConfirm = false;
  currentEmployee: NhanVien | null = null;

  // Form data
  employeeForm = {
    hovaten: '',
    email: '',
    sodienthoai: '',
    diachi: '',
    gioitinh: '',
    ngaysinh: '',
    chucvu: '',
    luong: 0,
    trangthai: true
  };

  constructor(
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.loadEmployees();
  }

  loadEmployees(): void {
    this.isLoading = true;
    
    this.http.get<any>('http://localhost:8080/api/nhanvien')
      .subscribe({
        next: (response) => {
          this.employees = response.data || [];
          this.calculateStatistics();
          this.applyFilters();
          this.isLoading = false;
        },
        error: (error: any) => {
          console.error('Error loading employees:', error);
          // Use mock data on error
          this.employees = this.getMockEmployees();
          this.calculateStatistics();
          this.applyFilters();
          this.isLoading = false;
        }
      });
  }

  getMockEmployees(): NhanVien[] {
    return [
      {
        id: 1,
        hovaten: 'Nguyễn Văn Admin',
        email: 'admin@noithat.com',
        sodienthoai: '0901234567',
        diachi: '123 Đường ABC, Quận 1, TP.HCM',
        gioitinh: 'Nam',
        ngaysinh: '1985-03-15',
        chucvu: 'Quản lý',
        luong: 20000000,
        trangthai: true,
        ngaytao: '2025-01-01T09:00:00',
        ngaycapnhat: '2025-08-26T14:30:00'
      },
      {
        id: 2,
        hovaten: 'Trần Thị Bán hàng',
        email: 'banhang@noithat.com',
        sodienthoai: '0902345678',
        diachi: '456 Đường XYZ, Quận 2, TP.HCM',
        gioitinh: 'Nữ',
        ngaysinh: '1990-07-20',
        chucvu: 'Nhân viên bán hàng',
        luong: 12000000,
        trangthai: true,
        ngaytao: '2025-01-15T10:30:00'
      },
      {
        id: 3,
        hovaten: 'Lê Văn Kho',
        email: 'kho@noithat.com',
        sodienthoai: '0903456789',
        diachi: '789 Đường DEF, Quận 3, TP.HCM',
        gioitinh: 'Nam',
        ngaysinh: '1988-12-10',
        chucvu: 'Nhân viên kho',
        luong: 10000000,
        trangthai: false,
        ngaytao: '2025-02-01T08:15:00',
        ngaycapnhat: '2025-08-20T16:45:00'
      }
    ];
  }

  calculateStatistics(): void {
    this.totalEmployees = this.employees.length;
    this.activeEmployees = this.employees.filter(e => e.trangthai).length;
    this.inactiveEmployees = this.totalEmployees - this.activeEmployees;
    
    const totalSalary = this.employees.reduce((sum, emp) => sum + (emp.luong || 0), 0);
    this.averageSalary = this.totalEmployees > 0 ? Math.round(totalSalary / this.totalEmployees) : 0;
  }

  applyFilters(): void {
    this.filteredEmployees = this.employees.filter(employee => {
      const matchesSearch = !this.searchTerm || 
        employee.hovaten.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        employee.email.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        employee.sodienthoai.includes(this.searchTerm) ||
        (employee.chucvu && employee.chucvu.toLowerCase().includes(this.searchTerm.toLowerCase()));

      const matchesStatus = !this.statusFilter || 
        employee.trangthai.toString() === this.statusFilter;

      const matchesPosition = !this.positionFilter || 
        employee.chucvu === this.positionFilter;

      return matchesSearch && matchesStatus && matchesPosition;
    });
  }

  onSearchChange(): void {
    this.applyFilters();
  }

  onStatusFilterChange(): void {
    this.applyFilters();
  }

  onPositionFilterChange(): void {
    this.applyFilters();
  }

  getUniquePositions(): string[] {
    const positions = this.employees
      .map(emp => emp.chucvu)
      .filter(pos => pos && pos.trim() !== '')
      .filter((pos, index, arr) => arr.indexOf(pos) === index);
    return positions as string[];
  }

  openAddForm(): void {
    this.resetForm();
    this.showAddForm = true;
  }

  openEditForm(employee: NhanVien): void {
    this.currentEmployee = employee;
    this.employeeForm = {
      hovaten: employee.hovaten,
      email: employee.email,
      sodienthoai: employee.sodienthoai,
      diachi: employee.diachi || '',
      gioitinh: employee.gioitinh || '',
      ngaysinh: employee.ngaysinh || '',
      chucvu: employee.chucvu || '',
      luong: employee.luong || 0,
      trangthai: employee.trangthai
    };
    this.showEditForm = true;
  }

  openViewModal(employee: NhanVien): void {
    this.currentEmployee = employee;
    this.showViewModal = true;
  }

  openDeleteConfirm(employee: NhanVien): void {
    this.currentEmployee = employee;
    this.showDeleteConfirm = true;
  }

  saveEmployee(): void {
    if (!this.validateForm()) return;

    const employeeData = {
      hovaten: this.employeeForm.hovaten,
      email: this.employeeForm.email,
      sodienthoai: this.employeeForm.sodienthoai,
      diachi: this.employeeForm.diachi,
      gioitinh: this.employeeForm.gioitinh,
      ngaysinh: this.employeeForm.ngaysinh,
      chucvu: this.employeeForm.chucvu,
      luong: this.employeeForm.luong,
      trangthai: this.employeeForm.trangthai
    };

    if (this.showEditForm && this.currentEmployee) {
      this.updateEmployee(this.currentEmployee.id, employeeData);
    } else {
      this.createEmployee(employeeData);
    }
  }

  createEmployee(employeeData: any): void {
    this.http.post('http://localhost:8080/api/nhanvien', employeeData)
      .subscribe({
        next: () => {
          this.showNotification('Thêm nhân viên thành công!', 'success');
          this.loadEmployees();
          this.closeModals();
        },
        error: (error: any) => {
          console.error('Error creating employee:', error);
          this.showNotification('Có lỗi khi thêm nhân viên!', 'error');
        }
      });
  }

  updateEmployee(id: number, employeeData: any): void {
    this.http.put(`http://localhost:8080/api/nhanvien/${id}`, employeeData)
      .subscribe({
        next: () => {
          this.showNotification('Cập nhật nhân viên thành công!', 'success');
          this.loadEmployees();
          this.closeModals();
        },
        error: (error: any) => {
          console.error('Error updating employee:', error);
          this.showNotification('Có lỗi khi cập nhật nhân viên!', 'error');
        }
      });
  }

  deleteEmployee(): void {
    if (!this.currentEmployee) return;

    this.http.delete(`http://localhost:8080/api/nhanvien/${this.currentEmployee.id}`)
      .subscribe({
        next: () => {
          this.showNotification('Xóa nhân viên thành công!', 'success');
          this.loadEmployees();
          this.closeModals();
        },
        error: (error: any) => {
          console.error('Error deleting employee:', error);
          this.showNotification('Có lỗi khi xóa nhân viên!', 'error');
        }
      });
  }

  toggleEmployeeStatus(employee: NhanVien): void {
    const newStatus = !employee.trangthai;
    
    this.http.patch(`http://localhost:8080/api/nhanvien/${employee.id}/status`, { trangthai: newStatus })
      .subscribe({
        next: () => {
          employee.trangthai = newStatus;
          this.calculateStatistics();
          this.applyFilters();
          this.showNotification(`${newStatus ? 'Kích hoạt' : 'Vô hiệu hóa'} nhân viên thành công!`, 'success');
        },
        error: (error: any) => {
          console.error('Error updating employee status:', error);
          this.showNotification('Có lỗi khi cập nhật trạng thái!', 'error');
        }
      });
  }

  validateForm(): boolean {
    if (!this.employeeForm.hovaten.trim()) {
      this.showNotification('Vui lòng nhập họ tên!', 'error');
      return false;
    }
    if (!this.employeeForm.email.trim()) {
      this.showNotification('Vui lòng nhập email!', 'error');
      return false;
    }
    if (!this.employeeForm.sodienthoai.trim()) {
      this.showNotification('Vui lòng nhập số điện thoại!', 'error');
      return false;
    }
    if (!this.employeeForm.chucvu.trim()) {
      this.showNotification('Vui lòng nhập chức vụ!', 'error');
      return false;
    }
    return true;
  }

  resetForm(): void {
    this.employeeForm = {
      hovaten: '',
      email: '',
      sodienthoai: '',
      diachi: '',
      gioitinh: '',
      ngaysinh: '',
      chucvu: '',
      luong: 0,
      trangthai: true
    };
    this.currentEmployee = null;
  }

  closeModals(): void {
    this.showAddForm = false;
    this.showEditForm = false;
    this.showViewModal = false;
    this.showDeleteConfirm = false;
    this.currentEmployee = null;
  }

  formatDate(dateString?: string): string {
    if (!dateString) return 'Chưa có';
    return new Date(dateString).toLocaleDateString('vi-VN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit'
    });
  }

  formatCurrency(amount?: number): string {
    if (!amount) return '0 ₫';
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND'
    }).format(amount);
  }

  getStatusClass(status: boolean): string {
    return status ? 'status-active' : 'status-inactive';
  }

  getStatusLabel(status: boolean): string {
    return status ? 'Đang làm việc' : 'Nghỉ việc';
  }

  getGenderLabel(gender?: string): string {
    if (!gender) return 'Chưa xác định';
    return gender === 'Nam' ? 'Nam' : gender === 'Nữ' ? 'Nữ' : 'Khác';
  }

  getActivePercentage(): number {
    if (this.totalEmployees === 0) return 0;
    return Math.round((this.activeEmployees / this.totalEmployees) * 100);
  }

  refreshData(): void {
    this.loadEmployees();
  }

  exportEmployees(): void {
    this.showNotification('Tính năng xuất dữ liệu đang được phát triển!', 'info');
  }

  trackByEmployeeId(index: number, employee: NhanVien): number {
    return employee.id;
  }

  private showNotification(message: string, type: 'success' | 'error' | 'info'): void {
    alert(message);
  }
}
