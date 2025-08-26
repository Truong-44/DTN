import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

interface Product {
  id: number;
  tensanpham: string;
  mota?: string;
  trangthai: boolean;
  danhmucId?: number;
  danhmucTen?: string;
  tenloai?: string;
  giacu?: number;
  giamoi?: number;
  ngaytao?: Date | string;
  chitietsanpham?: ProductDetail[];
}

interface ProductDetail {
  id: number;
  sanphamId: number;
  tensanpham?: string;
  tenmau?: string;
  mamau?: string;
  chatlieu?: string;
  kichthuoc?: string;
  trongluong?: number;
  soluong: number;
  hinhchinh?: string;
  hinhphu?: string;
  // Backward compatibility
  mausac?: string;
  giamoi?: number;
  giacu?: number;
}

interface Category {
  id: number;
  tendanhmuc: string;
  trangthai: boolean;
}

interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
}

@Component({
  selector: 'app-product-management',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="product-management">
      <!-- Header -->
      <div class="header">
        <h1>Quản Lý Sản Phẩm</h1>
        <button class="btn-add" (click)="openAddForm()">
          Thêm Sản Phẩm
        </button>
      </div>

      <!-- Loading -->
      <div *ngIf="loading" class="loading">
        <div class="spinner"></div>
        <p>Đang tải dữ liệu...</p>
      </div>

      <!-- Error -->
      <div *ngIf="error" class="error">
        <h3>Lỗi kết nối:</h3>
        <pre>{{ error | json }}</pre>
        <button class="btn-retry" (click)="loadProducts()">Thử lại</button>
      </div>

      <!-- Success State -->
      <div *ngIf="!loading && !error && products.length > 0" class="content">
        <!-- Stats -->
        <div class="stats-grid">
          <div class="stat-card">
            <div class="stat-number">{{ products.length }}</div>
            <div class="stat-label">Tổng sản phẩm</div>
          </div>
          <div class="stat-card">
            <div class="stat-number">{{ getActiveProducts() }}</div>
            <div class="stat-label">Đang hiển thị</div>
          </div>
        </div>

        <!-- Search & Filter -->
        <div class="filters">
          <input 
            type="text" 
            [(ngModel)]="searchTerm" 
            (input)="filterProducts()"
            placeholder="Tìm kiếm sản phẩm..."
            class="search-input"
          >
          <select [(ngModel)]="statusFilter" (change)="filterProducts()" class="filter-select">
            <option value="">Tất cả trạng thái</option>
            <option value="true">Đang hiển thị</option>
            <option value="false">Đã ẩn</option>
          </select>
        </div>

        <!-- Products Table -->
        <div class="table-container">
          <table class="products-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Hình ảnh</th>
                <th>Tên sản phẩm</th>
                <th>Giá</th>
                <th>Trạng thái</th>
                <th>Thao tác</th>
              </tr>
            </thead>
            <tbody>
              <tr *ngFor="let product of filteredProducts; trackBy: trackByProductId" class="product-row">
                <td>{{ product.id }}</td>
                <td>
                  <img 
                    [src]="getImageSrc(product)" 
                    [alt]="product.tensanpham"
                    class="product-image"
                    (error)="onImageError($event)"
                  >
                </td>
                <td>
                  <div class="product-name">{{ product.tensanpham }}</div>
                </td>
                <td>
                  <div class="price-display">
                    <!-- Simple and safe price display -->
                    <span class="price" *ngIf="product.giamoi && product.giamoi > 0">
                      {{ formatPrice(product.giamoi) }}
                    </span>
                    <span class="price" *ngIf="(!product.giamoi || product.giamoi <= 0) && product.giacu && product.giacu > 0">
                      {{ formatPrice(product.giacu) }}
                    </span>
                    <span class="no-price" *ngIf="(!product.giamoi || product.giamoi <= 0) && (!product.giacu || product.giacu <= 0)">
                      Chưa có giá
                    </span>
                  </div>
                </td>
                <td>
                  <span [class]="'status ' + (product.trangthai ? 'active' : 'inactive')">
                    {{ product.trangthai ? 'Hiển thị' : 'Ẩn' }}
                  </span>
                </td>
                <td>
                  <div class="actions">
                    <div class="action-row">
                      <button class="btn-view" (click)="viewProduct(product)" title="Xem chi tiết">Xem</button>
                      <button class="btn-edit" (click)="editProduct(product)" title="Chỉnh sửa">Sửa</button>
                    </div>
                    <div class="action-row">
                      <button class="btn-toggle" (click)="toggleProduct(product)" 
                              [title]="product.trangthai ? 'Ẩn sản phẩm' : 'Hiển thị sản phẩm'">
                        {{ product.trangthai ? 'Ẩn' : 'Hiện' }}
                      </button>
                      <button class="btn-delete" (click)="deleteProduct(product)" title="Xóa">Xóa</button>
                    </div>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- No results -->
        <div *ngIf="filteredProducts.length === 0 && products.length > 0" class="no-results">
          <p>Không tìm thấy sản phẩm nào phù hợp</p>
        </div>
      </div>

      <!-- Empty State -->
      <div *ngIf="!loading && !error && products.length === 0" class="empty-state">
        <div class="empty-icon">Trống</div>
        <h3>Chưa có sản phẩm nào</h3>
        <p>Hãy thêm sản phẩm đầu tiên của bạn</p>
        <button class="btn-add-first" (click)="openAddForm()">Thêm sản phẩm đầu tiên</button>
      </div>

      <!-- Image Gallery Modal -->
      <div *ngIf="showImageGallery && currentVariant" class="modal-overlay" (click)="closeModals()">
        <div class="modal-content gallery-modal" (click)="$event.stopPropagation()">
          <div class="modal-header">
            <h2>Gallery - {{ currentVariant.mausac }} {{ currentVariant.kichthuoc }}</h2>
            <button class="btn-close" (click)="closeModals()">Đóng</button>
          </div>
          
          <div class="gallery-content">
            <div class="main-image">
              <img [src]="getVariantImage(currentVariant)" [alt]="'Hình chính'" class="large-image">
              <p class="image-caption">Hình chính</p>
            </div>
            
            <div *ngIf="getSecondaryImages(currentVariant).length > 0" class="secondary-images">
              <h3>Hình phụ:</h3>
              <div class="image-gallery">
                <div *ngFor="let secImg of getSecondaryImages(currentVariant)" class="gallery-item">
                  <img [src]="'assets/img/' + (secImg.startsWith('/') ? secImg.substring(1) : secImg)" 
                       [alt]="'Hình phụ'" class="gallery-image">
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Add/Edit Product Modal -->
      <div *ngIf="showAddForm || showEditForm" class="modal-overlay" (click)="closeModals()">
        <div class="modal-content large-modal" (click)="$event.stopPropagation()">
          <div class="modal-header">
            <h2>{{ showEditForm ? 'Chỉnh sửa sản phẩm' : 'Thêm sản phẩm mới' }}</h2>
            <button class="btn-close" (click)="closeModals()">Đóng</button>
          </div>
          
          <form class="product-form" (ngSubmit)="saveProduct()">
            <!-- Thông tin sản phẩm cơ bản -->
            <div class="form-section">
              <h3>Thông tin cơ bản</h3>
              <div class="form-row">
                <div class="form-group">
                  <label for="tensanpham">Tên sản phẩm *</label>
                  <input 
                    type="text" 
                    id="tensanpham"
                    [(ngModel)]="productForm.tensanpham" 
                    name="tensanpham"
                    class="form-control"
                    placeholder="Nhập tên sản phẩm..."
                    required
                  >
                </div>

                <div class="form-group">
                  <label for="danhmucId">Danh mục</label>
                  <select 
                    id="danhmucId"
                    [(ngModel)]="productForm.danhmucId" 
                    name="danhmucId"
                    class="form-control"
                  >
                    <option value="0">Chọn danh mục</option>
                    <option *ngFor="let category of categories" [value]="category.id">
                      {{ category.tendanhmuc }}
                    </option>
                  </select>
                </div>
              </div>

              <div class="form-group">
                <label for="mota">Mô tả *</label>
                <textarea 
                  id="mota"
                  [(ngModel)]="productForm.mota" 
                  name="mota"
                  class="form-control"
                  placeholder="Nhập mô tả sản phẩm..."
                  rows="4"
                  required
                ></textarea>
              </div>

              <div class="form-group">
                <label class="checkbox-label">
                  <input 
                    type="checkbox" 
                    [(ngModel)]="productForm.trangthai" 
                    name="trangthai"
                  >
                  <span>Hiển thị sản phẩm</span>
                </label>
              </div>
            </div>

            <!-- Chi tiết sản phẩm -->
            <div class="form-section">
              <h3>Chi tiết sản phẩm</h3>
              <div class="form-row">
                <div class="form-group">
                  <label for="tenmau">Tên màu</label>
                  <input 
                    type="text" 
                    id="tenmau"
                    [(ngModel)]="productDetailForm.tenmau" 
                    name="tenmau"
                    class="form-control"
                    placeholder="Cam, Trắng, Cam trắng, Trắng cam..."
                  >
                </div>

                <div class="form-group">
                  <label for="mamau">Mã màu</label>
                  <input 
                    type="text" 
                    id="mamau"
                    [(ngModel)]="productDetailForm.mamau" 
                    name="mamau"
                    class="form-control"
                    placeholder="#FF6600, #FFFFFF, #FF8533..."
                  >
                </div>

                <div class="form-group">
                  <label for="kichthuoc">Kích thước</label>
                  <input 
                    type="text" 
                    id="kichthuoc"
                    [(ngModel)]="productDetailForm.kichthuoc" 
                    name="kichthuoc"
                    class="form-control"
                    placeholder="L100xW80xH45cm..."
                  >
                </div>
              </div>

              <div class="form-row">
                <div class="form-group">
                  <label for="chatlieu">Chất liệu</label>
                  <input 
                    type="text" 
                    id="chatlieu"
                    [(ngModel)]="productDetailForm.chatlieu" 
                    name="chatlieu"
                    class="form-control"
                    placeholder="Gỗ tự nhiên, Nỉ..."
                  >
                </div>

                <div class="form-group">
                  <label for="trongluong">Trọng lượng (kg)</label>
                  <input 
                    type="number" 
                    id="trongluong"
                    [(ngModel)]="productDetailForm.trongluong" 
                    name="trongluong"
                    class="form-control"
                    placeholder="0"
                    min="0"
                    step="0.1"
                  >
                </div>

                <div class="form-group">
                  <label for="soluong">Số lượng</label>
                  <input 
                    type="number" 
                    id="soluong"
                    [(ngModel)]="productDetailForm.soluong" 
                    name="soluong"
                    class="form-control"
                    placeholder="0"
                    min="0"
                  >
                </div>
              </div>

              <div class="form-row">
                <div class="form-group">
                  <label for="baohanh">Bảo hành (tháng)</label>
                  <input 
                    type="number" 
                    id="baohanh"
                    [(ngModel)]="productDetailForm.baohanh" 
                    name="baohanh"
                    class="form-control"
                    placeholder="12"
                    min="0"
                  >
                </div>

                <div class="form-group">
                  <label for="xuatxu">Xuất xứ</label>
                  <input 
                    type="text" 
                    id="xuatxu"
                    [(ngModel)]="productDetailForm.xuatxu" 
                    name="xuatxu"
                    class="form-control"
                    placeholder="Việt Nam, Trung Quốc..."
                  >
                </div>
              </div>

              <div class="form-row">
                <div class="form-group">
                  <label for="detail_giacu">Giá cũ chi tiết</label>
                  <input 
                    type="number" 
                    id="detail_giacu"
                    [(ngModel)]="productDetailForm.giacu" 
                    name="detail_giacu"
                    class="form-control"
                    placeholder="0"
                    min="0"
                  >
                </div>

                <div class="form-group">
                  <label for="detail_giamoi">Giá mới chi tiết</label>
                  <input 
                    type="number" 
                    id="detail_giamoi"
                    [(ngModel)]="productDetailForm.giamoi" 
                    name="detail_giamoi"
                    class="form-control"
                    placeholder="0"
                    min="0"
                  >
                </div>
              </div>

              <div class="form-row">
                <div class="form-group full-width">
                  <label for="detail_mota">Mô tả chi tiết</label>
                  <textarea 
                    id="detail_mota"
                    [(ngModel)]="productDetailForm.mota" 
                    name="detail_mota"
                    class="form-control"
                    placeholder="Mô tả chi tiết về biến thể này..."
                    rows="3"
                  ></textarea>
                </div>
              </div>

              <div class="form-row">
                <div class="form-group">
                  <label for="mainCategory">Loại sản phẩm</label>
                  <select 
                    id="mainCategory"
                    [(ngModel)]="productDetailForm.mainCategory" 
                    name="mainCategory"
                    class="form-control"
                    (ngModelChange)="onMainCategoryChange()"
                  >
                    <option value="">Chọn loại sản phẩm...</option>
                    <option value="ban">Bàn</option>
                    <option value="ghe">Ghế</option>
                    <option value="giuong">Giường</option>
                    <option value="tu">Tủ</option>
                  </select>
                </div>

                <div class="form-group">
                  <label for="subCategory">Danh mục con</label>
                  <select 
                    id="subCategory"
                    [(ngModel)]="productDetailForm.subCategory" 
                    name="subCategory"
                    class="form-control"
                    [disabled]="!productDetailForm.mainCategory"
                    (ngModelChange)="updateImagePath()"
                  >
                    <option value="">Chọn danh mục con...</option>
                    <option *ngFor="let sub of getSubCategories()" [value]="sub.value">
                      {{ sub.label }}
                    </option>
                  </select>
                </div>
              </div>

              <div class="form-row" *ngIf="productDetailForm.mainCategory && productDetailForm.subCategory">
                <div class="form-group full-width">
                  <label>Đường dẫn hình ảnh: <strong>sanpham/{{ productDetailForm.mainCategory }}/{{ productDetailForm.subCategory }}/</strong></label>
                </div>
              </div>

              <div class="form-row">
                <div class="form-group full-width">
                  <label for="imageFiles">Chọn hình ảnh từ máy tính</label>
                  <input 
                    type="file" 
                    id="imageFiles"
                    (change)="onFileSelect($event)"
                    class="form-control file-input"
                    accept="image/*"
                    multiple
                  >
                  <small class="form-text">Chọn nhiều hình ảnh cùng lúc. Định dạng: JPG, PNG, WEBP, SVG</small>
                  
                  <!-- Preview selected files -->
                  <div *ngIf="selectedFiles.length > 0" class="file-preview">
                    <h4>Hình ảnh đã chọn:</h4>
                    <div class="file-list">
                      <div *ngFor="let file of selectedFiles; let i = index" class="file-item">
                        <span class="file-name">{{ file.name }}</span>
                        <button type="button" class="btn-remove" (click)="removeFile(i)">×</button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Manual input fallback -->
              <div class="form-row">
                <div class="form-group full-width">
                  <label for="hinhanh">Hoặc nhập tên file thủ công</label>
                  <input 
                    type="text" 
                    id="hinhanh"
                    [(ngModel)]="productDetailForm.hinhanh" 
                    name="hinhanh"
                    class="form-control"
                    placeholder="aurora_01.jpg,aurora_02.jpg..."
                  >
                  <small class="form-text">Chỉ tên file, cách nhau bởi dấu phẩy</small>
                </div>
              </div>
            </div>

            <div class="form-actions">
              <button type="button" class="btn-cancel" (click)="closeModals()">Hủy</button>
              <button type="submit" class="btn-save">
                {{ showEditForm ? 'Cập nhật' : 'Thêm mới' }}
              </button>
            </div>
          </form>
        </div>
      </div>

      <!-- View Product Modal -->
      <div *ngIf="showViewModal && currentProduct" class="modal-overlay" (click)="closeModals()">
        <div class="modal-content view-modal" (click)="$event.stopPropagation()">
          <div class="modal-header">
            <h2>Chi tiết sản phẩm #{{ currentProduct.id }}</h2>
            <button class="btn-close" (click)="closeModals()">Đóng</button>
          </div>
          
          <div class="product-details">
            <!-- Thông tin cơ bản -->
            <div class="section">
              <h3>Thông tin cơ bản</h3>
              <div class="detail-grid">
                <div class="detail-item">
                  <label>ID sản phẩm:</label>
                  <span>{{ currentProduct.id }}</span>
                </div>
                <div class="detail-item">
                  <label>Tên sản phẩm:</label>
                  <span>{{ currentProduct.tensanpham }}</span>
                </div>
                <div class="detail-item">
                  <label>Loại sản phẩm:</label>
                  <span>{{ currentProduct.tenloai || 'Chưa xác định' }}</span>
                </div>
                <div class="detail-item">
                  <label>Danh mục:</label>
                  <span>{{ currentProduct.danhmucId }}</span>
                </div>
                <div class="detail-item">
                  <label>Trạng thái:</label>
                  <span [class]="'status ' + (currentProduct.trangthai ? 'active' : 'inactive')">
                    {{ currentProduct.trangthai ? 'Hiển thị' : 'Ẩn' }}
                  </span>
                </div>
                <div class="detail-item" *ngIf="currentProduct.ngaytao">
                  <label>Ngày tạo:</label>
                  <span>{{ formatDate(currentProduct.ngaytao!) }}</span>
                </div>
              </div>
              
              <div class="detail-item description" *ngIf="currentProduct.mota">
                <label>Mô tả:</label>
                <div class="description-content">{{ currentProduct.mota }}</div>
              </div>
            </div>

            <!-- Giá sản phẩm -->
            <div class="section" *ngIf="hasValidPrices(currentProduct)">
              <h3>Giá sản phẩm</h3>
              <div class="price-info">
                <div class="price-item" *ngIf="currentProduct.giacu">
                  <label>Giá cũ:</label>
                  <span class="old-price">{{ formatPrice(currentProduct.giacu) }}</span>
                </div>
                <div class="price-item" *ngIf="currentProduct.giamoi">
                  <label>Giá mới:</label>
                  <span class="new-price">{{ formatPrice(currentProduct.giamoi) }}</span>
                </div>
                <div class="price-range" *ngIf="currentProduct.chitietsanpham?.length">
                  <label>Khoảng giá từ chi tiết:</label>
                  <span class="price-range-value">
                    {{ currentProduct.giamoi || currentProduct.giacu || 'Chưa có giá' }}
                  </span>
                </div>
              </div>
            </div>

            <!-- Thống kê tổng quan -->
            <div class="section">
              <h3>Thống kê</h3>
              <div class="stats-grid">
                <div class="stat-item">
                  <label>Tổng tồn kho:</label>
                  <span>{{ getTotalStock(currentProduct) }}</span>
                </div>
                <div class="stat-item" *ngIf="hasValidPrices(currentProduct)">
                  <label>Giá trị tồn kho:</label>
                  <span>{{ formatPrice(getInventoryValue(currentProduct)) }}</span>
                </div>
              </div>
            </div>
          </div>
          
          <div class="modal-footer">
            <button class="btn-edit" (click)="editProduct(currentProduct)">Chỉnh sửa</button>
            <button class="btn-close" (click)="closeModals()">Đóng</button>
          </div>
        </div>
      </div>

      <!-- Delete Confirmation Modal -->
      <div *ngIf="showDeleteConfirm && currentProduct" class="modal-overlay" (click)="closeModals()">
        <div class="modal-content delete-modal" (click)="$event.stopPropagation()">
          <div class="modal-header">
            <h2>Xác nhận xóa</h2>
            <button class="btn-close" (click)="closeModals()">Đóng</button>
          </div>
          
          <div class="delete-content">
            <div class="warning-icon">!</div>
            <p>Bạn có chắc chắn muốn xóa sản phẩm này?</p>
            <div class="product-info">
              <strong>{{ currentProduct.tensanpham }}</strong>
            </div>
            <p class="warning-text">Hành động này không thể hoàn tác!</p>
          </div>
          
          <div class="modal-footer">
            <button class="btn-cancel" (click)="closeModals()">Hủy</button>
            <button class="btn-delete-confirm" (click)="confirmDelete()">Xóa</button>
          </div>
        </div>
      </div>
    </div>
  `,
  styleUrls: ['./product-management.component.scss']
})
export class ProductManagementComponent implements OnInit {
  products: Product[] = [];
  filteredProducts: Product[] = [];
  categories: Category[] = [];
  loading = false;
  error: any = null;
  
  // Filters
  searchTerm = '';
  statusFilter = '';
  
  // Modals
  showAddForm = false;
  showEditForm = false;
  showViewModal = false;
  showDeleteConfirm = false;
  showImageGallery = false;
  
  // Current product for operations
  currentProduct: Product | null = null;
  currentVariant: ProductDetail | null = null;
  
  // Form data
  productForm = {
    tensanpham: '',
    mota: '',
    danhmucId: 0,
    giacu: 0,
    giamoi: 0, // Không set giá mặc định
    trangthai: true
  };

  productDetailForm = {
    tenmau: 'Cam',
    mamau: '#FF6600',
    kichthuoc: '',
    chatlieu: '',
    trongluong: 0,
    soluong: 0,
    baohanh: 12,
    xuatxu: '',
    giacu: 0,
    giamoi: 0,
    mota: '',
    hinhanh: '',
    mainCategory: '',
    subCategory: ''
  };

  // File handling
  selectedFiles: File[] = [];

  // Category mapping
  categoryMapping = {
    ban: [
      { value: 'an', label: 'Bàn Ăn' },
      { value: 'anan', label: 'Bàn Ăn Nhỏ' },
      { value: 'lamviec', label: 'Bàn Làm Việc' },
      { value: 'tra', label: 'Bàn Trà' }
    ],
    ghe: [
      { value: 'sofa', label: 'Sofa' },
      { value: 'ghean', label: 'Ghế Ăn' },
      { value: 'lamviec', label: 'Ghế Làm Việc' }
    ],
    giuong: [
      { value: '', label: 'Giường' }
    ],
    tu: [
      { value: 'quanao', label: 'Tủ Quần Áo' },
      { value: 'bep', label: 'Tủ Bếp' },
      { value: 'sach', label: 'Tủ Sách' }
    ]
  };

  // Available asset directories
  assetDirectories = [
    'sanpham/ban/an/',
    'sanpham/ban/anan/', 
    'sanpham/ban/lamviec/',
    'sanpham/ban/tra/',
    'sanpham/ghe/sofa/',
    'sanpham/giuong/'
  ];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    console.log('🚀 ProductManagementComponent started');
    console.log('🔄 Initial loading state:', this.loading);
    this.loadInitialData();
  }

  loadInitialData() {
    this.loading = true;
    Promise.all([
      this.loadProducts(),
      this.loadCategories()
    ]).then(() => {
      console.log('✅ All initial data loaded');
      this.loading = false;
    }).catch((error) => {
      console.error('❌ Error loading initial data:', error);
      this.loading = false;
    });
  }

  loadProducts(): Promise<void> {
    console.log('📡 Loading products from API...');
    this.error = null;

    return new Promise((resolve) => {
      this.http.get<ApiResponse<Product[]>>('http://localhost:8080/api/sanpham').subscribe({
        next: (response) => {
          console.log('✅ Products loaded successfully:', response);
          if (response.success && response.data) {
            this.products = response.data;
            this.filteredProducts = [...this.products];
          } else {
            this.error = 'Invalid response format';
          }
          resolve();
        },
        error: (error) => {
          console.error('❌ Error loading products:', error);
          this.error = error;
          resolve();
        }
      });
    });
  }

  loadCategories(): Promise<void> {
    return new Promise((resolve) => {
      this.http.get<ApiResponse<Category[]>>('http://localhost:8080/api/danhmuc').subscribe({
        next: (response) => {
          console.log('✅ Categories loaded:', response);
          if (response.success && response.data) {
            this.categories = response.data;
          }
          resolve();
        },
        error: (error) => {
          console.error('❌ Error loading categories:', error);
          resolve();
        }
      });
    });
  }

  filterProducts() {
    this.filteredProducts = this.products.filter(product => {
      const matchesSearch = !this.searchTerm || 
        product.tensanpham.toLowerCase().includes(this.searchTerm.toLowerCase());
      
      const matchesStatus = !this.statusFilter || 
        product.trangthai.toString() === this.statusFilter;

      return matchesSearch && matchesStatus;
    });
  }

  getActiveProducts(): number {
    return this.products.filter(p => p.trangthai).length;
  }

  getTotalVariants(): number {
    return this.products.reduce((total, product) => 
      total + (product.chitietsanpham?.length || 0), 0
    );
  }

  // Temporarily disabled to prevent change detection loops
  /*
  getProductImage(product: Product): string {
    if (product.chitietsanpham && product.chitietsanpham.length > 0) {
      const hinhchinh = product.chitietsanpham[0].hinhchinh;
      if (hinhchinh) {
        // Remove leading slash và map path từ backend sang assets
        const cleanPath = hinhchinh.startsWith('/') ? hinhchinh.substring(1) : hinhchinh;
        return `assets/img/${cleanPath}`;
      }
    }
    return 'assets/img/placeholder/product.jpg';
  }
  */

  // Image cache to prevent recalculation
  private imageCache = new Map<number, string>();

  // Safe image getter method with caching
  getImageSrc(product: any): string {
    if (this.imageCache.has(product.id)) {
      return this.imageCache.get(product.id)!;
    }

    const hinhchinh = product?.chitietsanpham?.[0]?.hinhchinh;
    let imageSrc: string;
    
    if (hinhchinh) {
      const cleanPath = hinhchinh.startsWith('/') ? hinhchinh.substring(1) : hinhchinh;
      imageSrc = `assets/img/${cleanPath}`;
    } else {
      imageSrc = 'assets/img/placeholder/product.jpg';
    }

    this.imageCache.set(product.id, imageSrc);
    return imageSrc;
  }

  // TrackBy function for performance optimization
  trackByProductId(index: number, product: any): number {
    return product.id;
  }

  onImageError(event: any) {
    event.target.src = 'assets/img/placeholder/product.jpg';
  }

  getVariantImage(variant: ProductDetail): string {
    if (variant.hinhchinh) {
      // Remove leading slash và map path từ backend sang assets
      const cleanPath = variant.hinhchinh.startsWith('/') ? variant.hinhchinh.substring(1) : variant.hinhchinh;
      return `assets/img/${cleanPath}`;
    }
    return 'assets/img/placeholder/product.jpg';
  }

  // Temporarily disabled to prevent change detection loops
  /*
  getMinPrice(product: Product): number {
    // Ưu tiên giá từ sản phẩm chính
    if (product.giamoi && product.giamoi > 0) return product.giamoi;
    if (product.giacu && product.giacu > 0) return product.giacu;
    
    // Nếu không có giá chính, lấy từ chi tiết
    if (!product.chitietsanpham || product.chitietsanpham.length === 0) return 0;
    const prices = product.chitietsanpham
      .map(ct => ct.giamoi || ct.giacu || 0)
      .filter(price => price > 0);
    return prices.length > 0 ? Math.min(...prices) : 0;
  }

  getMaxPrice(product: Product): number {
    // Ưu tiên giá từ sản phẩm chính
    if (product.giamoi && product.giamoi > 0) return product.giamoi;
    if (product.giacu && product.giacu > 0) return product.giacu;
    
    // Nếu không có giá chính, lấy từ chi tiết
    if (!product.chitietsanpham || product.chitietsanpham.length === 0) return 0;
    const prices = product.chitietsanpham
      .map(ct => ct.giamoi || ct.giacu || 0)
      .filter(price => price > 0);
    return prices.length > 0 ? Math.max(...prices) : 0;
  }
  */

  formatPrice(price: number): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND'
    }).format(price);
  }

  formatDate(date: Date | string): string {
    const d = new Date(date);
    return d.toLocaleDateString('vi-VN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  hasValidPrices(product: Product): boolean {
    return !!(product.giacu || product.giamoi || 
      (product.chitietsanpham && product.chitietsanpham.some(ct => ct.giamoi || ct.giacu)));
  }

  getTotalStock(product: Product): number {
    if (!product.chitietsanpham) return 0;
    return product.chitietsanpham.reduce((total, ct) => total + (ct.soluong || 0), 0);
  }

  getInventoryValue(product: Product): number {
    if (!product.chitietsanpham) return 0;
    return product.chitietsanpham.reduce((total, ct) => {
      const price = ct.giamoi || ct.giacu || 0;
      const quantity = ct.soluong || 0;
      return total + (price * quantity);
    }, 0);
  }

  viewProduct(product: Product) {
    console.log('👁️ View product:', product);
    this.currentProduct = product;
    this.showViewModal = true;
  }

  editProduct(product: Product) {
    console.log('✏️ Edit product:', product);
    this.currentProduct = product;
    this.productForm = {
      tensanpham: product.tensanpham,
      mota: product.mota || '',
      danhmucId: product.danhmucId || 0,
      giacu: product.giacu || 0,
      giamoi: product.giamoi || 0,
      trangthai: product.trangthai
    };
    this.showEditForm = true;
  }

  deleteProduct(product: Product) {
    console.log('🗑️ Delete product:', product);
    this.currentProduct = product;
    this.showDeleteConfirm = true;
  }

  toggleProduct(product: Product) {
    console.log('🔄 Toggle product status:', product);
    const newStatus = !product.trangthai;
    
    this.http.patch(`http://localhost:8080/api/sanpham/${product.id}/status`, { trangthai: newStatus }).subscribe({
      next: (response) => {
        console.log('✅ Product status updated:', response);
        product.trangthai = newStatus;
        this.showNotification(`${newStatus ? 'Hiển thị' : 'Ẩn'} sản phẩm thành công!`, 'success');
      },
      error: (error) => {
        console.error('❌ Error updating product status:', error);
        this.showNotification('Lỗi cập nhật trạng thái sản phẩm!', 'error');
      }
    });
  }

  // CRUD Operations
  openAddForm() {
    this.resetForm();
    this.showAddForm = true;
  }

  saveProduct() {
    if (!this.validateForm()) return;

    const productData = {
      tensanpham: this.productForm.tensanpham,
      mota: this.productForm.mota,
      danhmucId: Number(this.productForm.danhmucId), // Ensure it's a number
      giacu: Number(this.productForm.giacu) || 0,
      giamoi: Number(this.productForm.giamoi) || 0,
      trangthai: Boolean(this.productForm.trangthai)
    };

    console.log('🚀 Saving product with data:', productData);
    console.log('📋 Product detail form data:', this.productDetailForm);

    // Validate required fields
    if (!productData.tensanpham || !productData.mota || !productData.danhmucId) {
      this.showNotification('Vui lòng điền đầy đủ thông tin bắt buộc!', 'error');
      return;
    }

    if (this.showEditForm && this.currentProduct) {
      // Update existing product
      this.http.put(`http://localhost:8080/api/sanpham/${this.currentProduct.id}`, productData).subscribe({
        next: (response) => {
          console.log('✅ Product updated:', response);
          this.showNotification('Cập nhật sản phẩm thành công!', 'success');
          this.loadProducts();
          this.closeModals();
        },
        error: (error) => {
          console.error('❌ Error updating product:', error);
          this.showNotification('Lỗi cập nhật sản phẩm!', 'error');
        }
      });
    } else {
      // Create new product - always create detail
      this.http.post('http://localhost:8080/api/sanpham', productData).subscribe({
        next: (response: any) => {
          console.log('✅ Product created:', response);
          console.log('📝 Creating product detail for product ID:', response.id);
          
          // Always create product detail
          if (this.selectedFiles.length > 0 && this.productDetailForm.mainCategory && this.productDetailForm.subCategory) {
            const imagePath = `sanpham/${this.productDetailForm.mainCategory}/${this.productDetailForm.subCategory}`;
            this.uploadFiles(response.id, imagePath).then(() => {
              this.createProductDetail(response.id);
            }).catch((error) => {
              console.error('❌ Error uploading files:', error);
              this.createProductDetail(response.id);
            });
          } else {
            this.createProductDetail(response.id);
          }
        },
        error: (error) => {
          console.error('❌ Error creating product:', error);
          this.showNotification('Lỗi thêm sản phẩm!', 'error');
        }
      });
    }
  }

  // Check if product detail form has meaningful data
  hasDetailData(): boolean {
    // Always return true to ensure chitietsanpham is created
    // Even with basic/default data
    return true;
  }

  // File handling methods
  async loadImagesFromDirectory(directory: string): Promise<string[]> {
    // This would typically be an API call to get available images
    // For now, return empty array - will implement file picker
    return [];
  }

  onFileSelect(event: any) {
    const files = event.target.files;
    if (files && files.length > 0) {
      // Add selected files to array
      const newFiles = Array.from(files) as File[];
      this.selectedFiles = [...this.selectedFiles, ...newFiles];
      
      // Update hinhanh field with file names
      const fileNames = this.selectedFiles.map(file => file.name);
      this.productDetailForm.hinhanh = fileNames.join(',');
      
      console.log('Selected files:', this.selectedFiles);
      this.showNotification(`Đã chọn ${newFiles.length} file`, 'success');
    }
  }

  removeFile(index: number) {
    this.selectedFiles.splice(index, 1);
    
    // Update hinhanh field
    const fileNames = this.selectedFiles.map(file => file.name);
    this.productDetailForm.hinhanh = fileNames.join(',');
    
    this.showNotification('Đã xóa file', 'success');
  }

  // Form validation
  isFormValid(): boolean {
    return !!(this.productForm.tensanpham?.trim() && this.productForm.danhmucId > 0);
  }

  // Modal methods
  closeModals() {
    this.showAddForm = false;
    this.showEditForm = false;
    this.showViewModal = false;
    this.showDeleteConfirm = false;
    this.showImageGallery = false;
    this.currentProduct = null;
    this.currentVariant = null;
  }

  resetForm() {
    this.productForm = {
      tensanpham: '',
      mota: '',
      danhmucId: 0,
      giacu: 0,
      giamoi: 0, // Không set giá mặc định
      trangthai: true
    };
    this.productDetailForm = {
      tenmau: 'Cam',
      mamau: '#FF6600',
      kichthuoc: '',
      chatlieu: '',
      trongluong: 0,
      soluong: 0,
      baohanh: 12,
      xuatxu: '',
      giacu: 0,
      giamoi: 0,
      mota: '',
      hinhanh: '',
      mainCategory: '',
      subCategory: ''
    };
    this.selectedFiles = [];
    this.currentProduct = null;
  }

  // CRUD operations
  confirmDelete() {
    if (!this.currentProduct) return;

    this.http.delete(`http://localhost:8080/api/sanpham/${this.currentProduct.id}`).subscribe({
      next: (response: any) => {
        console.log('✅ Product deleted:', response);
        this.showNotification('Xóa sản phẩm thành công!', 'success');
        this.loadProducts();
        this.closeModals();
      },
      error: (error: any) => {
        console.error('❌ Error deleting product:', error);
        this.showNotification('Lỗi xóa sản phẩm!', 'error');
      }
    });
  }

  // Form validation
  validateForm(): boolean {
    if (!this.productForm.tensanpham.trim()) {
      this.showNotification('Vui lòng nhập tên sản phẩm!', 'error');
      return false;
    }
    if (!this.productForm.mota?.trim()) {
      this.showNotification('Vui lòng nhập mô tả sản phẩm!', 'error');
      return false;
    }
    return true;
  }

  // Utility methods
  showNotification(message: string, type: 'success' | 'error') {
    // Simple alert for now - can be replaced with proper notification service
    alert(message);
  }

  // Temporarily disabled to prevent change detection loops
  /*
  getCategoryName(categoryId?: number): string {
    if (!categoryId) return 'Chưa phân loại';
    const category = this.categories.find(c => c.id === categoryId);
    return category?.tendanhmuc || 'Không xác định';
  }
  */

  // Image Gallery Methods
  openImageGallery(variant: ProductDetail) {
    this.currentVariant = variant;
    this.showImageGallery = true;
  }

  // Helper methods for image handling
  extractImagePath(product: Product): string {
    if (product.chitietsanpham && product.chitietsanpham.length > 0) {
      const hinhchinh = product.chitietsanpham[0].hinhchinh;
      if (hinhchinh) {
        // Extract directory path từ full path
        const cleanPath = hinhchinh.startsWith('/') ? hinhchinh.substring(1) : hinhchinh;
        const pathParts = cleanPath.split('/');
        pathParts.pop(); // Remove filename
        return pathParts.join('/') + '/';
      }
    }
    return 'sanpham/ghe/sofa/';
  }

  getAllProductImages(product: Product): string[] {
    const images: string[] = [];
    if (product.chitietsanpham) {
      product.chitietsanpham.forEach(variant => {
        if (variant.hinhchinh) {
          images.push(variant.hinhchinh);
        }
        if (variant.hinhphu) {
          const hinhphuArray = variant.hinhphu.split(';');
          images.push(...hinhphuArray);
        }
      });
    }
    return [...new Set(images)]; // Remove duplicates
  }

  getSecondaryImages(variant: ProductDetail): string[] {
    if (!variant.hinhphu) return [];
    return variant.hinhphu.split(';').filter(img => img.trim());
  }

  // File upload method
  async uploadFiles(productId: number, imagePath: string): Promise<void> {
    if (this.selectedFiles.length === 0) return;

    // Create FormData for file upload
    const formData = new FormData();
    this.selectedFiles.forEach((file, index) => {
      formData.append(`files`, file);
    });
    formData.append('imagePath', imagePath);
    formData.append('productId', productId.toString());

    // This would typically be an API call to upload files
    // For now, we'll simulate the upload
    console.log('📁 Uploading files to:', `assets/img/${imagePath}/`);
    console.log('📄 Files:', this.selectedFiles.map(f => f.name));
    
    // Simulate upload delay
    await new Promise(resolve => setTimeout(resolve, 1000));
    
    // Update hinhanh with uploaded file names (including path)
    const uploadedNames = this.selectedFiles.map(file => `${imagePath}/${file.name}`);
    this.productDetailForm.hinhanh = uploadedNames.join(',');
    
    console.log('✅ Files uploaded successfully to:', uploadedNames);
  }

  // Create product detail
  createProductDetail(productId: number) {
    const detailData = {
      sanphamId: productId,
      tenmau: this.productDetailForm.tenmau || 'Mac dinh',
      mamau: this.productDetailForm.mamau || '#FFFFFF',
      kichthuoc: this.productDetailForm.kichthuoc || 'Tieu chuan',
      chatlieu: this.productDetailForm.chatlieu || 'Go tu nhien',
      trongluong: this.productDetailForm.trongluong || 1.0,
      soluong: this.productDetailForm.soluong || 1,
      hinhchinh: this.productDetailForm.hinhanh || 'placeholder.jpg',
      hinhphu: '' // Set empty for now
    };
    
    console.log('📋 Creating product detail with data:', detailData);
    
    this.http.post('http://localhost:8080/api/chitietsanpham', detailData).subscribe({
      next: (detailResponse) => {
        console.log('✅ Product detail created:', detailResponse);
        this.showNotification('Thêm sản phẩm và chi tiết thành công!', 'success');
        this.loadProducts();
        this.closeModals();
      },
      error: (error) => {
        console.error('❌ Error creating product detail:', error);
        this.showNotification('Sản phẩm đã được tạo nhưng có lỗi khi thêm chi tiết!', 'error');
        this.loadProducts();
        this.closeModals();
      }
    });
  }

  // Category methods
  onMainCategoryChange() {
    this.productDetailForm.subCategory = '';
    this.updateImagePath();
  }

  getSubCategories() {
    const mainCat = this.productDetailForm.mainCategory;
    return mainCat ? this.categoryMapping[mainCat as keyof typeof this.categoryMapping] || [] : [];
  }

  updateImagePath() {
    if (this.productDetailForm.mainCategory && this.productDetailForm.subCategory) {
      // Update hinhanh field with the constructed path
      this.productDetailForm.hinhanh = `sanpham/${this.productDetailForm.mainCategory}/${this.productDetailForm.subCategory}/`;
    } else {
      this.productDetailForm.hinhanh = '';
    }
  }
}
