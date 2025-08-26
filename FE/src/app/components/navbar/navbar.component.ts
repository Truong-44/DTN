import { Component, HostListener, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import {
  FontAwesomeModule,
  FaIconLibrary,
} from '@fortawesome/angular-fontawesome';
import {
  faCartShopping,
  faUser,
  faSearch,
  faSignOutAlt,
  faChevronDown,
} from '@fortawesome/free-solid-svg-icons';
import { debounceTime, distinctUntilChanged, Subject, Observable } from 'rxjs';

import { SanPhamService } from '../../core/services/sanpham.service';
import { DanhMucService } from '../../core/services/danhmuc.service';
import { AuthService } from '../../core/services/auth.service';
import { GioHangService } from '../../core/services/giohang.service';

import { SanPham } from '../../core/models/sanpham.model';
import { DanhMuc } from '../../core/models/danhmuc.model';
import { TaiKhoan } from '../../core/models/taikhoan.model';

@Component({
  standalone: true,
  selector: 'app-navbar',
  imports: [CommonModule, FormsModule, RouterModule, FontAwesomeModule],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent {
  private sanphamService = inject(SanPhamService);
  private danhMucService = inject(DanhMucService);
  private authService = inject(AuthService);
  private gioHangService = inject(GioHangService);
  private router = inject(Router);
  private library = inject(FaIconLibrary);

  isLoginOpen = false;
  isDanhMucOpen = false;
  isShrunk = false;
  keyword = '';
  suggestions: SanPham[] = [];
  searchInput$ = new Subject<string>();
  showSuggestions = false;
  danhMucs: DanhMuc[] = [];
  currentUser: TaiKhoan | null = null;
  cartCount$: Observable<number>;

  constructor() {
    this.library.addIcons(
      faCartShopping,
      faUser,
      faSearch,
      faSignOutAlt,
      faChevronDown
    );

    this.cartCount$ = this.gioHangService.cartCount$;

    this.danhMucService.getAll().subscribe((cats) => {
      let arr: DanhMuc[] = [];
      if (Array.isArray(cats)) {
        arr = cats;
      } else if (cats && typeof cats === 'object') {
        // Ép kiểu về any để tránh lỗi TS2339
        const obj = cats as any;
        arr = obj.items || obj.data || [];
      }
      this.danhMucs = arr;
      console.log('Danh mục navbar:', this.danhMucs);
    });

    this.authService.currentUser$.subscribe((u) => {
      console.log('🧭 [Navbar] User state updated:', u);
      if (u) {
        console.log('👤 [Navbar] KhachHang data:', u.khachhang);
        console.log('👤 [Navbar] Display name would be:', this.userDisplayName);
      }
      this.currentUser = u;
    });

    this.searchInput$
      .pipe(debounceTime(300), distinctUntilChanged())
      .subscribe((kw) => {
        if (kw.trim()) this.searchProducts(kw);
        else this.clearSuggestions();
      });
  }

  // SEARCH
  searchProducts(keyword: string) {
    this.sanphamService.searchSanPham(keyword).subscribe({
      next: (products) => (this.suggestions = (products || []).slice(0, 5)),
      error: () => (this.suggestions = []),
    });
    this.showSuggestions = true;
  }
  clearSuggestions() {
    this.suggestions = [];
    this.showSuggestions = false;
  }
  onInputChange() {
    this.searchInput$.next(this.keyword);
  }
  onSelectSuggestion(product: SanPham) {
    this.router.navigate(['/product', product.id]);
    this.keyword = '';
    this.clearSuggestions();
  }
  onEnter() {
    if (this.keyword.trim()) {
      this.router.navigate(['/search'], {
        queryParams: { keyword: this.keyword },
      });
      this.keyword = '';
      this.clearSuggestions();
    }
  }
  onBlur() {
    setTimeout(() => (this.showSuggestions = false), 200);
  }
  onFocus() {
    if (this.keyword.trim() && this.suggestions.length > 0)
      this.showSuggestions = true;
  }

  // NAVIGATION
  navigateToHome() {
    this.router.navigate(['/']);
    this.closeAllDropdowns();
  }
  navigateToCategory(id: number) {
    this.router.navigate(['/search'], { queryParams: { category: id } });
    this.closeAllDropdowns();
  }
  navigateToCart() {
    this.router.navigate(['/giohang']);
    this.closeAllDropdowns();
  }
  navigateToProfile() {
    this.router.navigate(['/profile']);
    this.closeAllDropdowns();
  }
  navigateToOrders() {
    console.log('🔍 === NAVIGATING TO ORDERS ===');

    // Kiểm tra trực tiếp từ AuthService
    const isAuth = this.authService.isAuthenticated();
    const currentUser = this.authService.getCurrentUser();
    const token = this.authService.getToken();

    console.log('🔐 AuthService.isAuthenticated():', isAuth);
    console.log('� AuthService.getCurrentUser():', currentUser);
    console.log('🔑 Token:', token);

    if (!isAuth || !currentUser || !token) {
      console.log('❌ Not authenticated, redirecting to login');
      this.router.navigate(['/login']);
      this.closeAllDropdowns();
      return;
    }

    console.log('✅ Authenticated, navigating to order-list');
    this.router.navigate(['/order-list']);
    this.closeAllDropdowns();
  }

  // DROPDOWN
  toggleLoginDropdown() {
    this.isLoginOpen = !this.isLoginOpen;
    this.isDanhMucOpen = false;
  }
  toggleDanhMucDropdown() {
    this.isDanhMucOpen = !this.isDanhMucOpen;
    this.isLoginOpen = false;
  }
  closeAllDropdowns() {
    this.isLoginOpen = false;
    this.isDanhMucOpen = false;
    this.showSuggestions = false;
  }

  // AUTH
  login() {
    this.router.navigate(['/login']);
    this.closeAllDropdowns();
  }
  register() {
    this.router.navigate(['/register']);
    this.closeAllDropdowns();
  }
  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
    this.closeAllDropdowns();
  }

  // UTILS
  getCategoryName(c: DanhMuc) {
    return c?.tendanhmuc || 'Unknown';
  }
  getProductName(p: SanPham) {
    return p?.tensanpham || 'Unknown';
  }
  getProductPrice(p: SanPham) {
    const price = p?.giamoi || p?.giacu || 0;
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(price);
  }
  getProductImage(p: SanPham) {
    const img = p?.chitietsanpham?.[0]?.hinhchinh;
    return img && img !== '' ? img : 'assets/img/no-image.png';
  }

  // TRACK BY
  trackByCategoryId(index: number, item: DanhMuc) {
    return item?.id || index;
  }
  trackByProductId(index: number, item: SanPham) {
    return item?.id || index;
  }

  // GETTERS
  get isAuthenticated() {
    return !!this.currentUser;
  }
  get userDisplayName() {
    console.log(
      '🏷️ userDisplayName getter called with user:',
      this.currentUser
    );

    if (!this.currentUser) return 'Guest';

    // Ưu tiên tên trong khachhang trước
    if (this.currentUser.khachhang?.hoten) {
      console.log(
        '🏷️ Using khachhang.hoten:',
        this.currentUser.khachhang.hoten
      );
      return this.currentUser.khachhang.hoten;
    }

    // Sau đó là tên nhân viên
    if (this.currentUser.nhanvien?.hoten) {
      console.log('🏷️ Using nhanvien.hoten:', this.currentUser.nhanvien.hoten);
      return this.currentUser.nhanvien.hoten;
    }

    // Cuối cùng là username hoặc email
    const fallback =
      this.currentUser.tendangnhap ||
      this.currentUser.email?.split('@')[0] ||
      'User';
    console.log('🏷️ Using fallback name:', fallback);
    return fallback;
  }

  // SHRINK NAVBAR ON SCROLL
  @HostListener('window:scroll')
  onScroll() {
    this.isShrunk = window.scrollY > 50;
  }
  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event) {
    const target = event.target as HTMLElement;
    if (!target.closest('.navbar')) this.closeAllDropdowns();
  }
}
