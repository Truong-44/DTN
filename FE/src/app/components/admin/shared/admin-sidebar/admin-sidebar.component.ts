import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../../core/services/auth.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-sidebar',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './admin-sidebar.component.html',
  styleUrls: ['./admin-sidebar.component.scss'],
})
export class AdminSidebarComponent {
  user: any;

  menuItems = [
    { label: 'Tổng quan', route: '/admin/dashboard', roles: [1, 2] },
    { label: 'Sản phẩm', route: '/admin/product-management', roles: [1, 2] },
    { label: 'Danh mục', route: '/admin/category-management', roles: [1, 2] },
    { label: 'Đơn hàng', route: '/admin/order-management', roles: [1, 2] },
    { label: 'Khách hàng', route: '/admin/customer-management', roles: [1, 2] },
    { label: 'Nhân viên', route: '/admin/employee-management', roles: [1] },
    { label: 'Hóa đơn', route: '/admin/invoice-management', roles: [1, 2] },
    { label: 'Vận chuyển', route: '/admin/shipping-management', roles: [1, 2] },
    { label: 'Kho hàng', route: '/admin/stock-management', roles: [1, 2] },
    { label: 'Phân quyền', route: '/admin/permission-management', roles: [1] },
  ];

  constructor(private authService: AuthService, private router: Router) {
    this.user = this.authService.getCurrentUser();
  }

  canShow(item: any): boolean {
    return this.user && item.roles.includes(this.user.quyenid);
  }
}
