import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-permission-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './permission-list.component.html',
  styleUrls: ['./permission-list.component.scss'],
})
export class PermissionListComponent implements OnInit {
  roles: any[] = [];
  users: any[] = [];
  filteredUsers: any[] = [];
  loading = false;
  
  private apiUrl = 'http://localhost:8080/api';

  // Tab management
  activeTab = 'roles';

  // Stats
  totalRoles = 0;
  totalUsers = 0;
  totalPermissions = 8; // Fixed number based on system features
  activeUsers = 0;

  // Filters
  userSearchKeyword = '';
  roleFilter = '';
  statusFilter = '';

  // System permissions for matrix
  systemPermissions = [
    { key: 'customer_view', name: 'Xem khách hàng' },
    { key: 'customer_edit', name: 'Sửa khách hàng' },
    { key: 'product_view', name: 'Xem sản phẩm' },
    { key: 'product_edit', name: 'Sửa sản phẩm' },
    { key: 'order_view', name: 'Xem đơn hàng' },
    { key: 'order_edit', name: 'Sửa đơn hàng' },
    { key: 'employee_view', name: 'Xem nhân viên' },
    { key: 'employee_edit', name: 'Sửa nhân viên' },
  ];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.loading = true;

    // Load roles and users from backend
    forkJoin({
      roles: this.http.get<any[]>(`${this.apiUrl}/quyen`),
      users: this.http.get<any[]>(`${this.apiUrl}/taikhoan`)
    }).subscribe({
      next: (data) => {
        this.roles = data.roles || [];
        this.users = data.users || [];
        this.calculateStats();
        this.filterUsers();
        this.loading = false;
      },
      error: (error: any) => {
        console.error('Lỗi tải dữ liệu:', error);
        this.loading = false;
        
        // Fallback data
        this.roles = [
          { id: 1, ten: 'Admin', mota: 'Quản trị viên hệ thống' },
          { id: 2, ten: 'Nhân viên', mota: 'Nhân viên bán hàng' },
          { id: 3, ten: 'Khách hàng', mota: 'Khách hàng thường' }
        ];
        
        this.users = [
          { id: 1, tendangnhap: 'admin', email: 'admin@example.com', quyenid: 1, trangthai: true, created_at: new Date() },
          { id: 2, tendangnhap: 'staff01', email: 'staff01@example.com', quyenid: 2, trangthai: true, created_at: new Date() },
          { id: 3, tendangnhap: 'customer01', email: 'customer01@example.com', quyenid: 3, trangthai: true, created_at: new Date() }
        ];
        
        this.calculateStats();
        this.filterUsers();
      }
    });
  }

  calculateStats(): void {
    this.totalRoles = this.roles.length;
    this.totalUsers = this.users.length;
    this.activeUsers = this.users.filter((u) => u.trangthai).length;
  }

  filterUsers(): void {
    this.filteredUsers = this.users.filter((user) => {
      const matchesSearch =
        !this.userSearchKeyword ||
        user.tendangnhap
          .toLowerCase()
          .includes(this.userSearchKeyword.toLowerCase()) ||
        (user.email &&
          user.email
            .toLowerCase()
            .includes(this.userSearchKeyword.toLowerCase()));

      const matchesRole =
        !this.roleFilter || user.quyenid?.toString() === this.roleFilter;

      const matchesStatus =
        !this.statusFilter || user.trangthai.toString() === this.statusFilter;

      return matchesSearch && matchesRole && matchesStatus;
    });
  }

  getUserCountByRole(roleId: number): number {
    return this.users.filter((u) => u.quyenid === roleId).length;
  }

  getRoleName(roleId: number): string {
    const role = this.roles.find((r) => r.id === roleId);
    return role?.ten || 'Không xác định';
  }

  getRoleBadgeClass(roleId: number): string {
    const role = this.roles.find((r) => r.id === roleId);
    const roleName = role?.ten?.toLowerCase() || '';

    if (roleName.includes('admin')) return 'admin';
    if (roleName.includes('nhân viên') || roleName.includes('employee'))
      return 'employee';
    if (roleName.includes('khách hàng') || roleName.includes('customer'))
      return 'customer';

    return 'customer';
  }

  getRolePermissions(roleId: number): string[] {
    // Mock permissions based on role
    switch (roleId) {
      case 1: // Admin
        return ['Toàn quyền', 'Quản lý hệ thống', 'Báo cáo'];
      case 2: // Employee
        return ['Quản lý đơn hàng', 'Quản lý sản phẩm', 'Xem khách hàng'];
      case 3: // Customer
        return ['Đặt hàng', 'Xem sản phẩm', 'Cập nhật thông tin'];
      default:
        return ['Không có quyền'];
    }
  }

  formatDate(date: Date | string): string {
    if (!date) return 'N/A';
    return new Date(date).toLocaleDateString('vi-VN');
  }

  // Role Management Methods
  createRole(): void {
    console.log('Chức năng tạo vai trò đang phát triển');
    alert('Chức năng tạo vai trò đang phát triển');
  }

  editRole(role: any): void {
    console.log('Chức năng sửa vai trò đang phát triển', role);
    alert('Chức năng sửa vai trò đang phát triển');
  }

  deleteRole(roleId: number): void {
    if (roleId === 1) {
      alert('Không thể xóa vai trò Admin');
      return;
    }

    if (confirm('Bạn có chắc chắn muốn xóa vai trò này?')) {
      this.http.delete<void>(`${this.apiUrl}/quyen/${roleId}`).subscribe({
        next: () => {
          this.roles = this.roles.filter((r) => r.id !== roleId);
          this.calculateStats();
          alert('Xóa vai trò thành công');
        },
        error: (error: any) => {
          console.error('Lỗi xóa vai trò:', error);
          alert('Lỗi xóa vai trò');
        },
      });
    }
  }

  // User Management Methods
  editUser(user: any): void {
    console.log('Chức năng sửa người dùng đang phát triển', user);
    alert('Chức năng sửa người dùng đang phát triển');
  }

  toggleUserStatus(userId: number, newStatus: boolean): void {
    const updateData = { trangthai: newStatus };
    
    this.http.put<any>(`${this.apiUrl}/taikhoan/${userId}`, updateData).subscribe({
      next: (updatedUser: any) => {
        const index = this.users.findIndex((u) => u.id === userId);
        if (index !== -1) {
          this.users[index] = updatedUser;
          this.calculateStats();
          this.filterUsers();
          alert(`${newStatus ? 'Kích hoạt' : 'Vô hiệu hóa'} người dùng thành công`);
        }
      },
      error: (error: any) => {
        console.error('Lỗi cập nhật trạng thái:', error);
        alert('Lỗi cập nhật trạng thái người dùng');
      },
    });
  }

  // Permission Matrix Methods
  hasPermission(roleId: number, permissionKey: string): boolean {
    // Mock permission check - in real app this would come from backend
    if (roleId === 1) return true; // Admin has all permissions
    if (roleId === 2)
      return (
        permissionKey.includes('view') ||
        permissionKey.includes('order') ||
        permissionKey.includes('product')
      );
    if (roleId === 3)
      return (
        permissionKey.includes('view') && !permissionKey.includes('employee')
      );
    return false;
  }

  togglePermission(roleId: number, permissionKey: string): void {
    if (roleId === 1) {
      alert('Không thể thay đổi quyền của Admin');
      return;
    }

    // In real app, this would call an API to update permissions
    console.log('Chức năng cập nhật quyền đang phát triển', roleId, permissionKey);
    alert('Chức năng cập nhật quyền đang phát triển');
  }
}
