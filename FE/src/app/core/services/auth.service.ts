// core/services/auth.service.ts
import { Injectable, Injector } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { ApiService } from './api.service';
import { StorageService } from './storage.service';
import { TaiKhoan } from '../models/taikhoan.model';
import { TaiKhoanService } from './taikhoan.service';
import {
  LoginRequest,
  LoginResponse,
  RegisterRequest,
} from '../models/auth.model';
import { _ENDPOINTS } from '../constants/api.constants';
import { APP_CONSTANTS } from '../constants/app.constants';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private currentUserSubject = new BehaviorSubject<TaiKhoan | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  public isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  private gioHangService: any = null; // Use any to avoid circular dependency

  constructor(
    private apiService: ApiService,
    private storageService: StorageService,
    private injector: Injector, // Sử dụng Injector để tránh circular dependency
    private taiKhoanService: TaiKhoanService
  ) {
    this.initializeAuth();

    // Delay GioHangService injection to prevent circular dependency
    setTimeout(() => {
      try {
        const GioHangServiceClass = this.injector.get('GioHangService' as any);
        this.gioHangService = GioHangServiceClass;
      } catch (error) {
        console.warn('[AuthService] Could not inject GioHangService:', error);
      }
    }, 50);
  }

  public forceInitializeAuth(): void {
    console.log('🔄 Force reinitializing auth from external call...');
    this.initializeAuth();
  }

  private initializeAuth(): void {
    console.log('🔧 Initializing Authentication...');
    const token = this.storageService.getItem(APP_CONSTANTS.TOKEN_KEY);
    const userJson = this.storageService.getItem(APP_CONSTANTS.USER_KEY);

    if (token && userJson) {
      try {
        const user = JSON.parse(userJson);
        console.log('📖 Restored user from storage:', user);
        this.currentUserSubject.next(user);
        this.isAuthenticatedSubject.next(true);
        console.log('✅ Auth state restored from storage.');
      } catch (error) {
        console.error(
          '❌ Error parsing user from storage, clearing auth data.',
          error
        );
        this.clearAuthData();
      }
    } else {
      console.log('ℹ️ No valid auth data in storage.');
      this.isAuthenticatedSubject.next(false);
      this.currentUserSubject.next(null);
    }
  }
  sendOtpEmail(email: string): Observable<any> {
    // type = 'email' cho gửi email
    return this.apiService.post<any>(
      `/api/otp/send?receiver=${encodeURIComponent(email)}&type=email`,
      {}
    );
  }

  sendOtpPhone(phone: string): Observable<any> {
    return this.apiService.post<any>(
      `/api/otp/send?receiver=${encodeURIComponent(phone)}&type=phone`,
      {}
    );
  }

  verifyOtpEmail(email: string, otp: string): Observable<any> {
    return this.apiService.post<any>(
      `/api/otp/verify?receiver=${encodeURIComponent(
        email
      )}&type=email&otp=${encodeURIComponent(otp)}`,
      {}
    );
  }

  verifyOtpPhone(phone: string, otp: string): Observable<any> {
    return this.apiService.post<any>(
      `/api/otp/verify?receiver=${encodeURIComponent(
        phone
      )}&type=phone&otp=${encodeURIComponent(otp)}`,
      {}
    );
  }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.apiService.post<any>(_ENDPOINTS.AUTH.LOGIN, credentials).pipe(
      map((response) => {
        if (!response || !response.success || !response.data) {
          throw new Error(
            response.message || 'Login failed: Invalid response from server'
          );
        }
        return {
          token: response.data.token,
          user: response.data.user,
          refreshToken: response.data.refreshToken,
          expiresIn: response.data.expiresIn,
        } as LoginResponse;
      }),
      tap((response) => {
        console.log('✅ Login successful, response user:', response.user);
        this.setAuthData(response);
        if (this.gioHangService && this.gioHangService.handleLogin) {
          this.gioHangService.handleLogin();
        }
      })
    );
  }

  register(userData: RegisterRequest): Observable<TaiKhoan> {
    return this.apiService.post<TaiKhoan>(_ENDPOINTS.AUTH.REGISTER, userData);
  }

  logout(): void {
    this.apiService.post<void>(_ENDPOINTS.AUTH.LOGOUT, {}).subscribe({
      next: () => {
        console.log('✅ Logout successful on server.');
        this.clearAuthData();
      },
      error: (err) => {
        console.error(
          '❌ Logout API call failed, clearing local data anyway.',
          err
        );
        this.clearAuthData();
      },
    });
  }

  changePassword(oldPassword: string, newPassword: string): Observable<any> {
    return this.apiService.post(_ENDPOINTS.AUTH.CHANGE_PASSWORD, {
      oldPassword,
      newPassword,
    });
  }

  private setAuthData(response: LoginResponse): void {
    if (!response.token || !response.user) {
      console.error('❌ [AuthService] Invalid auth data received.', response);
      return;
    }
    console.log(
      '💾 [AuthService] Saving auth data. User object received:',
      response.user
    );
    console.log(
      '👤 [AuthService] KhachHang data in response:',
      response.user.khachhang
    );
    console.log(
      '👔 [AuthService] NhanVien data in response:',
      response.user.nhanvien
    );

    this.storageService.setItem(APP_CONSTANTS.TOKEN_KEY, response.token);
    this.storageService.setObject(APP_CONSTANTS.USER_KEY, response.user);

    this.currentUserSubject.next(response.user);
    this.isAuthenticatedSubject.next(true);
  }
  private clearAuthData(): void {
    console.log('🗑️ Clearing all authentication data.');
    this.storageService.removeItem(APP_CONSTANTS.TOKEN_KEY);
    this.storageService.removeItem(APP_CONSTANTS.USER_KEY);
    this.currentUserSubject.next(null);
    this.isAuthenticatedSubject.next(false);
  }

  loginWithGoogle(idToken: string) {
    return this.apiService
      .post<any>('/api/taikhoanlienket', {
        provider: 'google',
        idToken,
      })
      .pipe(
        tap((response) => {
          if (response.token && response.user) {
            this.setAuthData(response);
            if (response.user?.id) {
              this.fetchAndSetFullUser(response.user.id).subscribe({
                next: () => {
                  if (this.gioHangService && this.gioHangService.handleLogin) {
                    this.gioHangService.handleLogin();
                  }
                },
                error: () => {
                  if (this.gioHangService && this.gioHangService.handleLogin) {
                    this.gioHangService.handleLogin();
                  }
                },
              });
            } else {
              if (this.gioHangService && this.gioHangService.handleLogin) {
                this.gioHangService.handleLogin();
              }
            }
          }
        })
      );
  }

  getCurrentUser(): TaiKhoan | null {
    return this.currentUserSubject.value;
  }

  getCurrentUserId(): number | null {
    const user = this.getCurrentUser();
    return user ? user.id : null;
  }

  getToken(): string | null {
    return this.storageService.getItem(APP_CONSTANTS.TOKEN_KEY);
  }

  isAuthenticated(): boolean {
    return this.isAuthenticatedSubject.value;
  }

  hasRole(roleId: number): boolean {
    const user = this.getCurrentUser();
    return user ? user.quyenid === roleId : false;
  }

  isAdmin(): boolean {
    return this.hasRole(1);
  }

  isEmployee(): boolean {
    return this.hasRole(2);
  }

  isCustomer(): boolean {
    return this.hasRole(3);
  }

  /**
   * Gọi API lấy đầy đủ thông tin tài khoản (bao gồm khachhang/nhanvien nếu có)
   * và cập nhật lại storage + BehaviorSubjects.
   */
  private fetchAndSetFullUser(userId: number): Observable<TaiKhoan> {
    console.log(
      `🚀 [AuthService] Fetching full user data for userId: ${userId}`
    );
    return this.taiKhoanService.getTaiKhoanById(userId).pipe(
      tap((fullUser) => {
        console.log('🔄 [AuthService] Fetched full user data:', fullUser);
        console.log(
          '👤 [AuthService] Full KhachHang data after fetch:',
          fullUser.khachhang
        );

        if (fullUser.quyenid === 3 && !fullUser.khachhang) {
          console.warn(
            '⚠️ [AuthService] Customer user without khachhang data. This might cause issues.'
          );
        }
        this.storageService.setObject(APP_CONSTANTS.USER_KEY, fullUser);
        this.currentUserSubject.next(fullUser);
        console.log(
          '✅ [AuthService] User subject and storage updated with full data.'
        );
      })
    );
  }
}
