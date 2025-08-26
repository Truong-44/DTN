import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { map, take, tap } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';
import { NotificationService } from '../services/notification.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const notificationService = inject(NotificationService);

  console.log('🛡️ Auth Guard: Checking access for', state.url);

  return authService.isAuthenticated$.pipe(
    take(1), // Take the latest value and complete
    tap((isAuthenticated) =>
      console.log(`🛡️ Auth Guard - isAuthenticated$: ${isAuthenticated}`)
    ),
    map((isAuthenticated) => {
      if (isAuthenticated) {
        console.log('✅ Access granted');
        return true;
      } else {
        console.log('❌ Access denied - redirecting to login');
        notificationService.warning(
          'Yêu cầu đăng nhập',
          'Bạn cần đăng nhập để truy cập trang này'
        );
        router.navigate(['/login'], {
          queryParams: { returnUrl: state.url },
        });
        return false;
      }
    })
  );
};

export const guestGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return authService.isAuthenticated$.pipe(
    take(1),
    map((isAuthenticated) => {
      if (!isAuthenticated) {
        return true;
      } else {
        router.navigate(['/']);
        return false;
      }
    })
  );
};
