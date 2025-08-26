import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';
import { TaiKhoanService } from '../../core/services/taikhoan.service';

@Component({
  selector: 'app-debug',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div
      style="padding: 20px; background: #f5f5f5; margin: 20px; border-radius: 8px;"
    >
      <h3>🔍 Debug Authentication Status</h3>

      <div style="margin: 10px 0;">
        <strong>Is Authenticated:</strong> {{ isAuthenticated }}
      </div>

      <div style="margin: 10px 0;">
        <strong>Current User:</strong>
        <pre>{{ currentUserJson }}</pre>
      </div>

      <div style="margin: 10px 0;">
        <strong>Token:</strong> {{ token || 'None' }}
      </div>

      <div style="margin: 10px 0;">
        <strong>LocalStorage User:</strong>
        <pre>{{ localStorageUserJson }}</pre>
      </div>

      <button (click)="testLogin()" style="margin: 5px; padding: 10px;">
        Test Login (admin/admin)
      </button>

      <button (click)="refreshUserData()" style="margin: 5px; padding: 10px;">
        Refresh User Data
      </button>

      <button (click)="testLogout()" style="margin: 5px; padding: 10px;">
        Test Logout
      </button>
    </div>
  `,
})
export class DebugComponent implements OnInit {
  isAuthenticated = false;
  currentUserJson = '';
  token = '';
  localStorageUserJson = '';

  constructor(
    private authService: AuthService,
    private taiKhoanService: TaiKhoanService
  ) {}

  ngOnInit() {
    this.updateDebugInfo();

    // Subscribe to auth changes
    this.authService.currentUser$.subscribe((user) => {
      console.log('🔄 Debug: User changed to:', user);
      this.updateDebugInfo();
    });
  }

  updateDebugInfo() {
    this.isAuthenticated = this.authService.isAuthenticated();
    this.currentUserJson = JSON.stringify(
      this.authService.getCurrentUser(),
      null,
      2
    );
    this.token = this.authService.getToken() || '';

    const userFromStorage = localStorage.getItem('user');
    this.localStorageUserJson = userFromStorage
      ? JSON.stringify(JSON.parse(userFromStorage), null, 2)
      : 'None';
  }

  testLogin() {
    console.log('🧪 Testing login...');
    this.authService.login({ username: 'admin', password: 'admin' }).subscribe({
      next: (response) => {
        console.log('✅ Login successful:', response);
        this.updateDebugInfo();
      },
      error: (error) => {
        console.error('❌ Login failed:', error);
      },
    });
  }

  refreshUserData() {
    const currentUser = this.authService.getCurrentUser();
    if (currentUser?.id) {
      console.log('🔄 Refreshing user data for ID:', currentUser.id);
      this.taiKhoanService.getTaiKhoanById(currentUser.id).subscribe({
        next: (userData) => {
          console.log('✅ Refreshed user data:', userData);
          this.authService['setAuthData']({
            token: this.authService.getToken()!,
            user: userData,
            expiresIn: 3600, // Default 1 hour
          });
          this.updateDebugInfo();
        },
        error: (error) => {
          console.error('❌ Refresh failed:', error);
        },
      });
    }
  }

  testLogout() {
    console.log('🧪 Testing logout...');
    this.authService.logout();
    this.updateDebugInfo();
  }
}
