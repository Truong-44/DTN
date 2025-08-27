import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { NotificationService } from '../services/notification.service';
import { AuthService } from '../services/auth.service';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const notificationService = inject(NotificationService);
  const authService = inject(AuthService);
  const router = inject(Router);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMessage = 'Đã xảy ra lỗi không xác định';

      switch (error.status) {
        case 400:
          errorMessage = 'Dữ liệu không hợp lệ';
          break;
        case 401:
          errorMessage = 'Phiên đăng nhập đã hết hạn';
          authService.logout();
          console.warn('[HTTP 401] Redirecting to /login');
          router.navigate(['/login']);
          break;
        case 403:
          errorMessage = 'Bạn không có quyền thực hiện hành động này';
          router.navigate(['/unauthorized']);
          break;
        case 404:
          errorMessage = 'Không tìm thấy tài nguyên';
          break;
        case 422:
          errorMessage = 'Dữ liệu không đúng định dạng';
          break;
        case 500:
          errorMessage = 'Lỗi máy chủ nội bộ';
          break;
        case 503:
          errorMessage = 'Dịch vụ tạm thời không khả dụng';
          break;
        default:
          errorMessage = `Lỗi: ${error.status} - ${error.message}`;
      }

      // Chỉ hiển thị notification nếu không skip
      if (!req.headers.has('X-Skip-Error-Notification')) {
        notificationService.error('Lỗi', errorMessage);
      }

      return throwError(() => error);
    })
  );
};
