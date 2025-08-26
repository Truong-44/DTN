import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

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

  constructor(private router: Router) {}

  onLogin() {
    if (this.username === 'admin01' && this.password === '123') {
      this.router.navigate(['/admin/dashboard']);
    } else {
      this.error = 'Sai tài khoản hoặc mật khẩu!';
    }
  }
}
