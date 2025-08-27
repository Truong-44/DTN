import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';
import { StorageService } from '../../../core/services/storage.service';
import { APP_CONSTANTS } from '../../../core/constants/app.constants';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-debug',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="debug-container">
      <h2>🔍 Admin Debug Information</h2>
      
      <div class="debug-section">
        <h3>Authentication Status</h3>
        <p><strong>Is Authenticated:</strong> {{ isAuthenticated }}</p>
        <p><strong>Is Admin:</strong> {{ isAdmin }}</p>
        <p><strong>Is Employee:</strong> {{ isEmployee }}</p>
        <p><strong>Is Customer:</strong> {{ isCustomer }}</p>
      </div>

      <div class="debug-section">
        <h3>Current User Data</h3>
        <pre>{{ currentUserJson }}</pre>
      </div>

      <div class="debug-section">
        <h3>Storage Data</h3>
        <p><strong>Token:</strong> {{ token ? 'Present (' + token.substring(0, 20) + '...)' : 'Not found' }}</p>
        <p><strong>User from Storage:</strong></p>
        <pre>{{ userFromStorageJson }}</pre>
      </div>

      <div class="debug-section">
        <h3>Role Analysis</h3>
        <p><strong>User QuyenID:</strong> {{ currentUser?.quyenid || 'N/A' }}</p>
        <p><strong>Expected Admin QuyenID:</strong> 1</p>
        <p><strong>Role Check Result:</strong> {{ roleCheckResult }}</p>
      </div>

      <div class="debug-actions">
        <button (click)="refreshAuth()" class="btn btn-primary">🔄 Refresh Auth</button>
        <button (click)="clearAuth()" class="btn btn-warning">🗑️ Clear Auth</button>
        <button (click)="goToLogin()" class="btn btn-secondary">🔐 Go to Login</button>
        <button (click)="tryAdminAccess()" class="btn btn-success">🚀 Try Admin Access</button>
      </div>

      <div class="debug-section" *ngIf="debugMessages.length > 0">
        <h3>Debug Messages</h3>
        <div *ngFor="let message of debugMessages" class="debug-message">
          {{ message }}
        </div>
      </div>
    </div>
  `,
  styles: [`
    .debug-container {
      max-width: 800px;
      margin: 20px auto;
      padding: 20px;
      background: #f8f9fa;
      border-radius: 8px;
      font-family: monospace;
    }

    .debug-section {
      margin-bottom: 30px;
      padding: 15px;
      background: white;
      border-radius: 4px;
      border-left: 4px solid #007bff;
    }

    .debug-section h3 {
      margin-top: 0;
      color: #007bff;
    }

    pre {
      background: #f1f3f4;
      padding: 10px;
      border-radius: 4px;
      overflow-x: auto;
      font-size: 12px;
    }

    .debug-actions {
      display: flex;
      gap: 10px;
      flex-wrap: wrap;
    }

    .btn {
      padding: 8px 16px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-weight: bold;
    }

    .btn-primary { background: #007bff; color: white; }
    .btn-warning { background: #ffc107; color: black; }
    .btn-secondary { background: #6c757d; color: white; }
    .btn-success { background: #28a745; color: white; }

    .debug-message {
      padding: 8px;
      margin: 4px 0;
      background: #e9ecef;
      border-radius: 4px;
      font-size: 12px;
    }
  `]
})
export class AdminDebugComponent implements OnInit {
  currentUser: any = null;
  currentUserJson = '';
  userFromStorageJson = '';
  token: string | null = null;
  isAuthenticated = false;
  isAdmin = false;
  isEmployee = false;
  isCustomer = false;
  roleCheckResult = '';
  debugMessages: string[] = [];

  constructor(
    private authService: AuthService,
    private storageService: StorageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadDebugInfo();
  }

  loadDebugInfo(): void {
    // Get current user from service
    this.currentUser = this.authService.getCurrentUser();
    this.currentUserJson = JSON.stringify(this.currentUser, null, 2);

    // Get user from storage directly
    const userFromStorage = this.storageService.getItem(APP_CONSTANTS.USER_KEY);
    if (userFromStorage) {
      try {
        const userObj = JSON.parse(userFromStorage);
        this.userFromStorageJson = JSON.stringify(userObj, null, 2);
      } catch (e) {
        this.userFromStorageJson = 'Error parsing user from storage: ' + e;
      }
    } else {
      this.userFromStorageJson = 'No user found in storage';
    }

    // Get token
    this.token = this.authService.getToken();

    // Get authentication status
    this.isAuthenticated = this.authService.isAuthenticated();
    this.isAdmin = this.authService.isAdmin();
    this.isEmployee = this.authService.isEmployee();
    this.isCustomer = this.authService.isCustomer();

    // Role check analysis
    if (this.currentUser) {
      this.roleCheckResult = `QuyenID ${this.currentUser.quyenid} ${this.currentUser.quyenid === 1 ? 'MATCHES' : 'DOES NOT MATCH'} Admin requirement (1)`;
    } else {
      this.roleCheckResult = 'No user data available';
    }

    this.addDebugMessage('Debug info loaded at ' + new Date().toLocaleTimeString());
  }

  refreshAuth(): void {
    this.addDebugMessage('Refreshing authentication...');
    this.authService.forceInitializeAuth();
    setTimeout(() => {
      this.loadDebugInfo();
      this.addDebugMessage('Authentication refreshed');
    }, 100);
  }

  clearAuth(): void {
    this.addDebugMessage('Clearing authentication...');
    this.authService.logout();
    setTimeout(() => {
      this.loadDebugInfo();
      this.addDebugMessage('Authentication cleared');
    }, 100);
  }

  goToLogin(): void {
    this.addDebugMessage('Navigating to login...');
    this.router.navigate(['/login']);
  }

  tryAdminAccess(): void {
    this.addDebugMessage('Attempting admin access...');
    if (this.isAdmin) {
      this.router.navigate(['/admin']);
      this.addDebugMessage('Navigating to admin panel...');
    } else {
      this.addDebugMessage('❌ Admin access denied - not an admin user');
    }
  }

  private addDebugMessage(message: string): void {
    this.debugMessages.unshift(`[${new Date().toLocaleTimeString()}] ${message}`);
    if (this.debugMessages.length > 10) {
      this.debugMessages = this.debugMessages.slice(0, 10);
    }
  }
}
