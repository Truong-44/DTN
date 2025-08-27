import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { map, take } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';
import { NotificationService } from '../services/notification.service';
import { UserRoles } from '../enums/user-roles.enum';

export const roleGuard = (allowedRoles: UserRoles[]): CanActivateFn => {
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

        const userRole = user.quyenid as UserRoles;

        if (allowedRoles.includes(userRole)) {
          return true;
        } else {
          notificationService.error(
            'Không có quyền truy cập',
            'Bạn không có quyền truy cập vào trang này'
          );
          router.navigate(['/unauthorized']);
          return false;
        }
      })
    );
  };
};

// Guard đặc biệt cho Admin - cho phép quyền 1 và 2
export const adminGuard: CanActivateFn = roleGuard([UserRoles.ADMIN, UserRoles.EMPLOYEE]);

// Guard đặc biệt cho Employee và Admin
export const employeeGuard: CanActivateFn = roleGuard([
  UserRoles.ADMIN,
  UserRoles.EMPLOYEE,
]);

// Guard đặc biệt cho Customer
export const customerGuard: CanActivateFn = roleGuard([UserRoles.CUSTOMER]);

// Guard cho phép tất cả user đã đăng nhập
export const userGuard: CanActivateFn = roleGuard([
  UserRoles.ADMIN,
  UserRoles.EMPLOYEE,
  UserRoles.CUSTOMER,
]);
