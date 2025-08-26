import { CanDeactivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { Observable, of } from 'rxjs';
import { NotificationService } from '../services/notification.service';

// Interface cho component có thể có unsaved changes
export interface CanComponentDeactivate {
  canDeactivate(): Observable<boolean> | Promise<boolean> | boolean;
  hasUnsavedChanges?(): boolean;
}

export const unsavedChangesGuard: CanDeactivateFn<CanComponentDeactivate> = (
  component,
  currentRoute,
  currentState,
  nextState
) => {
  const notificationService = inject(NotificationService);

  // Nếu component không implement interface, cho phép rời đi
  if (!component.canDeactivate) {
    return true;
  }

  // Kiểm tra nếu có unsaved changes
  if (component.hasUnsavedChanges && component.hasUnsavedChanges()) {
    const result = confirm(
      'Bạn có thay đổi chưa được lưu. Bạn có chắc chắn muốn rời khỏi trang này không?'
    );

    if (!result) {
      notificationService.info(
        'Thao tác bị hủy',
        'Vui lòng lưu thay đổi trước khi rời khỏi trang'
      );
    }

    return result;
  }

  // Gọi method canDeactivate của component
  return component.canDeactivate();
};
