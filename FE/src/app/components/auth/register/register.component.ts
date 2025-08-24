import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {
  registerData = {
    tendangnhap: '',
    matkhau: '',
    email: '',
    sodienthoai: '',
    hoten: '',
    loaidangnhap: 'local',
    xacnhanmatkhau: '',
    nhanthongbao: false,
  };

  showThongBaoModal = false;
  loading = false;
  errorMessage = '';
  showPassword = false;
  showConfirmPassword = false;
  successMessage = '';

  otpSent = false;
  otpCode = '';
  otpError = '';
  registeredEmail = '';

  constructor(
    private authService: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }
  toggleConfirmPassword(): void {
    this.showConfirmPassword = !this.showConfirmPassword;
  }

  onToggleThongBao(): void {
    if (this.registerData.nhanthongbao) {
      this.showThongBaoModal = true;
    }
  }
  xacNhanThongBao(dongy: boolean): void {
    this.registerData.nhanthongbao = dongy;
    this.showThongBaoModal = false;
  }

  validateForm(): boolean {
    this.errorMessage = '';
    if (!this.registerData.hoten?.trim()) {
      this.errorMessage = 'Vui lòng nhập họ tên!';
      return false;
    }
    if (!this.registerData.email?.trim()) {
      this.errorMessage = 'Vui lòng nhập email!';
      return false;
    }
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(this.registerData.email)) {
      this.errorMessage = 'Email không hợp lệ!';
      return false;
    }
    if (!this.registerData.tendangnhap.trim()) {
      this.errorMessage = 'Vui lòng nhập tên đăng nhập!';
      return false;
    }
    if (this.registerData.tendangnhap.length < 3) {
      this.errorMessage = 'Tên đăng nhập phải có ít nhất 3 ký tự!';
      return false;
    }
    if (!this.registerData.matkhau) {
      this.errorMessage = 'Vui lòng nhập mật khẩu!';
      return false;
    }
    if (this.registerData.matkhau.length < 6) {
      this.errorMessage = 'Mật khẩu phải có ít nhất 6 ký tự!';
      return false;
    }
    if (this.registerData.matkhau !== this.registerData.xacnhanmatkhau) {
      this.errorMessage = 'Mật khẩu xác nhận không khớp!';
      return false;
    }
    if (this.registerData.sodienthoai && this.registerData.sodienthoai.trim()) {
      const phoneRegex = /^[0-9]{10,11}$/;
      if (!phoneRegex.test(this.registerData.sodienthoai.replace(/\s/g, ''))) {
        this.errorMessage = 'Số điện thoại không hợp lệ!';
        return false;
      }
    }
    return true;
  }

  onRegister(form?: NgForm): void {
    if (!this.validateForm()) {
      this.cdr.markForCheck();
      return;
    }
    this.loading = true;
    this.errorMessage = '';
    this.cdr.markForCheck();

    const registrationData = {
      tendangnhap: this.registerData.tendangnhap.trim(),
      matkhau: this.registerData.matkhau,
      email: this.registerData.email?.trim(),
      sodienthoai: this.registerData.sodienthoai?.trim() || undefined,
      hoten: this.registerData.hoten?.trim(),
      loaidangnhap: 'local',
      trangthai: true,
      quyenId: 3,
      quyenTen: 'CUSTOMER',
    };

    this.authService.register(registrationData).subscribe({
      next: () => {
        this.loading = false;
        this.successMessage =
          'Đăng ký thành công! Vui lòng kiểm tra email để xác thực OTP.';
        this.errorMessage = '';
        this.registeredEmail = registrationData.email || '';
        this.sendOtpToEmail();
        this.otpSent = true;
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.loading = false;
        this.successMessage = '';
        if (err.status === 409) {
          this.errorMessage = 'Tên đăng nhập hoặc email đã tồn tại!';
        } else if (err.status === 400) {
          this.errorMessage = 'Thông tin đăng ký không hợp lệ!';
        } else {
          this.errorMessage = 'Đăng ký thất bại. Vui lòng thử lại!';
        }
        this.cdr.markForCheck();
      },
    });
  }

  sendOtpToEmail() {
    this.authService.sendOtpEmail(this.registeredEmail).subscribe({
      next: () => {
        // OTP đã gửi thành công
      },
      error: () => {
        this.otpError = 'Không gửi được mã OTP. Vui lòng thử lại!';
      },
    });
  }

  verifyOtp() {
    this.loading = true;
    this.authService
      .verifyOtpEmail(this.registeredEmail, this.otpCode)
      .subscribe({
        next: () => {
          this.loading = false;
          alert('Xác thực email thành công! Bạn có thể đăng nhập.');
          this.router.navigate(['/login']);
          this.cdr.markForCheck();
        },
        error: () => {
          this.loading = false;
          this.otpError = 'Mã OTP không đúng hoặc đã hết hạn!';
          this.cdr.markForCheck();
        },
      });
  }

  loginWithGoogle(): void {
    // Nếu chưa tích hợp Google, chỉ cần để trống hoặc alert
    alert('Chức năng đăng ký bằng Google chưa được hỗ trợ.');
  }
}
