import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export interface NotificationMessage {
  id: string;
  type: 'success' | 'error' | 'warning' | 'info';
  title: string;
  message: string;
  duration?: number;
  timestamp: Date;
}

@Injectable({
  providedIn: 'root'
})
export class AdminNotificationService {
  private notifications$ = new BehaviorSubject<NotificationMessage[]>([]);
  private idCounter = 0;

  constructor() {}

  getNotifications(): Observable<NotificationMessage[]> {
    return this.notifications$.asObservable();
  }

  success(title: string, message: string, duration: number = 5000): void {
    this.addNotification('success', title, message, duration);
  }

  error(title: string, message: string, duration: number = 8000): void {
    this.addNotification('error', title, message, duration);
  }

  warning(title: string, message: string, duration: number = 6000): void {
    this.addNotification('warning', title, message, duration);
  }

  info(title: string, message: string, duration: number = 5000): void {
    this.addNotification('info', title, message, duration);
  }

  private addNotification(
    type: NotificationMessage['type'],
    title: string,
    message: string,
    duration: number
  ): void {
    const notification: NotificationMessage = {
      id: `notification-${++this.idCounter}`,
      type,
      title,
      message,
      duration,
      timestamp: new Date()
    };

    const currentNotifications = this.notifications$.value;
    this.notifications$.next([...currentNotifications, notification]);

    // Auto remove after duration
    if (duration > 0) {
      setTimeout(() => {
        this.removeNotification(notification.id);
      }, duration);
    }
  }

  removeNotification(id: string): void {
    const currentNotifications = this.notifications$.value;
    const filteredNotifications = currentNotifications.filter(n => n.id !== id);
    this.notifications$.next(filteredNotifications);
  }

  clearAll(): void {
    this.notifications$.next([]);
  }

  // Quick methods for common admin operations
  operationSuccess(operation: string, entity: string): void {
    this.success(
      'Thành công!',
      `${operation} ${entity} thành công`
    );
  }

  operationError(operation: string, entity: string, error?: any): void {
    console.error(`Error ${operation} ${entity}:`, error);
    this.error(
      'Có lỗi xảy ra!',
      `Không thể ${operation} ${entity}. Vui lòng thử lại.`
    );
  }

  validationError(field: string): void {
    this.warning(
      'Thông tin không hợp lệ',
      `Vui lòng kiểm tra lại ${field}`
    );
  }

  networkError(): void {
    this.error(
      'Lỗi kết nối',
      'Không thể kết nối đến máy chủ. Vui lòng kiểm tra kết nối mạng.'
    );
  }

  featureNotAvailable(): void {
    this.info(
      'Tính năng đang phát triển',
      'Tính năng này sẽ có sẵn trong phiên bản tiếp theo'
    );
  }
}
