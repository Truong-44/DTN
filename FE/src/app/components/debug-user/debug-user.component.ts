import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-debug-user',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container mt-4">
      <div class="card">
        <div class="card-header">
          <h3>🔍 User Debug Information</h3>
        </div>
        <div class="card-body">
          <h5>Auth Service Data:</h5>
          <pre>{{ authServiceData | json }}</pre>
          
          <h5>LocalStorage Data:</h5>
          <pre>{{ localStorageData | json }}</pre>
          
          <h5>Permission Check:</h5>
          <ul>
            <li>hasRole(1): {{ hasRole1 }}</li>
            <li>hasRole(2): {{ hasRole2 }}</li>
            <li>isAdmin(): {{ isAdminCheck }}</li>
            <li>isEmployee(): {{ isEmployeeCheck }}</li>
            <li>Can Access Admin (quyenid 1 or 2): {{ canAccessAdmin }}</li>
          </ul>
          
          <div class="mt-3">
            <button class="btn btn-primary me-2" (click)="testAdminAccess()">Test Admin Access</button>
            <button class="btn btn-secondary" (click)="refreshData()">Refresh Data</button>
          </div>
          
          <div *ngIf="testResult" class="alert mt-3" [class.alert-success]="testResult.success" [class.alert-danger]="!testResult.success">
            {{ testResult.message }}
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    pre { 
      background: #f8f9fa; 
      padding: 10px; 
      border-radius: 4px; 
      font-size: 12px;
      max-height: 200px;
      overflow-y: auto;
    }
  `]
})
export class DebugUserComponent implements OnInit {
  authServiceData: any = {};
  localStorageData: any = {};
  hasRole1 = false;
  hasRole2 = false;
  isAdminCheck = false;
  isEmployeeCheck = false;
  canAccessAdmin = false;
  testResult: any = null;

  constructor(private authService: AuthService) {}

  ngOnInit() {
    this.refreshData();
  }

  refreshData() {
    // Data from AuthService
    const currentUser = this.authService.getCurrentUser();
    this.authServiceData = {
      currentUser: currentUser,
      isAuthenticated: this.authService.isAuthenticated(),
      token: this.authService.getToken()
    };

    // Data from localStorage
    this.localStorageData = {
      authToken: localStorage.getItem('authToken'),
      userData: localStorage.getItem('userData') ? JSON.parse(localStorage.getItem('userData')!) : null
    };

    // Permission checks
    this.hasRole1 = this.authService.hasRole(1);
    this.hasRole2 = this.authService.hasRole(2);
    this.isAdminCheck = this.authService.isAdmin();
    this.isEmployeeCheck = this.authService.isEmployee();
    this.canAccessAdmin = currentUser?.quyenid === 1 || currentUser?.quyenid === 2;

    console.log('🔍 Debug Data Refreshed:', {
      authServiceData: this.authServiceData,
      localStorageData: this.localStorageData,
      permissions: {
        hasRole1: this.hasRole1,
        hasRole2: this.hasRole2,
        isAdmin: this.isAdminCheck,
        isEmployee: this.isEmployeeCheck,
        canAccessAdmin: this.canAccessAdmin
      }
    });
  }

  testAdminAccess() {
    const currentUser = this.authService.getCurrentUser();
    
    if (!currentUser) {
      this.testResult = {
        success: false,
        message: 'Không có user đăng nhập'
      };
      return;
    }

    if (currentUser.quyenid === 1) {
      this.testResult = {
        success: true,
        message: `✅ User có quyền ADMIN (quyenid: ${currentUser.quyenid}). Có thể truy cập admin!`
      };
    } else if (currentUser.quyenid === 2) {
      this.testResult = {
        success: true,
        message: `✅ User có quyền EMPLOYEE (quyenid: ${currentUser.quyenid}). Có thể truy cập admin!`
      };
    } else {
      this.testResult = {
        success: false,
        message: `❌ User có quyền ${currentUser.quyenid}. Không thể truy cập admin (cần quyền 1 hoặc 2)!`
      };
    }
  }
}
