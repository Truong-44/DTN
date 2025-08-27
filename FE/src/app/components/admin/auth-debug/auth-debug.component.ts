import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-auth-debug',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container mt-4">
      <h2>Auth Debug Information</h2>
      
      <div class="card">
        <div class="card-body">
          <h5>Current User Info:</h5>
          <pre>{{ userInfo | json }}</pre>
          
          <h5>Is Authenticated:</h5>
          <p>{{ isAuthenticated }}</p>
          
          <h5>Current Role:</h5>
          <p>{{ currentRole }}</p>
          
          <h5>Is Admin:</h5>
          <p>{{ isAdmin }}</p>
          
          <h5>Local Storage Token:</h5>
          <p>{{ token || 'No token found' }}</p>
          
          <button class="btn btn-primary" (click)="refreshAuth()">Refresh Auth Info</button>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .container { max-width: 800px; }
    pre { background: #f8f9fa; padding: 15px; border-radius: 5px; }
  `]
})
export class AuthDebugComponent implements OnInit {
  userInfo: any = {};
  isAuthenticated = false;
  currentRole = '';
  isAdmin = false;
  token = '';

  constructor(private authService: AuthService) {}

  ngOnInit() {
    this.refreshAuth();
  }

  refreshAuth() {
    this.userInfo = this.authService.getCurrentUser();
    this.isAuthenticated = this.authService.isAuthenticated();
    this.currentRole = this.userInfo?.role || 'No role';
    this.isAdmin = this.authService.isAdmin();
    this.token = localStorage.getItem('authToken') || '';
    
    console.log('Auth Debug Info:', {
      userInfo: this.userInfo,
      isAuthenticated: this.isAuthenticated,
      currentRole: this.currentRole,
      isAdmin: this.isAdmin,
      token: this.token
    });
  }
}
