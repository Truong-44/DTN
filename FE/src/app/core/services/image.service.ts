import { Injectable } from '@angular/core';
import { SanPham } from '../models/sanpham.model';
import { ChiTietSanPham } from '../models/chitietsanpham.model';

@Injectable({
  providedIn: 'root',
})
export class ImageService {
  /**
   * Lấy hình ảnh chính từ sản phẩm hoặc null
   * Backend format: "/sanpham/ghe/sofa/giorgio_01.jpg"
   * Frontend result: "assets/img/sanpham/ghe/sofa/giorgio_01.jpg"
   */
  getMainImage(product: SanPham | null): string {
    if (!product?.chitietsanpham?.length) {
      return this.getPlaceholderImage();
    }

    const mainDetail = product.chitietsanpham[0];
    if (mainDetail?.hinhchinh) {
      return this.buildImageUrlInternal(mainDetail.hinhchinh);
    }

    return this.getPlaceholderImage();
  }

  /**
   * Lấy hình ảnh chính từ ChiTietSanPham
   */
  getMainImageFromDetail(detail: ChiTietSanPham): string {
    if (detail?.hinhchinh) {
      return this.buildImageUrlInternal(detail.hinhchinh);
    }

    return this.getPlaceholderImage();
  }

  /**
   * Lấy tất cả hình ảnh phụ của sản phẩm
   * Backend format: "/sanpham/ghe/sofa/giorgio_02.jpg;/sanpham/ghe/sofa/giorgio_03.jpg"
   * Frontend result: ["assets/img/sanpham/ghe/sofa/giorgio_02.jpg", "assets/img/sanpham/ghe/sofa/giorgio_03.jpg"]
   */
  getSubImages(product: SanPham): string[] {
    const detail =
      product.chitietsanpham?.find((d) => d.hinhphu) ||
      product.chitietsanpham?.[0];

    if (detail?.hinhphu) {
      return detail.hinhphu
        .split(';')
        .filter((path) => path.trim())
        .map((path) => this.buildImageUrlInternal(path.trim()));
    }

    return [];
  }

  /**
   * Lấy hình ảnh phụ từ ChiTietSanPham
   */
  getSubImagesFromDetail(detail: ChiTietSanPham): string[] {
    if (detail?.hinhphu) {
      return detail.hinhphu
        .split(';')
        .filter((path) => path.trim())
        .map((path) => this.buildImageUrlInternal(path.trim()));
    }

    return [];
  }

  /**
   * Lấy tất cả hình ảnh (chính + phụ) của sản phẩm
   */
  getAllImages(product: SanPham): string[] {
    const mainImage = this.getMainImage(product);
    const subImages = this.getSubImages(product);

    // Combine và loại bỏ duplicate
    const allImages = [mainImage, ...subImages];
    return Array.from(new Set(allImages)).filter(
      (img) => !img.includes('placeholder') && !img.includes('data:image')
    );
  }

  /**
   * Build URL từ đường dẫn backend
   * Input: "/sanpham/ghe/sofa/giorgio_01.jpg"
   * Output: "assets/img/sanpham/ghe/sofa/giorgio_01.jpg"
   */
  public getImageUrl(imagePath: string): string {
    return this.buildImageUrlInternal(imagePath);
  }

  // Private internal method
  private buildImageUrlInternal(imagePath: string): string {
    if (!imagePath) return this.getPlaceholderImage();

    console.log('🔨 Building image URL for:', imagePath);

    // Remove leading slash if present: /sanpham/ghe/sofa/giorgio_01.jpg -> sanpham/ghe/sofa/giorgio_01.jpg
    const cleanPath = imagePath.startsWith('/')
      ? imagePath.substring(1)
      : imagePath;

    // Build full assets path: assets/img/sanpham/ghe/sofa/giorgio_01.jpg
    const fullPath = `assets/img/${cleanPath}`;
    console.log('✅ Built image URL:', fullPath);
    return fullPath;
  }

  /**
   * Hình ảnh placeholder khi không có hình
   */
  getPlaceholderImage(): string {
    return 'assets/img/placeholder/product.jpg';
  }

  /**
   * Base64 placeholder động (SVG) - fallback nếu file placeholder không tồn tại
   */
  getDynamicPlaceholder(): string {
    return 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNDAwIiBoZWlnaHQ9IjQwMCIgdmlld0JveD0iMCAwIDQwMCA0MDAiIGZpbGw9Im5vbmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjxyZWN0IHdpZHRoPSI0MDAiIGhlaWdodD0iNDAwIiBmaWxsPSIjRjNGNEY2Ii8+CjxyZWN0IHg9IjUwIiB5PSIxMDAiIHdpZHRoPSIzMDAiIGhlaWdodD0iMjAwIiByeD0iMjAiIGZpbGw9IiNEMUQ1REIiLz4KPHJlY3QgeD0iMTAwIiB5PSIxNTAiIHdpZHRoPSI4MCIgaGVpZ2h0PSI4MCIgcng9IjEwIiBmaWxsPSIjOUNBM0FGIi8+CjxyZWN0IHg9IjIxMCIgeT0iMTUwIiB3aWR0aD0iODAiIGhlaWdodD0iODAiIHJ4PSIxMCIgZmlsbD0iIzlDQTNBRiIvPgo8dGV4dCB4PSI1MCUiIHk9IjM1MCIgZG9taW5hbnQtYmFzZWxpbmU9ImNlbnRyYWwiIHRleHQtYW5jaG9yPSJtaWRkbGUiIGZpbGw9IiM2QjcyODAiIGZvbnQtc2l6ZT0iMTgiIGZvbnQtZmFtaWx5PSJzYW5zLXNlcmlmIj5HaOG6vyBTb2ZhIE1PSE88L3RleHQ+Cjwvc3ZnPgo=';
  }

  /**
   * Handle image error - set placeholder
   */
  onImageError(event: any): void {
    console.warn('Image failed to load:', event.target.src);
    event.target.src = this.getDynamicPlaceholder();
  }
}
