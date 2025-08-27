import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-order-management',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './order-management.component.html',
  styleUrls: ['./order-management.component.scss']
})
export class OrderManagementComponent {
  orders = [
    { id: 1, customer: 'Nguyễn Văn A', total: 1500000, status: 'Đang xử lý', date: '2025-08-27' },
    { id: 2, customer: 'Trần Thị B', total: 2300000, status: 'Hoàn thành', date: '2025-08-26' },
    { id: 3, customer: 'Lê Văn C', total: 850000, status: 'Chờ xác nhận', date: '2025-08-25' }
  ];

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND'
    }).format(amount);
  }

  getStatusClass(status: string): string {
    switch(status) {
      case 'Hoàn thành': return 'status-completed';
      case 'Đang xử lý': return 'status-processing';
      case 'Chờ xác nhận': return 'status-pending';
      default: return '';
    }
  }
}
