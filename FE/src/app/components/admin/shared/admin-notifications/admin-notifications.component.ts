import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subject, takeUntil } from 'rxjs';
import { AdminNotificationService, NotificationMessage } from '../services/admin-notification.service';
import { trigger, transition, style, animate } from '@angular/animations';

@Component({
  selector: 'app-admin-notifications',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="notification-container">
      <div 
        *ngFor="let notification of notifications; trackBy: trackByNotificationId"
        class="notification"
        [ngClass]="'notification-' + notification.type"
        [@slideIn]
      >
        <div class="notification-icon">
          <i [ngClass]="getIconClass(notification.type)"></i>
        </div>
        <div class="notification-content">
          <h4 class="notification-title">{{ notification.title }}</h4>
          <p class="notification-message">{{ notification.message }}</p>
        </div>
        <button 
          class="notification-close"
          (click)="removeNotification(notification.id)"
          type="button"
        >
          <i class="fas fa-times"></i>
        </button>
        <div 
          class="notification-progress"
          [style.animation-duration]="notification.duration + 'ms'"
          *ngIf="notification.duration && notification.duration > 0"
        ></div>
      </div>
    </div>
  `,
  styleUrls: ['./admin-notifications.component.scss'],
  animations: [
    trigger('slideIn', [
      transition(':enter', [
        style({ transform: 'translateX(100%)', opacity: 0 }),
        animate('200ms ease-out', style({ transform: 'translateX(0)', opacity: 1 }))
      ]),
      transition(':leave', [
        animate('200ms ease-in', style({ transform: 'translateX(100%)', opacity: 0 }))
      ])
    ])
  ]
})
export class AdminNotificationsComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();
  notifications: NotificationMessage[] = [];

  constructor(private notificationService: AdminNotificationService) {}

  ngOnInit(): void {
    this.notificationService.getNotifications()
      .pipe(takeUntil(this.destroy$))
      .subscribe(notifications => {
        this.notifications = notifications;
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  removeNotification(id: string): void {
    this.notificationService.removeNotification(id);
  }

  getIconClass(type: NotificationMessage['type']): string {
    const iconMap = {
      success: 'fas fa-check-circle',
      error: 'fas fa-exclamation-circle',
      warning: 'fas fa-exclamation-triangle',
      info: 'fas fa-info-circle'
    };
    return iconMap[type];
  }

  trackByNotificationId(index: number, notification: NotificationMessage): string {
    return notification.id;
  }
}
