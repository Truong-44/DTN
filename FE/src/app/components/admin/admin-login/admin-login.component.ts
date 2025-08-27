import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-admin-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.scss'],
})
export class AdminLoginComponent {
  username = '';
  password = '';
  error = '';

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  onLogin() {
    console.log('🔐 Admin Login: Attempting login with:', this.username);
    
    this.authService.login({
      username: this.username,
      password: this.password
    }).subscribe({
      next: (response) => {
        console.log('✅ Admin Login: Login successful:', response);
        const user = this.authService.getCurrentUser();
        console.log('👤 Admin Login: Current user after login:', user);
        
        if (user && user.quyenid === 1) {
          console.log('✅ Admin Login: User is admin, redirecting to admin panel');
          this.router.navigate(['/admin']);
        } else if (user && user.quyenid === 2) {
          console.log('✅ Admin Login: User is employee, redirecting to admin panel');
          this.router.navigate(['/admin']);
        } else {
          console.log('❌ Admin Login: User is not admin/employee');
          this.error = 'Tài khoản không có quyền truy cập admin!';
        }
      },
      error: (error) => {
        console.error('❌ Admin Login: Login failed:', error);
        this.error = 'Sai tài khoản hoặc mật khẩu!';
      }
    });
  }

  goToMainLogin() {
    this.router.navigate(['/login']);
  }

  goToDebug() {
    this.router.navigate(['/admin-debug']);
  }
}
