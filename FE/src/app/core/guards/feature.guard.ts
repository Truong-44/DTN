import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { NotificationService } from '../services/notification.service';

// Interface cho feature flags
export interface FeatureFlags {
  enableChat: boolean;
  enablePayment: boolean;
  enableReports: boolean;
  enableAdvancedSearch: boolean;
  enableProductReviews: boolean;
  enableWishlist: boolean;
  maintenanceMode: boolean;
}

// Mock feature flags - trong thực tế sẽ load từ API hoặc config
const FEATURE_FLAGS: FeatureFlags = {
  enableChat: true,
  enablePayment: true,
  enableReports: true,
  enableAdvancedSearch: false,
  enableProductReviews: true,
  enableWishlist: false,
  maintenanceMode: false,
};

export const featureGuard = (feature: keyof FeatureFlags): CanActivateFn => {
  return (route, state) => {
    const router = inject(Router);
    const notificationService = inject(NotificationService);

    // Kiểm tra maintenance mode trước
    if (FEATURE_FLAGS.maintenanceMode) {
      notificationService.warning(
        'Bảo trì hệ thống',
        'Hệ thống đang được bảo trì, vui lòng thử lại sau'
      );
      router.navigate(['/maintenance']);
      return false;
    }

    // Kiểm tra feature cụ thể
    if (FEATURE_FLAGS[feature]) {
      return true;
    } else {
      notificationService.info(
        'Tính năng chưa khả dụng',
        'Tính năng này hiện tại chưa được kích hoạt'
      );
      router.navigate(['/']);
      return false;
    }
  };
};

// Các guards cụ thể cho từng feature
export const chatFeatureGuard: CanActivateFn = featureGuard('enableChat');
export const paymentFeatureGuard: CanActivateFn = featureGuard('enablePayment');
export const reportsFeatureGuard: CanActivateFn = featureGuard('enableReports');
export const advancedSearchFeatureGuard: CanActivateFn = featureGuard(
  'enableAdvancedSearch'
);
export const reviewsFeatureGuard: CanActivateFn = featureGuard(
  'enableProductReviews'
);
export const wishlistFeatureGuard: CanActivateFn =
  featureGuard('enableWishlist');

// Guard kiểm tra maintenance mode
export const maintenanceGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);

  if (FEATURE_FLAGS.maintenanceMode) {
    router.navigate(['/maintenance']);
    return false;
  }

  return true;
};
