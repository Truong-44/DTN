import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { SanPham } from '../../core/models/sanpham.model';
import { ChiTietSanPham } from '../../core/models/chitietsanpham.model';
import { SanPhamService } from '../../core/services/sanpham.service';
import { ImageService } from '../../core/services/image.service';
import { BuynowComponent } from '../shared/buynow/buynow.component';
import { GioHangService, CartItem } from '../../core/services/giohang.service';

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, BuynowComponent],
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.scss'],
})
export class ProductDetailComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();

  // Basic properties that template expects
  productId = 0;
  product: SanPham | undefined = undefined;
  productDetails: ChiTietSanPham[] = [];
  selectedDetail: ChiTietSanPham | null = null;
  loading = false;
  error: string | null = null;
  selectedColor = '';
  selectedSize = '';
  activeImageIndex = 0;

  // Additional properties for template
  quantity = 1;
  activeTab = 1;
  showCartSuccess = false;
  showBuyNowModal = false;

  // Review properties
  reviewName = '';
  reviewContent = '';
  reviewRating = 5;
  reviews: any[] = [];

  // Other products
  visibleOtherProducts: SanPham[] = [];
  otherProductIndex = 0;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private sanPhamService: SanPhamService,
    private imageService: ImageService,
    private cdr: ChangeDetectorRef,
    private gioHangService: GioHangService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.pipe(takeUntil(this.destroy$)).subscribe((params) => {
      const id = +(params.get('id') || 0);
      if (id) {
        this.productId = id;
        this.loadAllData(id);
      }
    });
  }

  loadAllData(id: number): void {
    this.loading = true;
    this.error = null;

    // Load both product and details, then combine
    const product$ = this.sanPhamService.getSanPhamById(id);
    const details$ = this.sanPhamService.getChiTietSanPhamBySanPhamId(id);

    // Wait for both to complete
    product$.subscribe({
      next: (response: any) => {
        this.product = response?.data || response;
        console.log('✅ Product loaded:', this.product);
        
        // If we already have details, populate them
        if (this.productDetails.length > 0) {
          this.populateProductDetails();
        }
      },
      error: (error) => {
        this.error = 'Không thể tải thông tin sản phẩm';
        this.loading = false;
        console.error('❌ Error loading product:', error);
      },
    });

    details$.subscribe({
      next: (response: any) => {
        this.productDetails = response?.data || response || [];
        console.log('✅ Product details loaded:', this.productDetails);
        
        if (this.productDetails.length > 0) {
          this.selectedColor = this.productDetails[0].tenmau || '';
          this.selectedSize = this.productDetails[0].kichthuoc || '';
          this.selectedDetail = this.productDetails[0];
          
          // If we already have product, populate now
          if (this.product) {
            this.populateProductDetails();
          }
        }
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Không thể tải chi tiết sản phẩm';
        this.loading = false;
        console.error('❌ Error loading product details:', error);
      },
    });
  }

  private populateProductDetails(): void {
    if (this.product && this.productDetails.length > 0) {
      // Populate all product details with sanpham data
      this.productDetails.forEach(detail => {
        detail.sanpham = this.product;
      });
      
      // Update selectedDetail
      if (this.selectedDetail) {
        this.selectedDetail.sanpham = this.product;
        console.log('🔗 Populated all product details with sanpham data');
      }
    }
  }

  // Methods that template expects
  getCurrentImage(): string {
    if (this.selectedDetail?.hinhchinh) {
      return this.imageService.getImageUrl(this.selectedDetail.hinhchinh);
    }
    return this.imageService.getPlaceholderImage();
  }

  getImageList(): string[] {
    const images: string[] = [];
    if (this.selectedDetail?.hinhchinh) {
      images.push(this.imageService.getImageUrl(this.selectedDetail.hinhchinh));
    }
    if (this.selectedDetail?.hinhphu) {
      const subImages = this.imageService.getSubImagesFromDetail(
        this.selectedDetail
      );
      images.push(...subImages);
    }
    return images.length > 0
      ? images
      : [this.imageService.getPlaceholderImage()];
  }

  hasDiscount(): boolean {
    return !!(
      this.product?.giacu &&
      this.product?.giamoi &&
      this.product.giamoi < this.product.giacu
    );
  }

  getDiscountPercent(): number {
    if (!this.hasDiscount()) return 0;
    const old = this.product?.giacu || 0;
    const current = this.product?.giamoi || 0;
    return Math.round(((old - current) / old) * 100);
  }

  getCurrentPrice(): number {
    return this.product?.giamoi || this.product?.giacu || 0;
  }

  getOldPrice(): number {
    return this.product?.giacu || 0;
  }

  getAvailableColors(): string[] {
    return [
      ...new Set(
        this.productDetails
          .map((d) => d.tenmau)
          .filter((color): color is string => !!color)
      ),
    ];
  }

  getAvailableSizes(): string[] {
    return [
      ...new Set(
        this.productDetails
          .filter((d) => d.tenmau === this.selectedColor)
          .map((d) => d.kichthuoc)
          .filter((size): size is string => !!size)
      ),
    ];
  }

  selectColor(color: string): void {
    this.selectedColor = color;
    this.updateSelectedDetail();
  }

  selectSize(size: string): void {
    this.selectedSize = size;
    this.updateSelectedDetail();
  }

  private updateSelectedDetail(): void {
    this.selectedDetail =
      this.productDetails.find(
        (d) =>
          d.tenmau === this.selectedColor && d.kichthuoc === this.selectedSize
      ) || null;
    
    // The sanpham should already be populated, but ensure it's there
    if (this.selectedDetail && this.product && !this.selectedDetail.sanpham) {
      this.selectedDetail.sanpham = this.product;
      console.log('🔄 Re-populated sanpham in updateSelectedDetail');
    }
    
    if (this.selectedDetail) {
      console.log('📋 Selected detail updated:', {
        id: this.selectedDetail.id,
        color: this.selectedDetail.tenmau,
        size: this.selectedDetail.kichthuoc,
        hasSanPham: !!this.selectedDetail.sanpham,
        sanPhamPrice: {
          giacu: this.selectedDetail.sanpham?.giacu,
          giamoi: this.selectedDetail.sanpham?.giamoi
        }
      });
    }
  }

  onImageError(event: any): void {
    this.imageService.onImageError(event);
  }

  // Image methods
  selectImage(index: number): void {
    this.activeImageIndex = index;
  }

  // Quantity methods
  decreaseQty(): void {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  increaseQty(): void {
    if (this.quantity < this.getStock()) {
      this.quantity++;
    }
  }

  getStock(): number {
    return this.selectedDetail?.soluong || 0;
  }

  // Cart methods
  addToCart(): void {
    if (this.canAddToCart() && this.selectedDetail && this.product) {
      const item: CartItem = {
        chitietsanphamid: this.selectedDetail.id,
        soluong: this.quantity,
        dongia: this.getCurrentPrice(),
        tensanpham: this.product.tensanpham,
        tenmau: this.selectedDetail.tenmau,
        hinhchinh: this.selectedDetail.hinhchinh,
      };

      this.gioHangService.addToCart(item).subscribe({
        next: (success) => {
          if (success) {
            this.showCartSuccess = true;
            setTimeout(() => {
              this.showCartSuccess = false;
              this.cdr.detectChanges(); // Manually trigger change detection
            }, 3000);
          } else {
            this.error = 'Không thể thêm sản phẩm vào giỏ hàng.';
          }
        },
        error: (err) => {
          console.error('Add to cart error:', err);
          this.error = 'Có lỗi xảy ra khi thêm vào giỏ hàng.';
        },
      });
    }
  }

  canAddToCart(): boolean {
    return !!(
      this.selectedDetail &&
      this.quantity > 0 &&
      this.quantity <= this.getStock()
    );
  }

  // Buy now modal methods
  openBuyNowModal(): void {
    if (this.canAddToCart()) {
      this.showBuyNowModal = true;
    }
  }

  closeBuyNowModal(): void {
    this.showBuyNowModal = false;
  }

  onOrderCompleted(): void {
    this.showBuyNowModal = false;
    // Handle order completion
  }

  // Other products methods
  prevOtherProduct(): void {
    if (this.otherProductIndex > 0) {
      this.otherProductIndex--;
    }
  }

  nextOtherProduct(): void {
    if (!this.isNextOtherProductDisabled()) {
      this.otherProductIndex++;
    }
  }

  isNextOtherProductDisabled(): boolean {
    return this.otherProductIndex >= this.visibleOtherProducts.length - 1;
  }

  goToProductDetail(productId: number): void {
    this.router.navigate(['/product-detail', productId]);
  }

  getMainImage(product: SanPham): string {
    // Get the first detail's main image or use placeholder
    const firstDetail = product.chitietsanpham?.[0];
    return this.imageService.getImageUrl(firstDetail?.hinhchinh || '');
  }

  // Review methods
  submitReview(event: Event): void {
    event.preventDefault();
    if (this.reviewName && this.reviewContent && this.reviewRating) {
      const newReview = {
        name: this.reviewName,
        content: this.reviewContent,
        rating: this.reviewRating,
        date: new Date(),
      };
      this.reviews.push(newReview);

      // Reset form
      this.reviewName = '';
      this.reviewContent = '';
      this.reviewRating = 5;
    }
  }

  // Tracking functions for ngFor
  trackByColorName(index: number, color: string): string {
    return color;
  }

  trackBySizeName(index: number, size: string): string {
    return size;
  }

  trackByImageIndex(index: number, image: string): string {
    return `${index}-${image}`;
  }

  trackByProductId(index: number, product: SanPham): number {
    return product.id || index;
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
