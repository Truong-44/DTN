import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
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

  // OTP Flow States
  otpSent = false;
  otpCode = '';
  otpError = '';
  otpMethod = ''; // 'email' or 'phone'
  otpTarget = ''; // email address or phone number
  registrationStep = 'form'; // 'form', 'otp', 'completed'

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
    
    // Kiểm tra phải có ít nhất email hoặc số điện thoại
    const hasEmail = this.registerData.email?.trim();
    const hasPhone = this.registerData.sodienthoai?.trim();
    
    if (!hasEmail && !hasPhone) {
      this.errorMessage = 'Vui lòng nhập email hoặc số điện thoại để nhận mã OTP!';
      return false;
    }
    
    // Validate email nếu có
    if (hasEmail) {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(this.registerData.email!)) {
        this.errorMessage = 'Email không hợp lệ!';
        return false;
      }
    }
    
    // Validate số điện thoại nếu có
    if (hasPhone) {
      const phoneRegex = /^[0-9]{10,11}$/;
      if (!phoneRegex.test(this.registerData.sodienthoai!.replace(/\s/g, ''))) {
        this.errorMessage = 'Số điện thoại không hợp lệ!';
        return false;
      }
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
    return true;
  }

  onRegister(): void {
    if (!this.validateForm()) {
      this.cdr.markForCheck();
      return;
    }

    // Bước 1: Gửi OTP trước khi tạo tài khoản
    this.sendOtpForRegistration();
  }

  sendOtpForRegistration(): void {
    this.loading = true;
    this.errorMessage = '';
    this.otpError = '';

    // Ưu tiên gửi OTP qua email nếu có, không thì qua số điện thoại
    const hasEmail = this.registerData.email?.trim();
    const hasPhone = this.registerData.sodienthoai?.trim();

    if (hasEmail) {
      this.otpMethod = 'email';
      this.otpTarget = this.registerData.email.trim();
      
      this.authService.sendOtpEmail(this.otpTarget).subscribe({
        next: () => {
          this.loading = false;
          this.registrationStep = 'otp';
          this.otpSent = true;
          this.successMessage = `Mã OTP đã được gửi đến email: ${this.otpTarget}`;
          this.cdr.markForCheck();
        },
        error: (err) => {
          this.loading = false;
          this.errorMessage = 'Không thể gửi mã OTP đến email. Vui lòng thử lại!';
          console.error('Error sending OTP to email:', err);
          this.cdr.markForCheck();
        }
      });
    } else if (hasPhone) {
      this.otpMethod = 'phone';
      this.otpTarget = this.registerData.sodienthoai.trim();
      
      this.authService.sendOtpPhone(this.otpTarget).subscribe({
        next: () => {
          this.loading = false;
          this.registrationStep = 'otp';
          this.otpSent = true;
          this.successMessage = `Mã OTP đã được gửi đến số điện thoại: ${this.otpTarget}`;
          this.cdr.markForCheck();
        },
        error: (err) => {
          this.loading = false;
          this.errorMessage = 'Không thể gửi mã OTP đến số điện thoại. Vui lòng thử lại!';
          console.error('Error sending OTP to phone:', err);
          this.cdr.markForCheck();
        }
      });
    } else {
      this.loading = false;
      this.errorMessage = 'Vui lòng nhập email hoặc số điện thoại để nhận mã OTP!';
      this.cdr.markForCheck();
    }
  }

  verifyOtpAndCreateAccount(): void {
    if (!this.otpCode.trim()) {
      this.otpError = 'Vui lòng nhập mã OTP!';
      return;
    }

    this.loading = true;
    this.otpError = '';

    const verifyObservable = this.otpMethod === 'email' 
      ? this.authService.verifyOtpEmail(this.otpTarget, this.otpCode)
      : this.authService.verifyOtpPhone(this.otpTarget, this.otpCode);

    verifyObservable.subscribe({
      next: () => {
        // OTP xác thực thành công, tạo tài khoản
        this.createAccountAfterOtpVerification();
      },
      error: (err) => {
        this.loading = false;
        this.otpError = 'Mã OTP không đúng hoặc đã hết hạn!';
        console.error('Error verifying OTP:', err);
        this.cdr.markForCheck();
      }
    });
  }

  createAccountAfterOtpVerification(): void {
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
        this.registrationStep = 'completed';
        this.successMessage = 'Đăng ký thành công! Đang đăng nhập...';
        
        // Tự động đăng nhập sau khi tạo tài khoản thành công
        this.autoLoginAfterRegistration();
      },
      error: (err) => {
        this.loading = false;
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

  autoLoginAfterRegistration(): void {
    const loginData = {
      username: this.registerData.tendangnhap.trim(),
      password: this.registerData.matkhau
    };

    this.authService.login(loginData).subscribe({
      next: () => {
        this.loading = false;
        this.successMessage = 'Đăng ký và đăng nhập thành công!';
        this.cdr.markForCheck();
        
        // Chuyển hướng về trang chủ sau 2 giây
        setTimeout(() => {
          this.router.navigate(['/']);
        }, 2000);
      },
      error: (err) => {
        this.loading = false;
        this.successMessage = 'Đăng ký thành công! Vui lòng đăng nhập thủ công.';
        console.error('Auto login failed:', err);
        this.cdr.markForCheck();
        
        // Chuyển hướng đến trang đăng nhập sau 3 giây
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 3000);
      }
    });
  }

  // Phương thức để quay lại bước đăng ký
  backToRegistrationForm(): void {
    this.registrationStep = 'form';
    this.otpSent = false;
    this.otpCode = '';
    this.otpError = '';
    this.successMessage = '';
    this.errorMessage = '';
  }

  // Phương thức gửi lại OTP
  resendOtp(): void {
    this.otpCode = '';
    this.otpError = '';
    this.sendOtpForRegistration();
  }

  loginWithGoogle(): void {
    // Nếu chưa tích hợp Google, chỉ cần để trống hoặc alert
    alert('Chức năng đăng ký bằng Google chưa được hỗ trợ.');
  }
}
