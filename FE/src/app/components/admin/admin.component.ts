import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  template: `
    <div class="admin-layout">
      <!-- Admin Navbar -->
      <nav class="admin-navbar">
        <div class="navbar-content">
          <h1>DTN Admin</h1>
          <div class="user-info">
            <span>Admin User</span>
            <button class="logout-btn">Đăng xuất</button>
          </div>
        </div>
      </nav>

      <div class="admin-body">
        <!-- Admin Sidebar -->
        <aside class="admin-sidebar">
          <ul class="sidebar-menu">
            <li><a routerLink="/admin/dashboard" routerLinkActive="active">📊 Dashboard</a></li>
            <li><a href="#" class="disabled">👤 Quản lý tài khoản (Đang phát triển)</a></li>
            <li><a href="#" class="disabled">👥 Quản lý khách hàng (Đang phát triển)</a></li>
            <li><a href="#" class="disabled">👨‍💼 Quản lý nhân viên (Đang phát triển)</a></li>
            <li><a href="#" class="disabled">📦 Quản lý sản phẩm (Đang phát triển)</a></li>
            <li><a href="#" class="disabled">🛒 Quản lý đơn hàng (Đang phát triển)</a></li>
            <li><a href="#" class="disabled">🏪 Quản lý kho hàng (Đang phát triển)</a></li>
            <li><a href="#" class="disabled">🔐 Phân quyền (Đang phát triển)</a></li>
          </ul>
        </aside>

        <!-- Content Area -->
        <main class="admin-content">
          <router-outlet></router-outlet>
        </main>
      </div>
    </div>
  `,
  styles: [`
    .admin-layout {
      display: flex;
      flex-direction: column;
      height: 100vh;
      background: #f5f5f5;
    }
    
    .admin-navbar {
      background: #ff6600;
      color: white;
      height: 60px;
      position: fixed;
      top: 0;
      left: 0;
      right: 0;
      z-index: 1000;
      box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }
    
    .navbar-content {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 0 20px;
      height: 100%;
    }
    
    .navbar-content h1 {
      margin: 0;
      font-size: 1.5rem;
    }
    
    .user-info {
      display: flex;
      align-items: center;
      gap: 15px;
    }
    
    .logout-btn {
      background: rgba(255,255,255,0.2);
      border: 1px solid white;
      color: white;
      padding: 5px 15px;
      border-radius: 4px;
      cursor: pointer;
    }
    
    .logout-btn:hover {
      background: rgba(255,255,255,0.3);
    }
    
    .admin-body {
      display: flex;
      margin-top: 60px;
      height: calc(100vh - 60px);
    }
    
    .admin-sidebar {
      width: 250px;
      background: white;
      border-right: 1px solid #ddd;
      overflow-y: auto;
    }
    
    .sidebar-menu {
      list-style: none;
      padding: 0;
      margin: 0;
    }
    
    .sidebar-menu li {
      border-bottom: 1px solid #eee;
    }
    
    .sidebar-menu a {
      display: block;
      padding: 15px 20px;
      color: #333;
      text-decoration: none;
      transition: all 0.3s;
    }
    
    .sidebar-menu a:hover,
    .sidebar-menu a.active {
      background: #ff6600;
      color: white;
    }
    
    .sidebar-menu a.disabled {
      color: #999;
      cursor: not-allowed;
    }
    
    .sidebar-menu a.disabled:hover {
      background: none;
      color: #999;
    }
    
    .admin-content {
      flex: 1;
      padding: 20px;
      overflow-y: auto;
      background: #f9f9f9;
    }
  `]
})
export class AdminComponent {
  constructor() {}
}
