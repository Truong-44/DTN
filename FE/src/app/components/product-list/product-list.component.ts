import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

// Services
import { SanPhamService } from '../../core/services/sanpham.service';
import { LoadingService } from '../../core/services/loading.service';
import { ImageService } from '../../core/services/image.service';
import { GioHangService } from '../../core/services/giohang.service';
import { NotificationService } from '../../core/services/notification.service';

// Models
import { SanPham } from '../../core/models/sanpham.model';
import { DanhMuc } from '../../core/models/danhmuc.model';
import { ChiTietSanPham } from '../../core/models/chitietsanpham.model';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss'],
})
export class ProductListComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();

  // Data properties
  allProducts: SanPham[] = [];
  filteredProducts: SanPham[] = [];
  categories: DanhMuc[] = [];

  // Filter properties
  selectedCategoryId: number | null = null;
  searchKeyword: string = '';
  priceRange = { min: 0, max: 50000000 };
  sortBy: string = 'default';

  // UI state
  isLoading = false;
  hasError = false;
  errorMessage = '';

  // Pagination
  currentPage = 1;
  itemsPerPage = 12;
  totalItems = 0;

  constructor(
    private sanPhamService: SanPhamService,
    private loadingService: LoadingService,
    private imageService: ImageService,
    private gioHangService: GioHangService,
    private notificationService: NotificationService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadCategories();
    this.loadProducts();
    this.checkRouteParams();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  // ==================== DATA LOADING ====================

  loadCategories(): void {
    console.log('🚀 Loading categories from /api/danhmuc...');

    this.sanPhamService
      .getAllDanhMuc()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (response: any) => {
          console.log('✅ Raw categories API response:', response);
          console.log('📊 Categories response type:', typeof response);

          // Handle different response formats
          let categories: DanhMuc[] = [];

          if (Array.isArray(response)) {
            categories = response;
            console.log('📊 Categories response is direct array');
          } else if (
            response &&
            response.data &&
            Array.isArray(response.data)
          ) {
            categories = response.data;
            console.log('📊 Categories response has data property');
          } else if (
            response &&
            response.content &&
            Array.isArray(response.content)
          ) {
            categories = response.content;
            console.log('📊 Categories response has content property');
          } else {
            console.warn('⚠️ Unexpected categories response format:', response);
            categories = [];
          }

          console.log('✅ Categories extracted:', categories.length);
          if (categories.length > 0) {
            console.log('📊 Sample category:', categories[0]);
          }

          this.categories = categories;
        },
        error: (error: any) => {
          console.error('❌ Error loading categories from API:', error);
          this.categories = []; // Empty array if API fails
        },
      });
  }

  loadProducts(): void {
    this.isLoading = true;
    this.hasError = false;

    console.log('🚀 Loading products from /api/sanpham...');

    this.sanPhamService
      .getAllSanPham()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (response: any) => {
          console.log('✅ Raw API response:', response);
          console.log('📊 Response type:', typeof response);

          // Handle different response formats
          let products: SanPham[] = [];

          if (Array.isArray(response)) {
            products = response;
            console.log('📊 Response is direct array');
          } else if (
            response &&
            response.data &&
            Array.isArray(response.data)
          ) {
            products = response.data;
            console.log('📊 Response has data property');
          } else if (
            response &&
            response.content &&
            Array.isArray(response.content)
          ) {
            products = response.content;
            console.log('📊 Response has content property');
          } else {
            console.warn('⚠️ Unexpected response format:', response);
            products = [];
          }

          console.log('✅ Products extracted:', products.length);
          if (products.length > 0) {
            console.log('📊 Sample product:', products[0]);
          }

          // ✅ LOAD CHI TIẾT SẢN PHẨM ĐỂ LẤY HÌNH ẢNH
          this.loadProductDetails(products);
        },
        error: (error: any) => {
          console.error('❌ Error loading products:', error);
          this.hasError = true;
          this.errorMessage = 'Không thể tải danh sách sản phẩm';
          this.allProducts = [];
          this.filteredProducts = [];
          this.isLoading = false;
        },
      });
  }

  loadProductDetails(products: SanPham[]): void {
    console.log('🔄 Loading chi tiết sản phẩm for all products...');

    // Load all chi tiết sản phẩm at once
    this.sanPhamService
      .getAllChiTietSanPham()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (response: any) => {
          console.log('✅ Raw chi tiết response:', response);

          // Handle response format
          let allChiTiet: ChiTietSanPham[] = [];
          if (Array.isArray(response)) {
            allChiTiet = response;
          } else if (
            response &&
            response.data &&
            Array.isArray(response.data)
          ) {
            allChiTiet = response.data;
          } else {
            console.warn('⚠️ Unexpected chi tiết response format:', response);
            allChiTiet = [];
          }

          console.log('✅ All chi tiết loaded:', allChiTiet.length);
          if (allChiTiet.length > 0) {
            console.log('📊 Sample chi tiết:', allChiTiet[0]);
          }

          // Map chi tiết to corresponding products
          products.forEach((product: SanPham) => {
            // Try both field names: sanphamid (model) và sanphamId (API response)
            const productDetails = allChiTiet.filter(
              (ct: any) =>
                ct.sanphamid === product.id || ct.sanphamId === product.id
            );
            product.chitietsanpham = productDetails;

            if (productDetails.length > 0) {
              console.log(
                `✅ Product ${product.id} (${product.tensanpham}) has ${productDetails.length} chi tiết`
              );
              console.log(`📸 Main image: ${productDetails[0].hinhchinh}`);
            } else {
              console.log(
                `⚠️ Product ${product.id} (${product.tensanpham}) has no chi tiết`
              );
            }
          });

          this.allProducts = products;
          this.applyFilters();
          this.isLoading = false;
        },
        error: (error: any) => {
          console.error('❌ Error loading chi tiết sản phẩm:', error);
          // Still use products without details
          this.allProducts = products;
          this.applyFilters();
          this.isLoading = false;
        },
      });
  }

  // ==================== FILTERING & SORTING ====================

  applyFilters(): void {
    // Ensure allProducts is an array
    if (!Array.isArray(this.allProducts)) {
      console.warn('⚠️ allProducts is not an array:', this.allProducts);
      this.allProducts = [];
    }

    let filtered = [...this.allProducts];

    // Filter by category
    if (this.selectedCategoryId) {
      filtered = filtered.filter(
        (p) => p.danhmucid === this.selectedCategoryId
      );
    }

    // Filter by search keyword
    if (this.searchKeyword.trim()) {
      const keyword = this.searchKeyword.toLowerCase().trim();
      filtered = filtered.filter(
        (p) =>
          p.tensanpham.toLowerCase().includes(keyword) ||
          p.mota?.toLowerCase().includes(keyword)
      );
    }

    // Filter by price range
    filtered = filtered.filter((p) => {
      const price = p.giamoi || p.giacu || 0;
      return price >= this.priceRange.min && price <= this.priceRange.max;
    });

    // Sort products
    this.sortProducts(filtered);

    this.filteredProducts = filtered;
    this.totalItems = filtered.length;
    this.currentPage = 1; // Reset to first page when filtering

    console.log('🔍 Filtered products:', this.filteredProducts.length);
  }

  sortProducts(products: SanPham[]): void {
    switch (this.sortBy) {
      case 'price-asc':
        products.sort(
          (a, b) => (a.giamoi || a.giacu || 0) - (b.giamoi || b.giacu || 0)
        );
        break;
      case 'price-desc':
        products.sort(
          (a, b) => (b.giamoi || b.giacu || 0) - (a.giamoi || a.giacu || 0)
        );
        break;
      case 'name-asc':
        products.sort((a, b) => a.tensanpham.localeCompare(b.tensanpham));
        break;
      case 'name-desc':
        products.sort((a, b) => b.tensanpham.localeCompare(a.tensanpham));
        break;
      case 'newest':
        products.sort((a, b) => {
          const dateA = a.ngaytao ? new Date(a.ngaytao).getTime() : 0;
          const dateB = b.ngaytao ? new Date(b.ngaytao).getTime() : 0;
          return dateB - dateA;
        });
        break;
      default:
        // Keep original order
        break;
    }
  }

  // ==================== EVENT HANDLERS ====================

  onSearchChange(): void {
    this.applyFilters();
  }

  onCategoryChange(categoryId: number | null): void {
    this.selectedCategoryId = categoryId;
    this.applyFilters();
  }

  onPriceRangeChange(): void {
    this.applyFilters();
  }

  onSortChange(): void {
    this.applyFilters();
  }

  clearFilters(): void {
    this.selectedCategoryId = null;
    this.searchKeyword = '';
    this.priceRange = { min: 0, max: 50000000 };
    this.sortBy = 'default';
    this.applyFilters();
  }

  // ==================== NAVIGATION ====================

  checkRouteParams(): void {
    this.route.queryParams
      .pipe(takeUntil(this.destroy$))
      .subscribe((params) => {
        if (params['category']) {
          this.selectedCategoryId = +params['category'];
        }
        if (params['search']) {
          this.searchKeyword = params['search'];
        }
        this.applyFilters();
      });
  }

  // ==================== GETTERS ====================

  get paginatedProducts(): SanPham[] {
    if (!Array.isArray(this.filteredProducts)) {
      return [];
    }
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.filteredProducts.slice(startIndex, endIndex);
  }

  get totalPages(): number {
    return Math.ceil(this.totalItems / this.itemsPerPage);
  }

  // ==================== HELPER METHODS ====================

  trackByProduct(index: number, product: SanPham): number {
    return product.id;
  }

  trackByCategory(index: number, category: DanhMuc): number {
    return category.id;
  }

  getMainImage(productId: number): string {
    const product = this.allProducts.find((p) => p.id === productId);
    if (!product) {
      return this.imageService.getPlaceholderImage();
    }
    return this.imageService.getMainImage(product);
  }

  onImageError(event: any, product: SanPham): void {
    this.imageService.onImageError(event);
  }

  goToProductDetail(product: SanPham): void {
    this.router.navigate(['/product', product.id]);
  }

  getDiscountPercent(product: SanPham): number {
    if (!product.giacu || !product.giamoi || product.giacu <= product.giamoi) {
      return 0;
    }
    return Math.round(((product.giacu - product.giamoi) / product.giacu) * 100);
  }

  // ==================== CART METHODS ====================

  addToCart(product: SanPham, event: Event): void {
    event.stopPropagation(); // Prevent navigation to product detail

    if (!product.trangthai) {
      this.notificationService.warning('Sản phẩm hết hàng', 'Sản phẩm này hiện tại không có sẵn');
      return;
    }

    // Get the first available product detail (chi tiết sản phẩm)
    const firstDetail = product.chitietsanpham?.[0];
    
    if (!firstDetail) {
      this.notificationService.error('Lỗi', 'Không tìm thấy thông tin chi tiết sản phẩm');
      return;
    }

    const price = product.giamoi || product.giacu || 0;
    
    if (price <= 0) {
      this.notificationService.error('Lỗi', 'Giá sản phẩm không hợp lệ');
      return;
    }

    const cartItem = {
      chitietsanphamid: firstDetail.id,
      soluong: 1,
      dongia: price,
      tensanpham: product.tensanpham,
      tenmau: firstDetail.tenmau || 'Mặc định',
      hinhchinh: firstDetail.hinhchinh || ''
    };

    console.log('🛒 Adding to cart:', cartItem);

    this.gioHangService.addToCart(cartItem).subscribe({
      next: () => {
        this.notificationService.success(
          'Thêm vào giỏ hàng thành công!',
          `${product.tensanpham} đã được thêm vào giỏ hàng`
        );
      },
      error: (error) => {
        console.error('❌ Error adding to cart:', error);
        this.notificationService.error(
          'Lỗi',
          'Không thể thêm sản phẩm vào giỏ hàng. Vui lòng thử lại.'
        );
      }
    });
  }

  // ==================== UTILITY METHODS ====================

  formatPrice(price: number): string {
    return new Intl.NumberFormat('vi-VN').format(price);
  }

  getCategoryName(categoryId?: number): string {
    if (!categoryId) return 'Chưa phân loại';
    const category = this.categories.find((c) => c.id === categoryId);
    return category?.tendanhmuc || 'Chưa phân loại';
  }

  getSortLabel(sortValue: string): string {
    const sortLabels: { [key: string]: string } = {
      'default': 'Mặc định',
      'newest': 'Mới nhất',
      'price-asc': 'Giá: Thấp → Cao',
      'price-desc': 'Giá: Cao → Thấp',
      'name-asc': 'Tên: A → Z',
      'name-desc': 'Tên: Z → A'
    };
    return sortLabels[sortValue] || 'Mặc định';
  }

  // ==================== PAGINATION METHODS ====================

  changePage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  }
}
