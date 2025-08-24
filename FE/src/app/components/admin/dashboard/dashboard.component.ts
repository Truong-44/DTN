import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="dashboard">
      <h2>Admin Dashboard</h2>
      <div class="stats-grid">
        <div class="stat-card">
          <h3>Tổng đơn hàng</h3>
          <p class="stat-number">{{ totalOrders }}</p>
        </div>
        <div class="stat-card">
          <h3>Doanh thu</h3>
          <p class="stat-number">{{ formatCurrency(totalRevenue) }}</p>
        </div>
        <div class="stat-card">
          <h3>Sản phẩm</h3>
          <p class="stat-number">{{ totalProducts }}</p>
        </div>
        <div class="stat-card">
          <h3>Khách hàng</h3>
          <p class="stat-number">{{ totalCustomers }}</p>
        </div>
      </div>
    </div>
  `,
  styles: [
    `
      .dashboard {
        padding: 20px;
      }
      .stats-grid {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
        gap: 20px;
        margin-top: 20px;
      }
      .stat-card {
        background: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      }
      .stat-number {
        font-size: 24px;
        font-weight: bold;
        color: #2563eb;
        margin: 10px 0;
      }
    `,
  ],
})
export class DashboardComponent implements OnInit {
  totalOrders = 0;
  totalRevenue = 0;
  totalProducts = 0;
  totalCustomers = 0;

  ngOnInit(): void {
    this.loadDashboardData();
  }

  loadDashboardData(): void {
    this.totalOrders = 150;
    this.totalRevenue = 25000000;
    this.totalProducts = 75;
    this.totalCustomers = 200;
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(amount);
  }
}
