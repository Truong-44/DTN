import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { TaiKhoan } from '../../../core/models/taikhoan.model';
import { KhachHang } from '../../../core/models/khachhang.model';
import { AuthService } from '../../../core/services/auth.service';
import { NotificationService } from '../../../core/services/notification.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  taiKhoan: TaiKhoan | null = null;
  khachHang: KhachHang | null = null;
  isLoading = false;
  isEditing = false;

  // Form data for editing
  profileForm = {
    hoten: '',
    email: '',
    sodienthoai: '',
    diachi: '',
    ngaysinh: '',
    gioitinh: '',
  };

  genderOptions = [
    { value: 'Nam', label: 'Nam' },
    { value: 'Nữ', label: 'Nữ' },
    { value: 'Khác', label: 'Khác' },
  ];

  constructor(
    private authService: AuthService,
    private notificationService: NotificationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadProfile();
  }

  loadProfile(): void {
    this.taiKhoan = this.authService.getCurrentUser();

    if (!this.taiKhoan) {
      this.notificationService.error(
        'Lỗi',
        'Vui lòng đăng nhập để xem thông tin tài khoản'
      );
      this.router.navigate(['/auth/login']);
      return;
    }

    this.khachHang = this.taiKhoan.khachhang || null;
    this.loadFormData();
  }

  loadFormData(): void {
    if (this.khachHang) {
      this.profileForm = {
        hoten: this.khachHang.hoten || '',
        email: this.taiKhoan?.email || '',
        sodienthoai: this.taiKhoan?.sodienthoai || '',
        diachi: this.khachHang.diachi || '',
        ngaysinh: this.khachHang.ngaysinh
          ? this.formatDateForInput(this.khachHang.ngaysinh)
          : '',
        gioitinh: this.khachHang.gioitinh || '',
      };
    } else {
      this.profileForm = {
        hoten: '',
        email: this.taiKhoan?.email || '',
        sodienthoai: this.taiKhoan?.sodienthoai || '',
        diachi: '',
        ngaysinh: '',
        gioitinh: '',
      };
    }
  }

  formatDateForInput(date: Date | string): string {
    if (typeof date === 'string') {
      return date.split('T')[0];
    }
    return date.toISOString().split('T')[0];
  }

  toggleEdit(): void {
    if (this.isEditing) {
      this.loadFormData(); // Reset form if cancelling
    }
    this.isEditing = !this.isEditing;
  }

  async updateProfile(): Promise<void> {
    if (!this.taiKhoan) return;

    this.isLoading = true;

    try {
      // Validate form
      if (!this.profileForm.hoten.trim()) {
        this.notificationService.error('Lỗi', 'Vui lòng nhập họ tên');
        return;
      }

      if (!this.profileForm.email.trim()) {
        this.notificationService.error('Lỗi', 'Vui lòng nhập email');
        return;
      }

      // Update localStorage với thông tin mới
      const updatedTaiKhoan: TaiKhoan = {
        ...this.taiKhoan,
        email: this.profileForm.email,
        sodienthoai: this.profileForm.sodienthoai,
        khachhang: this.khachHang
          ? {
              ...this.khachHang,
              hoten: this.profileForm.hoten,
              diachi: this.profileForm.diachi,
              ngaysinh: this.profileForm.ngaysinh
                ? new Date(this.profileForm.ngaysinh)
                : undefined,
              gioitinh: this.profileForm.gioitinh,
            }
          : {
              id: Date.now(), // Temporary ID
              taikhoanid: this.taiKhoan.id,
              hoten: this.profileForm.hoten,
              diachi: this.profileForm.diachi,
              ngaysinh: this.profileForm.ngaysinh
                ? new Date(this.profileForm.ngaysinh)
                : undefined,
              gioitinh: this.profileForm.gioitinh,
            },
      };

      // Cập nhật trong localStorage
      localStorage.setItem('currentUser', JSON.stringify(updatedTaiKhoan));

      // Update component data
      this.taiKhoan = updatedTaiKhoan;
      this.khachHang = updatedTaiKhoan.khachhang || null;

      this.isEditing = false;
      this.notificationService.success(
        'Thành công',
        'Đã cập nhật thông tin tài khoản'
      );
    } catch (error) {
      console.error('Error updating profile:', error);
      this.notificationService.error(
        'Lỗi',
        'Có lỗi xảy ra khi cập nhật thông tin'
      );
    } finally {
      this.isLoading = false;
    }
  }

  logout(): void {
    this.authService.logout();
    this.notificationService.success('Thành công', 'Đã đăng xuất');
    this.router.navigate(['/home']);
  }

  getAccountTypeName(): string {
    if (!this.taiKhoan) return '';

    switch (this.taiKhoan.loaidangnhap) {
      case 'NORMAL':
        return 'Tài khoản thường';
      case 'GOOGLE':
        return 'Đăng nhập Google';
      case 'FACEBOOK':
        return 'Đăng nhập Facebook';
      default:
        return this.taiKhoan.loaidangnhap;
    }
  }

  formatDate(date: Date | string | undefined): string {
    if (!date) return 'Chưa cập nhật';

    const d = typeof date === 'string' ? new Date(date) : date;
    return d.toLocaleDateString('vi-VN');
  }

  getAccountStatusText(): string {
    return this.taiKhoan?.trangthai ? 'Hoạt động' : 'Bị khóa';
  }

  getAccountStatusClass(): string {
    return this.taiKhoan?.trangthai ? 'status-active' : 'status-inactive';
  }
}
