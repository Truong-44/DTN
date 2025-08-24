import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../../../core/services/api.service';
import { TaiKhoanService } from '../../../../core/services/taikhoan.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { Quyen } from '../../../../core/models/quyen.model';
import { TaiKhoan } from '../../../../core/models/taikhoan.model';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-permission-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './permission-list.component.html',
  styleUrls: ['./permission-list.component.scss'],
})
export class PermissionListComponent implements OnInit {
  roles: Quyen[] = [];
  users: TaiKhoan[] = [];
  filteredUsers: TaiKhoan[] = [];
  loading = false;

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

  constructor(
    private apiService: ApiService,
    private taiKhoanService: TaiKhoanService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.loading = true;

    forkJoin({
      roles: this.apiService.get<Quyen[]>('api/quyen'),
      users: this.taiKhoanService.getAllTaiKhoan(),
    }).subscribe({
      next: (data) => {
        this.roles = data.roles || [];
        this.users = data.users || [];
        this.calculateStats();
        this.filterUsers();
        this.loading = false;
      },
      error: (error) => {
        console.error('Lỗi tải dữ liệu:', error);
        this.notificationService.error('Lỗi', 'Lỗi tải dữ liệu phân quyền');
        this.loading = false;
      },
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
    this.notificationService.info(
      'Thông báo',
      'Chức năng tạo vai trò đang phát triển'
    );
  }

  editRole(role: Quyen): void {
    this.notificationService.info(
      'Thông báo',
      'Chức năng sửa vai trò đang phát triển'
    );
  }

  deleteRole(roleId: number): void {
    if (roleId === 1) {
      this.notificationService.error('Lỗi', 'Không thể xóa vai trò Admin');
      return;
    }

    if (confirm('Bạn có chắc chắn muốn xóa vai trò này?')) {
      this.apiService.delete<void>(`api/quyen/${roleId}`).subscribe({
        next: () => {
          this.roles = this.roles.filter((r) => r.id !== roleId);
          this.calculateStats();
          this.notificationService.success(
            'Thành công',
            'Xóa vai trò thành công'
          );
        },
        error: (error) => {
          console.error('Lỗi xóa vai trò:', error);
          this.notificationService.error('Lỗi', 'Lỗi xóa vai trò');
        },
      });
    }
  }

  // User Management Methods
  editUser(user: TaiKhoan): void {
    this.notificationService.info(
      'Thông báo',
      'Chức năng sửa người dùng đang phát triển'
    );
  }

  toggleUserStatus(userId: number, newStatus: boolean): void {
    this.taiKhoanService.updateTaiKhoanStatus(userId, newStatus).subscribe({
      next: (updatedUser) => {
        const index = this.users.findIndex((u) => u.id === userId);
        if (index !== -1) {
          this.users[index] = updatedUser;
          this.calculateStats();
          this.filterUsers();
          this.notificationService.success(
            'Thành công',
            `${newStatus ? 'Kích hoạt' : 'Vô hiệu hóa'} người dùng thành công`
          );
        }
      },
      error: (error) => {
        console.error('Lỗi cập nhật trạng thái:', error);
        this.notificationService.error(
          'Lỗi',
          'Lỗi cập nhật trạng thái người dùng'
        );
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
      this.notificationService.error(
        'Lỗi',
        'Không thể thay đổi quyền của Admin'
      );
      return;
    }

    // In real app, this would call an API to update permissions
    this.notificationService.info(
      'Thông báo',
      'Chức năng cập nhật quyền đang phát triển'
    );
  }
}
