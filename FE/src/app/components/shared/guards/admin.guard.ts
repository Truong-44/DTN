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

  console.log('🛡️ AdminGuard: Checking admin access...');

  return authService.currentUser$.pipe(
    take(1),
    map((user) => {
      console.log('🛡️ AdminGuard: Current user from service:', user);
      
      if (!user) {
        console.log('❌ AdminGuard: No user found, redirecting to login');
        notificationService.error(
          'Lỗi xác thực',
          'Không tìm thấy thông tin người dùng'
        );
        router.navigate(['/login']);
        return false;
      }

      console.log('🛡️ AdminGuard: User quyenid:', user.quyenid);
      console.log('🛡️ AdminGuard: Expected admin role:', UserRoles.ADMIN);

      if (user.quyenid === UserRoles.ADMIN) {
        console.log('✅ AdminGuard: Admin access granted');
        return true;
      } else {
        console.log('❌ AdminGuard: Admin access denied - insufficient permissions');
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
