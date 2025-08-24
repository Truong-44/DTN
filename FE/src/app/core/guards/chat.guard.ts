import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ChatGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): Observable<boolean> {
    // Kiểm tra feature flag từ environment hoặc config
    const chatEnabled = true; // Có thể lấy từ environment hoặc feature service

    if (!chatEnabled) {
      console.warn('Chat feature is disabled');
      return of(false);
    }

    // Kiểm tra browser support cho các tính năng cần thiết
    if (!this.checkBrowserSupport()) {
      console.warn('Browser does not support chat features');
      return of(false);
    }

    return of(true);
  }

  private checkBrowserSupport(): boolean {
    // Kiểm tra WebSocket support (nếu cần realtime)
    const hasWebSocket = 'WebSocket' in window;

    // Kiểm tra LocalStorage support
    const hasLocalStorage = 'localStorage' in window;

    // Kiểm tra Notification API (nếu cần)
    const hasNotification = 'Notification' in window;

    return hasWebSocket && hasLocalStorage;
  }
}
