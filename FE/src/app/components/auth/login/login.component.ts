import {
  Component,
  ChangeDetectionStrategy,
  inject,
  ChangeDetectorRef,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent {
  private authService = inject(AuthService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  email = '';
  password = '';
  errorMessage = '';
  loading = false;
  showPassword = false;
  rememberMe = false;

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }

  login(): void {
    // Validate input
    if (!this.email || !this.password) {
      this.errorMessage = 'Vui lòng điền đầy đủ thông tin!';
      this.cdr.markForCheck();
      return;
    }

    // Trim whitespace
    const trimmedEmail = this.email.trim();
    const trimmedPassword = this.password.trim();

    if (!trimmedEmail || !trimmedPassword) {
      this.errorMessage = 'Vui lòng điền đầy đủ thông tin!';
      this.cdr.markForCheck();
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    this.cdr.markForCheck();

    const credentials = {
      username: trimmedEmail,
      password: trimmedPassword,
    };

    console.log('🔐 Login attempt with credentials:', {
      username: credentials.username,
      password: '***', // Don't log actual password
    });

    this.authService.login(credentials).subscribe({
      next: (res) => {
        console.log('✅ Login successful, response:', res);
        this.loading = false;

        // The AuthService now handles the response format conversion
        if (!res) {
          console.error('❌ No response from login service');
          this.errorMessage = 'Đăng nhập thất bại. Vui lòng thử lại!';
          this.cdr.markForCheck();
          return;
        }

        // Lưu thông tin remember me
        if (this.rememberMe) {
          localStorage.setItem('rememberLogin', 'true');
          localStorage.setItem('loginEmail', this.email);
        } else {
          localStorage.removeItem('rememberLogin');
          localStorage.removeItem('loginEmail');
        }

        console.log('👤 User info:', res.user);
        console.log('🔑 Token:', res.token);

        // Điều hướng theo vai trò user - simplified for now
        if (
          res.user &&
          res.user.quyen &&
          res.user.quyen.ten &&
          res.user.quyen.ten.includes('ADMIN')
        ) {
          console.log('🚀 Redirecting to admin panel');
          this.router.navigate(['/admin']);
        } else {
          console.log('🚀 Redirecting to home page');
          this.router.navigate(['/']);
        }
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('❌ Login error details:', err);
        console.error('❌ Error status:', err.status);
        console.error('❌ Error message:', err.message);
        console.error('❌ Error response:', err.error);

        this.loading = false;

        if (err.status === 401) {
          this.errorMessage = 'Email hoặc mật khẩu không chính xác!';
        } else if (err.status === 403) {
          this.errorMessage = 'Tài khoản của bạn đã bị khóa!';
        } else if (err.status === 0) {
          this.errorMessage = 'Không thể kết nối đến server!';
        } else if (err.status === 400) {
          this.errorMessage = 'Thông tin đăng nhập không hợp lệ!';
        } else {
          this.errorMessage =
            err.error?.message || 'Đăng nhập thất bại. Vui lòng thử lại!';
        }
        this.cdr.markForCheck();
      },
    });
  }

  loginWithGoogle(): void {
    // TODO: Implement Google login
    console.log('Google login not implemented yet');
  }

  loginWithFacebook(): void {
    // TODO: Implement Facebook login
    console.log('Facebook login not implemented yet');
  }

  ngOnInit(): void {
    // Kiểm tra remember me
    if (localStorage.getItem('rememberLogin') === 'true') {
      this.email = localStorage.getItem('loginEmail') || '';
      this.rememberMe = true;
    }
  }
}
