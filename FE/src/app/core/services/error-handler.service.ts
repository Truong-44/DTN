// core/services/error-handler.service.ts
import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { NotificationService } from './notification.service';

export interface ErrorDetail {
  code: string;
  message: string;
  details?: any;
}

@Injectable({
  providedIn: 'root',
})
export class ErrorHandlerService {
  constructor(private notificationService: NotificationService) {}

  /**
   * Handle HTTP errors and show appropriate notifications
   */
  handleHttpError(error: HttpErrorResponse): Observable<never> {
    const errorDetail = this.parseHttpError(error);

    // Show notification based on error type
    switch (error.status) {
      case 400:
        this.notificationService.error(
          'Yêu cầu không hợp lệ',
          errorDetail.message
        );
        break;
      case 401:
        this.notificationService.error(
          'Không có quyền truy cập',
          'Vui lòng đăng nhập lại'
        );
        break;
      case 403:
        this.notificationService.error(
          'Truy cập bị từ chối',
          'Bạn không có quyền thực hiện thao tác này'
        );
        break;
      case 404:
        this.notificationService.error(
          'Không tìm thấy',
          'Tài nguyên bạn tìm kiếm không tồn tại'
        );
        break;
      case 422:
        this.notificationService.error(
          'Dữ liệu không hợp lệ',
          errorDetail.message
        );
        break;
      case 500:
        this.notificationService.error(
          'Lỗi máy chủ',
          'Đã xảy ra lỗi không mong muốn. Vui lòng thử lại sau'
        );
        break;
      case 503:
        this.notificationService.error(
          'Dịch vụ không khả dụng',
          'Máy chủ đang bảo trì. Vui lòng thử lại sau'
        );
        break;
      default:
        this.notificationService.error(
          'Lỗi kết nối',
          'Kiểm tra kết nối mạng và thử lại'
        );
    }

    console.error('HTTP Error:', errorDetail);
    return throwError(() => errorDetail);
  }

  /**
   * Parse HTTP error response to extract meaningful information
   */
  private parseHttpError(error: HttpErrorResponse): ErrorDetail {
    let message = 'Đã xảy ra lỗi không xác định';
    let code = 'UNKNOWN_ERROR';
    let details = null;

    if (error.error) {
      if (typeof error.error === 'string') {
        message = error.error;
      } else if (error.error.message) {
        message = error.error.message;
        code = error.error.code || code;
        details = error.error.details;
      } else if (error.error.error) {
        message = error.error.error;
      }
    } else if (error.message) {
      message = error.message;
    }

    return {
      code,
      message,
      details,
    };
  }

  /**
   * Handle application errors (non-HTTP)
   */
  handleApplicationError(error: any, context?: string): void {
    console.error(
      `Application Error ${context ? `in ${context}` : ''}:`,
      error
    );

    this.notificationService.error(
      'Lỗi ứng dụng',
      context
        ? `Đã xảy ra lỗi trong ${context}. Vui lòng thử lại.`
        : 'Đã xảy ra lỗi không mong muốn. Vui lòng thử lại.'
    );
  }

  /**
   * Handle validation errors
   */
  handleValidationError(errors: { [key: string]: string }): void {
    const firstError = Object.values(errors)[0];
    this.notificationService.warning(
      'Dữ liệu không hợp lệ',
      firstError || 'Vui lòng kiểm tra lại thông tin đã nhập'
    );
  }
}
