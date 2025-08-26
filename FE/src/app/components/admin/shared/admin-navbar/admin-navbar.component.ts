import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-navbar',
  templateUrl: './admin-navbar.component.html',
  styleUrls: ['./admin-navbar.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule],
})
export class AdminNavbarComponent {
  constructor(private router: Router) {}
  user = { tendangnhap: 'admin01' }; // Thay bằng lấy từ service thực tế
  showAccountMenu = false;
  isDarkMode = false;
  showNotifications = false;

  toggleTheme() {
    this.isDarkMode = !this.isDarkMode;
    document.body.classList.toggle('dark-theme', this.isDarkMode);
  }

  toggleAccountMenu() {
    this.showAccountMenu = !this.showAccountMenu;
  }
  logout() {
    this.showAccountMenu = false;
    this.router.navigate(['/']);
  }

  switchAccount() {
    this.showAccountMenu = false;
    this.router.navigate(['/admin']);
  }

  toggleNotifications() {
    this.showNotifications = !this.showNotifications;
  }
}
