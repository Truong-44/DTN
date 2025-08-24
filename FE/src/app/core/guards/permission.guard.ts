import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { map, take } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';
import { NotificationService } from '../services/notification.service';

// Enum cho các permissions cụ thể
export enum Permissions {
  // Product permissions
  VIEW_PRODUCTS = 'VIEW_PRODUCTS',
  CREATE_PRODUCTS = 'CREATE_PRODUCTS',
  EDIT_PRODUCTS = 'EDIT_PRODUCTS',
  DELETE_PRODUCTS = 'DELETE_PRODUCTS',

  // Order permissions
  VIEW_ORDERS = 'VIEW_ORDERS',
  EDIT_ORDERS = 'EDIT_ORDERS',
  DELETE_ORDERS = 'DELETE_ORDERS',

  // User permissions
  VIEW_USERS = 'VIEW_USERS',
  CREATE_USERS = 'CREATE_USERS',
  EDIT_USERS = 'EDIT_USERS',
  DELETE_USERS = 'DELETE_USERS',

  // Report permissions
  VIEW_REPORTS = 'VIEW_REPORTS',
  EXPORT_REPORTS = 'EXPORT_REPORTS',

  // System permissions
  MANAGE_SYSTEM = 'MANAGE_SYSTEM',
  VIEW_LOGS = 'VIEW_LOGS',
}

// Mapping role với permissions
const ROLE_PERMISSIONS: Record<number, Permissions[]> = {
  1: [
    // ADMIN - có tất cả quyền
    ...Object.values(Permissions),
  ],
  2: [
    // EMPLOYEE - có một số quyền
    Permissions.VIEW_PRODUCTS,
    Permissions.CREATE_PRODUCTS,
    Permissions.EDIT_PRODUCTS,
    Permissions.VIEW_ORDERS,
    Permissions.EDIT_ORDERS,
    Permissions.VIEW_USERS,
    Permissions.VIEW_REPORTS,
  ],
  3: [
    // CUSTOMER - chỉ có quyền xem
    Permissions.VIEW_PRODUCTS,
  ],
};

export const permissionGuard = (
  requiredPermissions: Permissions[]
): CanActivateFn => {
  return (route, state) => {
    const authService = inject(AuthService);
    const router = inject(Router);
    const notificationService = inject(NotificationService);

    return authService.currentUser$.pipe(
      take(1),
      map((user) => {
        if (!user) {
          notificationService.error(
            'Lỗi xác thực',
            'Không tìm thấy thông tin người dùng'
          );
          router.navigate(['/auth/login']);
          return false;
        }

        const userPermissions = ROLE_PERMISSIONS[user.quyenid] || [];

        // Kiểm tra nếu user có tất cả quyền yêu cầu
        const hasAllPermissions = requiredPermissions.every((permission) =>
          userPermissions.includes(permission)
        );

        if (hasAllPermissions) {
          return true;
        } else {
          notificationService.error(
            'Không có quyền thực hiện',
            'Bạn không có quyền thực hiện hành động này'
          );
          router.navigate(['/unauthorized']);
          return false;
        }
      })
    );
  };
};

// Các guards cụ thể
export const canViewProductsGuard: CanActivateFn = permissionGuard([
  Permissions.VIEW_PRODUCTS,
]);
export const canCreateProductsGuard: CanActivateFn = permissionGuard([
  Permissions.CREATE_PRODUCTS,
]);
export const canEditProductsGuard: CanActivateFn = permissionGuard([
  Permissions.EDIT_PRODUCTS,
]);
export const canDeleteProductsGuard: CanActivateFn = permissionGuard([
  Permissions.DELETE_PRODUCTS,
]);

export const canViewOrdersGuard: CanActivateFn = permissionGuard([
  Permissions.VIEW_ORDERS,
]);
export const canEditOrdersGuard: CanActivateFn = permissionGuard([
  Permissions.EDIT_ORDERS,
]);

export const canManageUsersGuard: CanActivateFn = permissionGuard([
  Permissions.VIEW_USERS,
  Permissions.CREATE_USERS,
  Permissions.EDIT_USERS,
]);

export const canViewReportsGuard: CanActivateFn = permissionGuard([
  Permissions.VIEW_REPORTS,
]);
