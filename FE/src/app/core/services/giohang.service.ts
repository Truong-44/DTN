import { Injectable, Injector } from '@angular/core';
import { Observable, BehaviorSubject, of, forkJoin } from 'rxjs';
import { tap, switchMap, catchError, map, finalize } from 'rxjs/operators';
import { ApiService } from './api.service';
import { StorageService } from './storage.service';
import { GioHang } from '../models/giohang.model';
import { ChiTietGioHang } from '../models/chitietgiohang.model';
import { _ENDPOINTS } from '../constants/api.constants';
import { APP_CONSTANTS } from '../constants/app.constants';
import { ImageService } from './image.service';

export interface CartItem {
  chitietsanphamid: number;
  soluong: number;
  dongia: number;
  tensanpham?: string;
  tenmau?: string;
  hinhchinh?: string;
}

@Injectable({
  providedIn: 'root',
})
export class GioHangService {
  private cartItemsSubject = new BehaviorSubject<CartItem[]>([]);
  public cartItems$ = this.cartItemsSubject.asObservable();

  private cartCountSubject = new BehaviorSubject<number>(0);
  public cartCount$ = this.cartCountSubject.asObservable();

  private authService: any = null; // Use any to avoid circular dependency

  constructor(
    private apiService: ApiService,
    private storageService: StorageService,
    private injector: Injector,
    private imageService: ImageService
  ) {
    // Initialize cart from storage first
    this.loadCartFromStorage();

    // Delay AuthService injection to prevent circular dependency
    setTimeout(() => {
      try {
        const AuthServiceClass = this.injector.get('AuthService' as any);
        this.authService = AuthServiceClass;
        // Re-initialize after auth service is available
        this.initializeCart();
      } catch (error) {
        console.warn('[CartService] Could not inject AuthService:', error);
      }
    }, 100);
  }

  /**
   * Called by AuthService when user successfully logs in
   */
  public handleLogin(): void {
    console.log('[CartService] Handling login...');
    this.loadCartFromServer();
  }

  private initializeCart(): void {
    if (!this.authService || !this.authService.isAuthenticated) {
      // If authService is not ready, load from storage for now
      this.loadCartFromStorage();
      return;
    }

    const isAuthenticated = this.authService.isAuthenticated();
    if (isAuthenticated) {
      console.log(
        '[CartService] User is authenticated, loading cart from server.'
      );
      this.loadCartFromServer();
    } else {
      console.log(
        '[CartService] User not authenticated, loading cart from local storage.'
      );
      this.loadCartFromStorage();
    }
  }

  private loadCartFromStorage(): void {
    const cartData = this.storageService.getItem(APP_CONSTANTS.CART_KEY);
    if (cartData) {
      const items: CartItem[] = JSON.parse(cartData);
      this.cartItemsSubject.next(this.processCartItems(items));
      this.updateCartCount();
    } else {
      this.cartItemsSubject.next([]);
      this.updateCartCount();
    }
  }

  private processCartItems(items: CartItem[]): CartItem[] {
    return items.map((item) => ({
      ...item,
      hinhchinh: this.imageService.getImageUrl(item.hinhchinh || ''),
    }));
  }

  private loadCartFromServer(): void {
    if (!this.authService || !this.authService.getCurrentUser) {
      console.log('[CartService] AuthService not ready, loading from storage.');
      this.loadCartFromStorage();
      return;
    }

    const currentUser = this.authService.getCurrentUser();
    if (!currentUser || !currentUser.khachhang) {
      console.log(
        '[CartService] No customer data, loading from storage instead.'
      );
      this.loadCartFromStorage();
      return;
    }

    this.apiService
      .get<GioHang>(
        `${_ENDPOINTS.SPECIFIC.GIOHANG_BY_KHACHHANG.replace(
          '{khachHangId}',
          currentUser.khachhang.id.toString()
        )}`
      )
      .pipe(
        switchMap((gioHang) => {
          if (!gioHang || !gioHang.id) {
            console.log(
              '[CartService] No cart found for customer, creating empty cart.'
            );
            return of([]);
          }
          return this.apiService.get<ChiTietGioHang[]>(
            `${_ENDPOINTS.SPECIFIC.CHITIETGIOHANG_BY_GIOHANG.replace(
              '{giohangId}',
              gioHang.id.toString()
            )}`
          );
        }),
        map((chiTietGioHangs: ChiTietGioHang[]) => {
          return chiTietGioHangs.map((item) => ({
            chitietsanphamid: item.chitietsanpham?.id || 0,
            soluong: item.soluong,
            dongia: item.dongia,
            tensanpham: item.chitietsanpham?.sanpham?.tensanpham || 'Unknown',
            tenmau: item.chitietsanpham?.tenmau || '',
            hinhchinh: this.imageService.getImageUrl(
              item.chitietsanpham?.hinhchinh || ''
            ),
          }));
        }),
        catchError((error) => {
          console.error('[CartService] Error loading cart from server:', error);
          this.loadCartFromStorage();
          return of([]);
        })
      )
      .subscribe((items) => {
        this.cartItemsSubject.next(items);
        this.updateCartCount();
      });
  }

  addToCart(
    chiTietSanPhamIdOrItem: number | CartItem,
    quantity: number = 1,
    price: number = 0
  ): Observable<any> {
    if (!this.authService || !this.authService.isAuthenticated) {
      // If authService is not ready, add to local cart
      let cartItem: CartItem;
      if (typeof chiTietSanPhamIdOrItem === 'object') {
        cartItem = chiTietSanPhamIdOrItem;
      } else {
        cartItem = {
          chitietsanphamid: chiTietSanPhamIdOrItem,
          soluong: quantity,
          dongia: price,
        };
      }
      this.addToLocalCart(cartItem);
      return of({ success: true });
    }

    const isAuthenticated = this.authService.isAuthenticated();

    let cartItem: CartItem;
    if (typeof chiTietSanPhamIdOrItem === 'object') {
      // If it's already a CartItem object
      cartItem = chiTietSanPhamIdOrItem;
    } else {
      // If it's a number (chiTietSanPhamId)
      cartItem = {
        chitietsanphamid: chiTietSanPhamIdOrItem,
        soluong: quantity,
        dongia: price,
      };
    }

    if (isAuthenticated) {
      return this.addToServerCart(cartItem, true);
    } else {
      this.addToLocalCart(cartItem);
      return of({ success: true });
    }
  }

  private addToServerCart(
    cartItem: CartItem,
    shouldReload: boolean = true
  ): Observable<any> {
    const currentUser = this.authService.getCurrentUser();
    if (!currentUser || !currentUser.khachhang) {
      console.error(
        '[CartService] Cannot add to server cart: No customer data'
      );
      this.addToLocalCart(cartItem);
      return of({ success: false });
    }

    return this.apiService
      .get<GioHang>(
        `${_ENDPOINTS.SPECIFIC.GIOHANG_BY_KHACHHANG.replace(
          '{khachHangId}',
          currentUser.khachhang.id.toString()
        )}`
      )
      .pipe(
        switchMap((gioHang) => {
          if (!gioHang || !gioHang.id) {
            const newCart = { khachhangid: currentUser.khachhang!.id };
            return this.apiService.post<GioHang>(_ENDPOINTS.GIOHANG, newCart);
          }
          return of(gioHang);
        }),
        switchMap((gioHang) => {
          const chiTietGioHang = {
            giohangid: gioHang.id,
            chitietsanphamid: cartItem.chitietsanphamid,
            soluong: cartItem.soluong,
            dongia: cartItem.dongia,
          };
          return this.apiService.post(
            _ENDPOINTS.CHITIETGIOHANG,
            chiTietGioHang
          );
        }),
        tap(() => {
          if (shouldReload) {
            this.loadCartFromServer();
          }
        }),
        catchError((error) => {
          console.error('[CartService] Error adding to server cart:', error);
          this.addToLocalCart(cartItem);
          return of({ success: false });
        })
      );
  }

  private addToLocalCart(cartItem: CartItem): void {
    const existingItems = this.cartItemsSubject.value;
    const existingIndex = existingItems.findIndex(
      (item) => item.chitietsanphamid === cartItem.chitietsanphamid
    );

    let updatedItems: CartItem[];
    if (existingIndex > -1) {
      updatedItems = [...existingItems];
      updatedItems[existingIndex].soluong += cartItem.soluong;
    } else {
      updatedItems = [...existingItems, cartItem];
    }

    this.cartItemsSubject.next(updatedItems);
    this.updateCartCount();
    this.storageService.setItem(
      APP_CONSTANTS.CART_KEY,
      JSON.stringify(updatedItems)
    );
  }

  updateQuantity(chiTietSanPhamId: number, newQuantity: number): void {
    const isAuthenticated = this.authService.isAuthenticated();

    if (isAuthenticated) {
      this.updateServerCartQuantity(chiTietSanPhamId, newQuantity);
    } else {
      this.updateLocalCartQuantity(chiTietSanPhamId, newQuantity);
    }
  }

  private updateServerCartQuantity(
    chiTietSanPhamId: number,
    newQuantity: number
  ): void {
    const currentUser = this.authService.getCurrentUser();
    if (!currentUser || !currentUser.khachhang) {
      console.error(
        '[CartService] Cannot update server cart: No customer data'
      );
      return;
    }

    this.apiService
      .get<GioHang>(
        `${_ENDPOINTS.SPECIFIC.GIOHANG_BY_KHACHHANG.replace(
          '{khachHangId}',
          currentUser.khachhang.id.toString()
        )}`
      )
      .pipe(
        switchMap((gioHang) => {
          if (!gioHang || !gioHang.id) {
            throw new Error('Cart not found');
          }
          return this.apiService.get<ChiTietGioHang[]>(
            `${_ENDPOINTS.SPECIFIC.CHITIETGIOHANG_BY_GIOHANG.replace(
              '{giohangId}',
              gioHang.id.toString()
            )}`
          );
        }),
        switchMap((chiTietGioHangs) => {
          const item = chiTietGioHangs.find(
            (ct) => ct.chitietsanpham?.id === chiTietSanPhamId
          );
          if (!item) {
            throw new Error('Cart item not found');
          }

          const updateData = { ...item, soluong: newQuantity };
          return this.apiService.put(
            `${_ENDPOINTS.CHITIETGIOHANG}/${item.id}`,
            updateData
          );
        }),
        catchError((error) => {
          console.error('[CartService] Error updating server cart:', error);
          return of(null);
        })
      )
      .subscribe(() => {
        this.loadCartFromServer();
      });
  }

  private updateLocalCartQuantity(
    chiTietSanPhamId: number,
    newQuantity: number
  ): void {
    const existingItems = this.cartItemsSubject.value;
    const updatedItems = existingItems.map((item) =>
      item.chitietsanphamid === chiTietSanPhamId
        ? { ...item, soluong: newQuantity }
        : item
    );

    this.cartItemsSubject.next(updatedItems);
    this.updateCartCount();
    this.storageService.setItem(
      APP_CONSTANTS.CART_KEY,
      JSON.stringify(updatedItems)
    );
  }

  removeFromCart(chiTietSanPhamId: number): void {
    const isAuthenticated = this.authService.isAuthenticated();

    if (isAuthenticated) {
      this.removeFromServerCart(chiTietSanPhamId);
    } else {
      this.removeFromLocalCart(chiTietSanPhamId);
    }
  }

  private removeFromServerCart(chiTietSanPhamId: number): void {
    const currentUser = this.authService.getCurrentUser();
    if (!currentUser || !currentUser.khachhang) {
      console.error(
        '[CartService] Cannot remove from server cart: No customer data'
      );
      return;
    }

    this.apiService
      .get<GioHang>(
        `${_ENDPOINTS.SPECIFIC.GIOHANG_BY_KHACHHANG.replace(
          '{khachHangId}',
          currentUser.khachhang.id.toString()
        )}`
      )
      .pipe(
        switchMap((gioHang) => {
          if (!gioHang || !gioHang.id) {
            throw new Error('Cart not found');
          }
          return this.apiService.get<ChiTietGioHang[]>(
            `${_ENDPOINTS.SPECIFIC.CHITIETGIOHANG_BY_GIOHANG.replace(
              '{giohangId}',
              gioHang.id.toString()
            )}`
          );
        }),
        switchMap((chiTietGioHangs) => {
          const item = chiTietGioHangs.find(
            (ct) => ct.chitietsanpham?.id === chiTietSanPhamId
          );
          if (!item) {
            throw new Error('Cart item not found');
          }

          return this.apiService.delete(
            `${_ENDPOINTS.CHITIETGIOHANG}/${item.id}`
          );
        }),
        catchError((error) => {
          console.error(
            '[CartService] Error removing from server cart:',
            error
          );
          return of(null);
        })
      )
      .subscribe(() => {
        this.loadCartFromServer();
      });
  }

  private removeFromLocalCart(chiTietSanPhamId: number): void {
    const existingItems = this.cartItemsSubject.value;
    const updatedItems = existingItems.filter(
      (item) => item.chitietsanphamid !== chiTietSanPhamId
    );

    this.cartItemsSubject.next(updatedItems);
    this.updateCartCount();
    this.storageService.setItem(
      APP_CONSTANTS.CART_KEY,
      JSON.stringify(updatedItems)
    );
  }

  clearCart(): void {
    const isAuthenticated = this.authService.isAuthenticated();

    if (isAuthenticated) {
      this.clearServerCart();
    } else {
      this.clearLocalCart();
    }
  }

  private clearServerCart(): void {
    const currentUser = this.authService.getCurrentUser();
    if (!currentUser || !currentUser.khachhang) {
      console.error('[CartService] Cannot clear server cart: No customer data');
      return;
    }

    this.apiService
      .get<GioHang>(
        `${_ENDPOINTS.SPECIFIC.GIOHANG_BY_KHACHHANG.replace(
          '{khachHangId}',
          currentUser.khachhang.id.toString()
        )}`
      )
      .pipe(
        switchMap((gioHang) => {
          if (!gioHang || !gioHang.id) {
            return of(null);
          }
          return this.apiService.delete(
            `${_ENDPOINTS.SPECIFIC.CHITIETGIOHANG_CLEAR.replace(
              '{giohangId}',
              gioHang.id.toString()
            )}`
          );
        }),
        catchError((error) => {
          console.error('[CartService] Error clearing server cart:', error);
          return of(null);
        })
      )
      .subscribe(() => {
        this.loadCartFromServer();
      });
  }

  private clearLocalCart(): void {
    this.cartItemsSubject.next([]);
    this.updateCartCount();
    this.storageService.removeItem(APP_CONSTANTS.CART_KEY);
  }

  private updateCartCount(): void {
    const items = this.cartItemsSubject.value;
    const count = items.reduce((total, item) => total + item.soluong, 0);
    this.cartCountSubject.next(count);
  }

  getCurrentCartItems(): CartItem[] {
    return this.cartItemsSubject.value;
  }

  getCartItems(): CartItem[] {
    return this.cartItemsSubject.value;
  }

  getCartTotal(): number {
    const items = this.cartItemsSubject.value;
    return items.reduce((total, item) => total + item.soluong * item.dongia, 0);
  }

  getCartCount(): number {
    const items = this.cartItemsSubject.value;
    return items.reduce((total, item) => total + item.soluong, 0);
  }
}
