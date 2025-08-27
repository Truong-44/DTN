import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';
import { StorageService } from '../../core/services/storage.service';
import { APP_CONSTANTS } from '../../core/constants/app.constants';
import { UserRoles } from '../../core/enums/user-roles.enum';

@Component({
  selector: 'app-auth-debug',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container mt-5">
      <div class="card">
        <div class="card-header">
          <h3>🔍 Auth Debug Information</h3>
        </div>
        <div class="card-body">
          
          <!-- Authentication Status -->
          <div class="mb-3">
            <h5>Authentication Status:</h5>
            <p><strong>Is Authenticated:</strong> {{ isAuthenticated }}</p>
            <p><strong>Has Token:</strong> {{ hasToken }}</p>
          </div>

          <!-- Current User -->
          <div class="mb-3">
            <h5>Current User:</h5>
            <pre>{{ currentUserJson }}</pre>
          </div>

          <!-- Role Information -->
          <div class="mb-3">
            <h5>Role Information:</h5>
            <p><strong>User Role ID:</strong> {{ userRoleId }}</p>
            <p><strong>Is Admin:</strong> {{ isAdmin }}</p>
            <p><strong>Is Employee:</strong> {{ isEmployee }}</p>
            <p><strong>Is Customer:</strong> {{ isCustomer }}</p>
          </div>

          <!-- Storage Information -->
          <div class="mb-3">
            <h5>Storage Information:</h5>
            <p><strong>Token in Storage:</strong> {{ tokenInStorage }}</p>
            <p><strong>User Data in Storage:</strong></p>
            <pre>{{ userDataInStorage }}</pre>
          </div>

          <!-- Admin Access Test -->
          <div class="mb-3">
            <h5>Admin Access Test:</h5>
            <button 
              class="btn btn-primary me-2" 
              (click)="testAdminAccess()"
              [disabled]="!isAuthenticated">
              Test Admin Access
            </button>
            <button 
              class="btn btn-secondary me-2" 
              (click)="refreshAuth()">
              Refresh Auth
            </button>
            <button 
              class="btn btn-warning" 
              (click)="clearAuth()">
              Clear Auth Data
            </button>
          </div>

          <!-- Messages -->
          <div class="alert alert-info" *ngIf="debugMessage">
            {{ debugMessage }}
          </div>

        </div>
      </div>
    </div>
  `,
  styles: [`
    pre {
      background-color: #f8f9fa;
      padding: 10px;
      border-radius: 4px;
      font-size: 12px;
      max-height: 200px;
      overflow-y: auto;
    }
  `]
})
export class AuthDebugComponent implements OnInit {
  isAuthenticated = false;
  hasToken = false;
  currentUserJson = '';
  userRoleId: number | null = null;
  isAdmin = false;
  isEmployee = false;
  isCustomer = false;
  tokenInStorage = '';
  userDataInStorage = '';
  debugMessage = '';

  constructor(
    private authService: AuthService,
    private storageService: StorageService
  ) {}

  ngOnInit(): void {
    this.loadDebugInfo();
    
    // Subscribe to auth changes
    this.authService.isAuthenticated$.subscribe(isAuth => {
      this.isAuthenticated = isAuth;
      this.loadDebugInfo();
    });

    this.authService.currentUser$.subscribe(user => {
      this.loadDebugInfo();
    });
  }

  loadDebugInfo(): void {
    // Authentication status
    this.isAuthenticated = this.authService.isAuthenticated();
    this.hasToken = !!this.authService.getToken();

    // Current user
    const currentUser = this.authService.getCurrentUser();
    this.currentUserJson = JSON.stringify(currentUser, null, 2);
    
    // Role information
    this.userRoleId = currentUser?.quyenid || null;
    this.isAdmin = this.authService.isAdmin();
    this.isEmployee = this.authService.isEmployee();
    this.isCustomer = this.authService.isCustomer();

    // Storage information
    this.tokenInStorage = this.storageService.getItem(APP_CONSTANTS.TOKEN_KEY) || 'No token';
    const userData = this.storageService.getItem(APP_CONSTANTS.USER_KEY);
    this.userDataInStorage = userData ? JSON.stringify(JSON.parse(userData), null, 2) : 'No user data';
  }

  testAdminAccess(): void {
    const currentUser = this.authService.getCurrentUser();
    
    if (!currentUser) {
      this.debugMessage = '❌ No current user found';
      return;
    }

    if (currentUser.quyenid === UserRoles.ADMIN) {
      this.debugMessage = '✅ User has ADMIN role - should have access to admin routes';
    } else {
      this.debugMessage = `❌ User has role ${currentUser.quyenid} (not ADMIN=${UserRoles.ADMIN}) - no admin access`;
    }
  }

  refreshAuth(): void {
    this.authService.forceInitializeAuth();
    this.debugMessage = '🔄 Auth refreshed';
    setTimeout(() => this.loadDebugInfo(), 100);
  }

  clearAuth(): void {
    this.authService.logout();
    this.debugMessage = '🗑️ Auth data cleared';
    setTimeout(() => this.loadDebugInfo(), 100);
  }
}
