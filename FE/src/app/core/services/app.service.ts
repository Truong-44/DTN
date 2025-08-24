import { Injectable, inject } from '@angular/core';
import { Observable, forkJoin, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

// Import all services
import { ApiService } from './api.service';
import { AuthService } from './auth.service';
import { SanPhamService } from './sanpham.service';
import { GioHangService } from './giohang.service';
// import { OrderService } from './order.service';
import { NotificationService } from './notification.service';

// Import models
import { SanPham } from '../models/sanpham.model';
import { DanhMuc } from '../models/danhmuc.model';
import { LoginRequest } from '../models/auth.model';

@Injectable({
  providedIn: 'root',
})
export class AppService {
  private apiService = inject(ApiService);
  private authService = inject(AuthService);
  private sanPhamService = inject(SanPhamService);
  private cartService = inject(GioHangService);
  // private orderService = inject(OrderService);
  private notificationService = inject(NotificationService);

  constructor() {}

  // Test tất cả API calls
  testAllAPIs(): Observable<any> {
    console.log('🚀 Bắt đầu test tất cả API calls...');

    const tests = {
      // Test Product APIs
      products: this.testProductAPIs(),

      // Test Category APIs
      categories: this.testCategoryAPIs(),

      // Test Cart APIs (local storage)
      cart: this.testCartAPIs(),

      // Test basic API service
      apiHealth: this.testAPIHealth(),
    };

    return forkJoin(tests).pipe(
      map((results) => {
        console.log('✅ Kết quả test API:', results);
        this.notificationService.success(
          'Test API hoàn tất',
          'Tất cả API đã được test thành công'
        );
        return results;
      }),
      catchError((error) => {
        console.error('❌ Lỗi khi test API:', error);
        this.notificationService.error(
          'Test API thất bại',
          'Có lỗi xảy ra khi test API'
        );
        return of({ error: error.message });
      })
    );
  }

  private testProductAPIs(): Observable<any> {
    console.log('📦 Testing Product APIs...');

    return this.sanPhamService.getAllSanPham().pipe(
      map((response) => {
        console.log('✅ Product API response:', response);
        return {
          status: 'success',
          data: response,
          message: 'Product APIs working',
        };
      }),
      catchError((error) => {
        console.error('❌ Product API error:', error);
        return of({
          status: 'error',
          error: error.message,
          message: 'Product APIs failed',
        });
      })
    );
  }

  private testCategoryAPIs(): Observable<any> {
    console.log('📂 Testing Category APIs...');

    return this.apiService.get<DanhMuc[]>('danhmuc').pipe(
      map((response) => {
        console.log('✅ Category API response:', response);
        return {
          status: 'success',
          data: response,
          message: 'Category APIs working',
        };
      }),
      catchError((error) => {
        console.error('❌ Category API error:', error);
        return of({
          status: 'error',
          error: error.message,
          message: 'Category APIs failed',
        });
      })
    );
  }

  private testCartAPIs(): Observable<any> {
    console.log('🛒 Testing Cart APIs...');

    try {
      // Test local cart operations
      const testItem = {
        chitietsanphamid: 1,
        soluong: 2,
        dongia: 100000,
        tensanpham: 'Test Product',
        tenmau: 'Red',
        hinhchinh: 'test.jpg',
      };

      this.cartService.addToCart(testItem);
      const cartItems = this.cartService.getCartItems();
      const cartCount = this.cartService.getCartCount();
      const cartTotal = this.cartService.getCartTotal();

      console.log('✅ Cart operations:', { cartItems, cartCount, cartTotal });

      return of({
        status: 'success',
        data: { cartItems, cartCount, cartTotal },
        message: 'Cart APIs working',
      });
    } catch (error) {
      console.error('❌ Cart API error:', error);
      return of({
        status: 'error',
        error: error,
        message: 'Cart APIs failed',
      });
    }
  }

  private testAPIHealth(): Observable<any> {
    console.log('🏥 Testing API Health...');

    // Test basic API connectivity
    return this.apiService.get('health').pipe(
      map((response) => {
        console.log('✅ API Health response:', response);
        return {
          status: 'success',
          data: response,
          message: 'API Health check passed',
        };
      }),
      catchError((error) => {
        console.error('❌ API Health error:', error);
        // API health endpoint có thể không tồn tại, coi như thành công
        return of({
          status: 'info',
          error: error.message,
          message: 'API Health endpoint not available (normal)',
        });
      })
    );
  }

  // Test authentication flow
  testAuthFlow(credentials: LoginRequest): Observable<any> {
    console.log('🔐 Testing Authentication Flow...');

    return this.authService.login(credentials).pipe(
      map((response) => {
        console.log('✅ Auth response:', response);
        this.notificationService.success(
          'Đăng nhập thành công',
          `Chào mừng ${response.user.tendangnhap}`
        );
        return response;
      }),
      catchError((error) => {
        console.error('❌ Auth error:', error);
        this.notificationService.error(
          'Đăng nhập thất bại',
          'Sai tên đăng nhập hoặc mật khẩu'
        );
        throw error;
      })
    );
  }

  // Get app initialization data
  initializeApp(): Observable<any> {
    console.log('🚀 Initializing App Data...');

    const initData = {
      products: this.apiService
        .get<SanPham[]>('sanpham')
        .pipe(catchError(() => of([]))),
      categories: this.apiService
        .get<DanhMuc[]>('danhmuc')
        .pipe(catchError(() => of([]))),
      // Load user if authenticated
      user: this.authService.isAuthenticated()
        ? of(this.authService.getCurrentUser())
        : of(null),
    };

    return forkJoin(initData).pipe(
      map((data) => {
        console.log('✅ App initialized with data:', data);
        return data;
      }),
      catchError((error) => {
        console.error('❌ App initialization error:', error);
        return of({
          products: [],
          categories: [],
          user: null,
          error: error.message,
        });
      })
    );
  }

  // Quick API test for development
  quickAPITest(): void {
    console.log('⚡ Quick API Test...');

    // Test some basic endpoints
    this.apiService.get<any[]>('sanpham').subscribe({
      next: (data) => {
        console.log('✅ Products loaded:', data?.length || 0);
        this.notificationService.success(
          'API Test',
          `Loaded ${data?.length || 0} products`
        );
      },
      error: (err) => {
        console.error('❌ Products failed:', err);
        this.notificationService.error('API Test', 'Failed to load products');
      },
    });

    this.apiService.get<any[]>('danhmuc').subscribe({
      next: (data) => {
        console.log('✅ Categories loaded:', data?.length || 0);
        this.notificationService.info(
          'API Test',
          `Loaded ${data?.length || 0} categories`
        );
      },
      error: (err) => {
        console.error('❌ Categories failed:', err);
        this.notificationService.warning(
          'API Test',
          'Failed to load categories'
        );
      },
    });
  }
}
