import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NhanVien } from '../../../../core/models/nhanvien.model';
import { TaiKhoan } from '../../../../core/models/taikhoan.model';
import { TaiKhoanService } from '../../../../core/services/taikhoan.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { catchError, forkJoin, of } from 'rxjs';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.scss'],
})
export class EmployeeListComponent implements OnInit {
  employees: (NhanVien & { taikhoan?: TaiKhoan })[] = [];
  filteredEmployees: (NhanVien & { taikhoan?: TaiKhoan })[] = [];
  loading = false;
  searchKeyword = '';
  roleFilter = '';

  showAddModal = false;
  showEditModal = false;

  newEmployee = {
    hoten: '',
    email: '',
    tendn: '',
    matkhau: '',
    sodienthoai: '',
    vaitro: '',
  };

  editingEmployee: any = {};

  constructor(
    private taiKhoanService: TaiKhoanService,
    private notificationService: NotificationService
  ) {}

  ngOnInit() {
    this.loadEmployees();
  }

  loadEmployees() {
    this.loading = true;

    // Load both accounts and employees from backend
    forkJoin({
      taiKhoans: this.taiKhoanService.getAllTaiKhoan().pipe(
        catchError((error) => {
          console.error('Error loading accounts:', error);
          return of([]);
        })
      ),
      nhanViens: this.taiKhoanService.getAllNhanVien().pipe(
        catchError((error) => {
          console.error('Error loading employees:', error);
          return of([]);
        })
      ),
    }).subscribe({
      next: (data) => {
        // Combine employee data with account data
        this.employees = data.nhanViens.map((nhanVien) => {
          const taiKhoan = data.taiKhoans.find(
            (tk) => tk.id === nhanVien.taikhoanid
          );
          return {
            ...nhanVien,
            taikhoan: taiKhoan,
          };
        });
        this.applyFilters();
        this.loading = false;
      },
      error: (error) => {
        console.error('Error loading employee data:', error);
        this.notificationService.error(
          'Lỗi',
          'Không thể tải dữ liệu nhân viên'
        );
        this.loading = false;
      },
    });
  }

  applyFilters() {
    this.filteredEmployees = this.employees.filter((emp) => {
      const matchesSearch =
        !this.searchKeyword ||
        emp.hoten.toLowerCase().includes(this.searchKeyword.toLowerCase()) ||
        emp.email?.toLowerCase().includes(this.searchKeyword.toLowerCase()) ||
        emp.taikhoan?.tendangnhap
          ?.toLowerCase()
          .includes(this.searchKeyword.toLowerCase());

      const matchesRole = !this.roleFilter || emp.chucvu === this.roleFilter;

      return matchesSearch && matchesRole;
    });
  }

  addEmployee() {
    if (
      !this.newEmployee.hoten ||
      !this.newEmployee.tendn ||
      !this.newEmployee.matkhau
    ) {
      this.notificationService.error(
        'Lỗi',
        'Vui lòng nhập đầy đủ thông tin bắt buộc'
      );
      return;
    }

    this.loading = true;

    // Create account first
    const taiKhoanData = {
      tendangnhap: this.newEmployee.tendn,
      matkhau: this.newEmployee.matkhau,
      email: this.newEmployee.email,
      sodienthoai: this.newEmployee.sodienthoai,
      trangthai: true,
      quyenid: this.getQuyenIdFromRole(this.newEmployee.vaitro),
      loaidangnhap: 'normal',
    };

    this.taiKhoanService.createTaiKhoan(taiKhoanData).subscribe({
      next: (taiKhoan) => {
        // Create employee with the new account ID
        const nhanVienData = {
          hoten: this.newEmployee.hoten,
          email: this.newEmployee.email,
          sodienthoai: this.newEmployee.sodienthoai,
          chucvu: this.newEmployee.vaitro,
          ngayvaolam: new Date(),
          taikhoanid: taiKhoan.id,
        };

        this.taiKhoanService.createNhanVien(nhanVienData).subscribe({
          next: (nhanVien) => {
            this.notificationService.success(
              'Thành công',
              'Đã thêm nhân viên mới'
            );
            this.showAddModal = false;
            this.resetNewEmployee();
            this.loadEmployees(); // Reload data
          },
          error: (error) => {
            console.error('Error creating employee:', error);
            this.notificationService.error('Lỗi', 'Không thể tạo nhân viên');
            this.loading = false;
          },
        });
      },
      error: (error) => {
        console.error('Error creating account:', error);
        this.notificationService.error('Lỗi', 'Không thể tạo tài khoản');
        this.loading = false;
      },
    });
  }

  editEmployee(employee: any) {
    this.editingEmployee = {
      ...employee,
      sodienthoai: employee.taikhoan?.sodienthoai,
      vaitro: employee.chucvu,
    };
    this.showEditModal = true;
  }

  updateEmployee() {
    if (!this.editingEmployee.hoten) {
      this.notificationService.error('Lỗi', 'Vui lòng nhập họ tên');
      return;
    }

    this.loading = true;

    // Update employee info
    const nhanVienData = {
      hoten: this.editingEmployee.hoten,
      email: this.editingEmployee.email,
      chucvu: this.editingEmployee.vaitro,
    };

    this.taiKhoanService
      .updateNhanVien(this.editingEmployee.id, nhanVienData)
      .subscribe({
        next: () => {
          // Update account info if exists
          if (this.editingEmployee.taikhoan?.id) {
            const taiKhoanData = {
              email: this.editingEmployee.email,
              sodienthoai: this.editingEmployee.sodienthoai,
              quyenid: this.getQuyenIdFromRole(this.editingEmployee.vaitro),
            };

            this.taiKhoanService
              .updateTaiKhoan(this.editingEmployee.taikhoan.id, taiKhoanData)
              .subscribe({
                next: () => {
                  this.notificationService.success(
                    'Thành công',
                    'Đã cập nhật thông tin nhân viên'
                  );
                  this.showEditModal = false;
                  this.loadEmployees();
                },
                error: (error) => {
                  console.error('Error updating account:', error);
                  this.notificationService.error(
                    'Lỗi',
                    'Không thể cập nhật tài khoản'
                  );
                  this.loading = false;
                },
              });
          } else {
            this.notificationService.success(
              'Thành công',
              'Đã cập nhật thông tin nhân viên'
            );
            this.showEditModal = false;
            this.loadEmployees();
          }
        },
        error: (error) => {
          console.error('Error updating employee:', error);
          this.notificationService.error('Lỗi', 'Không thể cập nhật nhân viên');
          this.loading = false;
        },
      });
  }

  toggleEmployeeStatus(employee: any) {
    if (!employee.taikhoan?.id) {
      this.notificationService.error('Lỗi', 'Không tìm thấy tài khoản');
      return;
    }

    const action = employee.taikhoan?.trangthai ? 'khóa' : 'mở khóa';
    if (confirm(`Bạn có chắc muốn ${action} tài khoản này?`)) {
      const taiKhoanData = {
        trangthai: !employee.taikhoan.trangthai,
      };

      this.taiKhoanService
        .updateTaiKhoan(employee.taikhoan.id, taiKhoanData)
        .subscribe({
          next: () => {
            employee.taikhoan.trangthai = !employee.taikhoan.trangthai;
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

  deleteEmployee(employee: any) {
    if (
      confirm(
        'Bạn có chắc muốn xóa nhân viên này? Hành động này không thể hoàn tác.'
      )
    ) {
      this.loading = true;

      // Delete employee first, then account
      this.taiKhoanService.deleteNhanVien(employee.id).subscribe({
        next: () => {
          if (employee.taikhoan?.id) {
            this.taiKhoanService
              .deleteTaiKhoan(employee.taikhoan.id)
              .subscribe({
                next: () => {
                  this.notificationService.success(
                    'Thành công',
                    'Đã xóa nhân viên'
                  );
                  this.loadEmployees();
                },
                error: (error) => {
                  console.error('Error deleting account:', error);
                  this.notificationService.warning(
                    'Cảnh báo',
                    'Đã xóa nhân viên nhưng không thể xóa tài khoản'
                  );
                  this.loadEmployees();
                },
              });
          } else {
            this.notificationService.success('Thành công', 'Đã xóa nhân viên');
            this.loadEmployees();
          }
        },
        error: (error) => {
          console.error('Error deleting employee:', error);
          this.notificationService.error('Lỗi', 'Không thể xóa nhân viên');
          this.loading = false;
        },
      });
    }
  }

  closeModal(event: Event) {
    if (event.target === event.currentTarget) {
      this.showAddModal = false;
      this.showEditModal = false;
    }
  }

  resetNewEmployee() {
    this.newEmployee = {
      hoten: '',
      email: '',
      tendn: '',
      matkhau: '',
      sodienthoai: '',
      vaitro: '',
    };
  }

  getRoleLabel(role: string): string {
    const roleMap: { [key: string]: string } = {
      ADMIN: 'Quản trị viên',
      MANAGER: 'Quản lý',
      STAFF: 'Nhân viên',
    };
    return roleMap[role] || role;
  }

  private getQuyenIdFromRole(role: string): number {
    const roleMap: { [key: string]: number } = {
      ADMIN: 1,
      MANAGER: 2,
      STAFF: 3,
    };
    return roleMap[role] || 3;
  }

  formatDate(dateString: string | Date | undefined): string {
    if (!dateString) return 'N/A';
    return new Date(dateString).toLocaleDateString('vi-VN');
  }
}
