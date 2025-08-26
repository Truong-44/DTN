import { Component, OnInit, OnDestroy, inject, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Subject, forkJoin, of, interval } from 'rxjs';
import { takeUntil, catchError, finalize } from 'rxjs/operators';

import { SanPhamService } from '../../core/services/sanpham.service';
import { DanhMucService } from '../../core/services/danhmuc.service';
import { GioHangService } from '../../core/services/giohang.service';
import { AuthService } from '../../core/services/auth.service';
import { NotificationService } from '../../core/services/notification.service';
import { ImageService } from '../../core/services/image.service';
import { SanPham } from '../../core/models/sanpham.model';
import { DanhMuc } from '../../core/models/danhmuc.model';
import { ChiTietSanPham } from '../../core/models/chitietsanpham.model';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy, AfterViewInit {
  @ViewChild('featuredSlider') featuredSlider!: ElementRef;
  @ViewChild('newSlider') newSlider!: ElementRef;
  
  private router = inject(Router);
  private sanPhamService = inject(SanPhamService);
  private danhMucService = inject(DanhMucService);
  private gioHangService = inject(GioHangService);
  private authService = inject(AuthService);
  private notificationService = inject(NotificationService);
  private imageService = inject(ImageService);
  private destroy$ = new Subject<void>();

  // Data
  parentCategories: DanhMuc[] = [];
  featuredProducts: SanPham[] = [];
  newProducts: SanPham[] = [];
  isLoading = true;
  currentSlideIndex = 0;

  // Fixed categories data - 4 main categories
  fixedCategories = [
    {
      id: 1,
      tendanhmuc: 'Ghế',
      icon: '🪑',
      image: 'assets/img/categories/ghe.jpg'
    },
    {
      id: 2,
      tendanhmuc: 'Tủ',
      icon: '🗄️',
      image: 'assets/img/categories/tu.jpg'
    },
    {
      id: 3,
      tendanhmuc: 'Giường',
      icon: '🛏️',
      image: 'assets/img/categories/giuong.jpg'
    },
    {
      id: 4,
      tendanhmuc: 'Bàn',
      icon: '🪴',
      image: 'assets/img/categories/ban.jpg'
    }
  ];

  // Hero slides
  heroSlides = [
    {
      image: 'assets/img/BackGround/section1/BG101.webp',
      title: 'Nội thất hiện đại cho ngôi nhà của bạn',
      subtitle: 'Khám phá bộ sưu tập nội thất cao cấp với thiết kế độc đáo',
      ctaText: 'Khám phá ngay',
    },
    {
      image: 'assets/img/BackGround/section1/BG101.webp',
      title: 'Ưu đãi đặc biệt - Giảm đến 50%',
      subtitle: 'Cơ hội sở hữu nội thất chất lượng với giá tốt nhất',
      ctaText: 'Mua ngay',
    },
  ];

  // Static data
  services = [
    {
      icon: '🚚',
      title: 'Giao hàng miễn phí',
      desc: 'Miễn phí giao hàng cho đơn hàng trên 5 triệu',
    },
    {
      icon: '🔧',
      title: 'Lắp đặt chuyên nghiệp',
      desc: 'Đội ngũ thợ lành nghề, lắp đặt tận nơi',
    },
    {
      icon: '🛡️',
      title: 'Bảo hành dài hạn',
      desc: 'Bảo hành chính hãng lên đến 5 năm',
    },
    {
      icon: '💬',
      title: 'Tư vấn 24/7',
      desc: 'Hỗ trợ khách hàng mọi lúc, mọi nơi',
    },
  ];

  blogs = [
    {
      image: 'assets/img/BackGround/section3/BG301.webp',
      title: 'Xu hướng nội thất 2025',
      summary: 'Khám phá các phong cách nội thất nổi bật năm nay',
    },
    {
      image: 'assets/img/BackGround/section3/BG302.webp',
      title: 'Bí quyết chọn sofa phù hợp',
      summary: 'Những lưu ý khi chọn sofa cho phòng khách',
    },
  ];

  ngOnInit(): void {
    this.loadData();
    this.startHeroSlider();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  ngAfterViewInit(): void {
    // Setup smooth scrolling for sliders
    this.setupSliderScrolling();
  }

  private setupSliderScrolling(): void {
    // Enable smooth scrolling with trackpad/mouse wheel
    [this.featuredSlider, this.newSlider].forEach((sliderRef) => {
      if (sliderRef?.nativeElement) {
        const slider = sliderRef.nativeElement;
        
        // Enable horizontal scrolling with mouse wheel
        slider.addEventListener('wheel', (e: WheelEvent) => {
          e.preventDefault();
          const scrollAmount = e.deltaY > 0 ? 300 : -300;
          slider.scrollBy({
            left: scrollAmount,
            behavior: 'smooth'
          });
        });

        // Enable touch/trackpad scrolling
        slider.style.overflowX = 'auto';
        slider.style.scrollBehavior = 'smooth';
      }
    });
  }

  // Slider navigation methods
  scrollSlider(type: 'featured' | 'new', direction: 'left' | 'right'): void {
    const slider = type === 'featured' ? this.featuredSlider : this.newSlider;
    if (!slider?.nativeElement) return;

    const scrollAmount = 325; // Width of one card + gap
    const currentScroll = slider.nativeElement.scrollLeft;
    const targetScroll = direction === 'left' 
      ? currentScroll - scrollAmount 
      : currentScroll + scrollAmount;

    slider.nativeElement.scrollTo({
      left: targetScroll,
      behavior: 'smooth'
    });
  }

  private loadData(): void {
    this.isLoading = true;

    // Use fixed categories instead of loading from API
    this.parentCategories = this.fixedCategories as any[];

    forkJoin({
      products: this.sanPhamService
        .getAllSanPham()
        .pipe(catchError(() => of([]))),
      chiTietSanPham: this.sanPhamService
        .getAllChiTietSanPham()
        .pipe(catchError(() => of([]))),
    })
      .pipe(
        takeUntil(this.destroy$),
        finalize(() => (this.isLoading = false))
      )
      .subscribe({
        next: ({ products, chiTietSanPham }) => {
          console.log('Raw data loaded:', {
            products: products.length,
            chiTietSanPham: chiTietSanPham.length,
          });

          // Gán chi tiết sản phẩm vào từng sản phẩm (support both field names)
          products.forEach((product) => {
            product.chitietsanpham = chiTietSanPham.filter(
              (ct) => ct.sanphamId === product.id || ct.sanphamid === product.id
            );
          });

          // Hiển thị tất cả sản phẩm có trạng thái active
          const activeProducts = products.filter((p) => p.trangthai);
          console.log('Active products:', activeProducts.length);

          // Sản phẩm nổi bật - hiển thị tất cả sản phẩm có giảm giá trước
          const discountedProducts = activeProducts.filter(
            (p) => p.giacu && p.giamoi && p.giacu > p.giamoi
          );

          // Hiển thị tối đa 12 sản phẩm nổi bật
          this.featuredProducts =
            discountedProducts.length >= 12
              ? discountedProducts.slice(0, 12)
              : [
                  ...discountedProducts,
                  ...activeProducts.filter(
                    (p) => !discountedProducts.includes(p)
                  ),
                ].slice(0, 12);

          // Sản phẩm mới - hiển thị tối đa 12 sản phẩm còn lại
          this.newProducts = activeProducts
            .sort((a, b) => {
              if (a.ngaytao && b.ngaytao) {
                const dateA = new Date(a.ngaytao).getTime();
                const dateB = new Date(b.ngaytao).getTime();
                return dateB - dateA;
              }
              // Fallback to ID if no ngaytao
              return b.id - a.id;
            })
            .filter((p) => !this.featuredProducts.includes(p))
            .slice(0, 12);

          console.log('Processed data:', {
            fixedCategories: this.parentCategories.length,
            featuredProducts: this.featuredProducts.length,
            newProducts: this.newProducts.length,
          });

          // Debug first products
          if (this.featuredProducts.length > 0) {
            console.log('First featured product:', this.featuredProducts[0]);
            console.log(
              'First featured product chi tiet:',
              this.featuredProducts[0].chitietsanpham
            );
          }
        },
        error: (error) => {
          console.error('Error loading data:', error);
          this.notificationService.error(
            'Lỗi',
            'Không thể tải dữ liệu trang chủ'
          );
        },
      });
  }

  private startHeroSlider(): void {
    interval(5000)
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => {
        this.currentSlideIndex =
          (this.currentSlideIndex + 1) % this.heroSlides.length;
      });
  }

  // Navigation methods
  goToProducts(): void {
    this.router.navigate(['/products']);
  }

  goToCategory(category: any): void {
    // Navigate to products filtered by category name
    this.router.navigate(['/products'], {
      queryParams: { categoryName: category.tendanhmuc },
    });
  }

  goToProductDetail(id: number): void {
    this.router.navigate(['/product', id]);
  }

  goToAllProducts(): void {
    this.router.navigate(['/products']);
  }

  // Product methods
  addToCart(product: SanPham, event: Event): void {
    event.stopPropagation();

    if (!this.authService.isAuthenticated()) {
      this.notificationService.warning(
        'Cảnh báo',
        'Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng'
      );
      this.router.navigate(['/auth/login']);
      return;
    }

    if (!product.trangthai) {
      this.notificationService.warning(
        'Cảnh báo',
        'Sản phẩm hiện đang hết hàng'
      );
      return;
    }

    const detail = product.chitietsanpham?.[0];
    if (!detail) {
      this.notificationService.warning(
        'Cảnh báo',
        'Sản phẩm không có thông tin chi tiết'
      );
      return;
    }

    this.gioHangService
      .addToCart({
        chitietsanphamid: detail.id,
        soluong: 1,
        dongia: product.giamoi || 0,
        tensanpham: product.tensanpham,
        tenmau: detail.tenmau,
        hinhchinh: detail.hinhchinh,
      })
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (success) => {
          if (success) {
            this.notificationService.success(
              'Thành công',
              `Đã thêm ${product.tensanpham} vào giỏ hàng`
            );
          }
        },
        error: () =>
          this.notificationService.error(
            'Lỗi',
            'Không thể thêm sản phẩm vào giỏ hàng'
          ),
      });
  }

  subscribeNewsletter(email: string): void {
    if (!email || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
      this.notificationService.error(
        'Lỗi',
        !email ? 'Vui lòng nhập email' : 'Email không hợp lệ'
      );
      return;
    }
    this.notificationService.success(
      'Thành công',
      'Đăng ký nhận tin thành công!'
    );
  }

  // Utility methods
  getMainImage(product: SanPham): string {
    return this.imageService.getMainImage(product);
  }

  getSubImages(product: SanPham): string[] {
    return this.imageService.getSubImages(product);
  }

  getCategoryName(categoryId?: number): string {
    if (!categoryId) return '';

    // Check in all categories (not just parent categories) for category name
    const allCategories = [...this.parentCategories]; // extend if we have all categories loaded
    const category = allCategories.find((c) => c.id === categoryId);
    return category ? category.tendanhmuc : '';
  }

  getCategoryImage(category: any): string {
    // Return icon instead of image for fixed categories
    return category.icon || '🪑';
  }

  getCategoryIcon(category: any): string {
    return category.icon || '🪑';
  }

  formatPrice(price: number): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(price);
  }

  getDiscountPercent(product: SanPham): number {
    const giacu = product.giacu || 0;
    const giamoi = product.giamoi || 0;
    return giacu <= giamoi ? 0 : Math.round(((giacu - giamoi) / giacu) * 100);
  }

  onImageError(event: any): void {
    this.imageService.onImageError(event);
  }

  get currentSlide() {
    return this.heroSlides[this.currentSlideIndex];
  }

  // Track functions
  trackByProduct = (index: number, product: SanPham) => product.id;
  trackByCategory = (index: number, category: DanhMuc) => category.id;
  trackByService = (index: number, service: any) => service.title;
}
