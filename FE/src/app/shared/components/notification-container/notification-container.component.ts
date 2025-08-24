import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import {
  NotificationService,
  Notification,
} from '../../../core/services/notification.service';

@Component({
  selector: 'app-notification-container',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="notification-container">
      <div
        *ngFor="let notification of notifications; trackBy: trackByFn"
        class="notification"
        [ngClass]="'notification-' + notification.type"
        [@slideIn]
      >
        <div class="notification-content">
          <div class="notification-icon">
            <i [ngClass]="getIconClass(notification.type)"></i>
          </div>
          <div class="notification-text">
            <div class="notification-title">{{ notification.title }}</div>
            <div class="notification-message">{{ notification.message }}</div>
          </div>
          <button
            class="notification-close"
            (click)="closeNotification(notification.id)"
          >
            ×
          </button>
        </div>
      </div>
    </div>
  `,
  styles: [
    `
      .notification-container {
        position: fixed;
        top: 20px;
        right: 20px;
        z-index: 3000;
        max-width: 400px;
        width: 100%;
      }

      .notification {
        margin-bottom: 12px;
        border-radius: 8px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
        overflow: hidden;
        animation: slideIn 0.3s ease-out;
      }

      @keyframes slideIn {
        from {
          transform: translateX(100%);
          opacity: 0;
        }
        to {
          transform: translateX(0);
          opacity: 1;
        }
      }

      .notification-success {
        background: linear-gradient(135deg, #10b981 0%, #059669 100%);
        color: white;
      }

      .notification-error {
        background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%);
        color: white;
      }

      .notification-warning {
        background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
        color: white;
      }

      .notification-info {
        background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
        color: white;
      }

      .notification-content {
        display: flex;
        align-items: center;
        padding: 16px;
        gap: 12px;
      }

      .notification-icon {
        font-size: 24px;
        min-width: 24px;
      }

      .notification-text {
        flex: 1;
      }

      .notification-title {
        font-weight: 600;
        font-size: 16px;
        margin-bottom: 4px;
      }

      .notification-message {
        font-size: 14px;
        opacity: 0.9;
        line-height: 1.4;
      }

      .notification-close {
        background: none;
        border: none;
        color: white;
        font-size: 24px;
        cursor: pointer;
        padding: 0;
        width: 24px;
        height: 24px;
        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: 50%;
        transition: background-color 0.2s ease;

        &:hover {
          background-color: rgba(255, 255, 255, 0.2);
        }
      }

      @media (max-width: 768px) {
        .notification-container {
          top: 10px;
          right: 10px;
          left: 10px;
          max-width: none;
        }
      }
    `,
  ],
  animations: [],
})
export class NotificationContainerComponent implements OnInit, OnDestroy {
  notifications: Notification[] = [];
  private subscription?: Subscription;

  constructor(private notificationService: NotificationService) {}

  ngOnInit() {
    this.subscription = this.notificationService.notifications$.subscribe(
      (notifications: Notification[]) => {
        this.notifications = notifications;
      }
    );
  }

  ngOnDestroy() {
    this.subscription?.unsubscribe();
  }

  trackByFn(index: number, notification: Notification): string {
    return notification.id;
  }

  getIconClass(type: string): string {
    switch (type) {
      case 'success':
        return 'fas fa-check-circle';
      case 'error':
        return 'fas fa-exclamation-circle';
      case 'warning':
        return 'fas fa-exclamation-triangle';
      case 'info':
        return 'fas fa-info-circle';
      default:
        return 'fas fa-bell';
    }
  }

  closeNotification(id: string) {
    this.notificationService.removeNotification(id);
  }
}
