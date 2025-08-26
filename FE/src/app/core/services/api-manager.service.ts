// core/services/api-manager.service.ts
import { Injectable } from '@angular/core';
import { SanPhamService } from './sanpham.service';
import { GioHangService } from './giohang.service';
import { DonHangService } from './donhang.service';
import { HoaDonService } from './hoadon.service';
import { AuthService } from './auth.service';
import { TaiKhoanService } from './taikhoan.service';
import { VanChuyenService } from './vanchuyen.service';
import { LoadingService } from './loading.service';
import { NotificationService } from './notification.service';
import { StorageService } from './storage.service';

/**
 * Central API Manager Service
 * Provides a single entry point for all API operations
 * Makes it easier to manage dependencies and provides better organization
 */
@Injectable({
  providedIn: 'root',
})
export class ApiManagerService {
  constructor(
    // Core Services
    public auth: AuthService,
    public loading: LoadingService,
    public notification: NotificationService,
    public storage: StorageService,

    // Business Services
    public sanpham: SanPhamService,
    public giohang: GioHangService,
    public donhang: DonHangService,
    public hoadon: HoaDonService,
    public taikhoan: TaiKhoanService,
    public vanchuyen: VanChuyenService
  ) {}

  /**
   * Quick access methods for common operations
   */

  // Authentication shortcuts
  get isAuthenticated() {
    return this.auth.isAuthenticated();
  }

  get currentUser() {
    return this.auth.getCurrentUser();
  }

  get currentUserId() {
    return this.auth.getCurrentUserId();
  }

  // Loading shortcuts
  showLoading() {
    this.loading.show();
  }

  hideLoading() {
    this.loading.hide();
  }

  // Notification shortcuts
  showSuccess(title: string, message: string) {
    this.notification.success(title, message);
  }

  showError(title: string, message: string) {
    this.notification.error(title, message);
  }

  showWarning(title: string, message: string) {
    this.notification.warning(title, message);
  }

  showInfo(title: string, message: string) {
    this.notification.info(title, message);
  }
}
