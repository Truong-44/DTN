import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { map, take } from 'rxjs/operators';
import { AuthService } from '../../../core/services/auth.service';
import { NotificationService } from '../../../core/services/notification.service';
import { UserRoles } from '../../../core/enums/user-roles.enum';

export const AdminGuard: CanActivateFn = (route, state) => {
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
        router.navigate(['/login']);
        return false;
      }

      if (user.quyenid === UserRoles.ADMIN) {
        return true;
      } else {
        notificationService.error(
          'Không có quyền truy cập',
          'Bạn không có quyền admin để truy cập vào trang này'
        );
        router.navigate(['/']);
        return false;
      }
    })
  );
};
