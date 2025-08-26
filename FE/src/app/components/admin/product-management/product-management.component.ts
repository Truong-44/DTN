import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

interface SanPham {
  id: number;
  tensanpham: string;
  mota?: string;
  trangthai: boolean;
  danhmucId?: number;
  tenloai?: string;
  giacu?: number;
  giamoi?: number;
  ngaytao?: string;
  chitietsanpham?: ChiTietSanPham[];
}

interface ChiTietSanPham {
  id: number;
  sanphamId: number;
  tenmau?: string;
  mamau?: string;
  chatlieu?: string;
  kichthuoc?: string;
  trongluong?: number;
  soluong: number;
  hinhchinh?: string;
  hinhphu?: string;
}

interface DanhMuc {
  id: number;
  tendanhmuc: string;
  trangthai: boolean;
}

@Component({
  selector: 'app-product-management',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-management.component.html',
  styleUrls: ['./product-management.component.scss']
})
export class ProductManagementComponent implements OnInit {
  products: SanPham[] = [];
  filteredProducts: SanPham[] = [];
  categories: DanhMuc[] = [];
  isLoading = true;

  // Filters
  searchTerm = '';
  statusFilter = '';
  categoryFilter = '';

  // Statistics
  totalProducts = 0;
  activeProducts = 0;
  inactiveProducts = 0;
  totalStock = 0;
  totalValue = 0;

  // Modals
  showAddForm = false;
  showEditForm = false;
  showViewModal = false;
  showDeleteConfirm = false;
  currentProduct: SanPham | null = null;

  // Form data
  productForm = {
    tensanpham: '',
    mota: '',
    danhmucId: 0,
    tenloai: '',
    giacu: 0,
    giamoi: 0,
    trangthai: true
  };

  constructor(
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.loadInitialData();
  }

  loadInitialData(): void {
    this.isLoading = true;
    
    Promise.all([
      this.loadProducts(),
      this.loadCategories()
    ]).then(() => {
      this.calculateStatistics();
      this.applyFilters();
      this.isLoading = false;
    }).catch(() => {
      this.isLoading = false;
    });
  }

  loadProducts(): Promise<void> {
    return new Promise((resolve, reject) => {
      this.http.get<any>('http://localhost:8080/api/sanpham')
        .subscribe({
          next: (response) => {
            this.products = response.data || [];
            resolve();
          },
          error: (error: any) => {
            console.error('Error loading products:', error);
            // Use mock data on error
            this.products = this.getMockProducts();
            resolve();
          }
        });
    });
  }

  loadCategories(): Promise<void> {
    return new Promise((resolve) => {
      this.http.get<any>('http://localhost:8080/api/danhmuc')
        .subscribe({
          next: (response) => {
            this.categories = response.data || [];
            resolve();
          },
          error: () => {
            this.categories = this.getMockCategories();
            resolve();
          }
        });
    });
  }

  getMockProducts(): SanPham[] {
    return [
      {
        id: 1,
        tensanpham: 'Bàn ăn gỗ cao cấp Aurora',
        mota: 'Bàn ăn 6 chỗ làm từ gỗ tự nhiên cao cấp',
        trangthai: true,
        danhmucId: 1,
        tenloai: 'ban',
        giacu: 8000000,
        giamoi: 7200000,
        ngaytao: '2025-08-26T10:00:00',
        chitietsanpham: [
          {
            id: 1,
            sanphamId: 1,
            tenmau: 'Nâu gỗ tự nhiên',
            mamau: '#8B4513',
            chatlieu: 'Gỗ sồi',
            kichthuoc: '180x90x75cm',
            trongluong: 45.5,
            soluong: 5,
            hinhchinh: 'sanpham/ban/an/aurora_01.jpg'
          }
        ]
      },
      {
        id: 2,
        tensanpham: 'Ghế sofa da thật 3 chỗ Premium',
        mota: 'Ghế sofa da thật cao cấp phong cách hiện đại',
        trangthai: true,
        danhmucId: 2,
        tenloai: 'ghe',
        giacu: 15000000,
        giamoi: 13500000,
        ngaytao: '2025-08-25T14:30:00',
        chitietsanpham: [
          {
            id: 2,
            sanphamId: 2,
            tenmau: 'Đen sang trọng',
            mamau: '#000000',
            chatlieu: 'Da thật',
            kichthuoc: '220x95x85cm',
            trongluong: 65.0,
            soluong: 3,
            hinhchinh: 'sanpham/ghe/sofa/premium_01.jpg'
          }
        ]
      }
    ];
  }

  getMockCategories(): DanhMuc[] {
    return [
      { id: 1, tendanhmuc: 'Bàn', trangthai: true },
      { id: 2, tendanhmuc: 'Ghế', trangthai: true },
      { id: 3, tendanhmuc: 'Giường', trangthai: true },
      { id: 4, tendanhmuc: 'Tủ', trangthai: true }
    ];
  }

  calculateStatistics(): void {
    this.totalProducts = this.products.length;
    this.activeProducts = this.products.filter(p => p.trangthai).length;
    this.inactiveProducts = this.totalProducts - this.activeProducts;
    
    this.totalStock = this.products.reduce((sum, product) => {
      if (product.chitietsanpham) {
        return sum + product.chitietsanpham.reduce((detailSum, detail) => 
          detailSum + (detail.soluong || 0), 0);
      }
      return sum;
    }, 0);

    this.totalValue = this.products.reduce((sum, product) => {
      const price = product.giamoi || product.giacu || 0;
      if (product.chitietsanpham) {
        const stock = product.chitietsanpham.reduce((detailSum, detail) => 
          detailSum + (detail.soluong || 0), 0);
        return sum + (price * stock);
      }
      return sum;
    }, 0);
  }

  applyFilters(): void {
    this.filteredProducts = this.products.filter(product => {
      const matchesSearch = !this.searchTerm || 
        product.tensanpham.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        product.mota?.toLowerCase().includes(this.searchTerm.toLowerCase());

      const matchesStatus = !this.statusFilter || 
        product.trangthai.toString() === this.statusFilter;

      const matchesCategory = !this.categoryFilter || 
        product.danhmucId?.toString() === this.categoryFilter;

      return matchesSearch && matchesStatus && matchesCategory;
    });
  }

  onSearchChange(): void {
    this.applyFilters();
  }

  onStatusFilterChange(): void {
    this.applyFilters();
  }

  onCategoryFilterChange(): void {
    this.applyFilters();
  }

  getProductImage(product: SanPham): string {
    if (product.chitietsanpham && product.chitietsanpham.length > 0) {
      const hinhchinh = product.chitietsanpham[0].hinhchinh;
      if (hinhchinh) {
        const cleanPath = hinhchinh.startsWith('/') ? hinhchinh.substring(1) : hinhchinh;
        return `assets/img/${cleanPath}`;
      }
    }
    return 'assets/img/placeholder/product.jpg';
  }

  onImageError(event: any): void {
    event.target.src = 'assets/img/placeholder/product.jpg';
  }

  openAddForm(): void {
    this.resetForm();
    this.showAddForm = true;
  }

  openEditForm(product: SanPham): void {
    this.currentProduct = product;
    this.productForm = {
      tensanpham: product.tensanpham,
      mota: product.mota || '',
      danhmucId: product.danhmucId || 0,
      tenloai: product.tenloai || '',
      giacu: product.giacu || 0,
      giamoi: product.giamoi || 0,
      trangthai: product.trangthai
    };
    this.showEditForm = true;
  }

  openViewModal(product: SanPham): void {
    this.currentProduct = product;
    this.showViewModal = true;
  }

  openDeleteConfirm(product: SanPham): void {
    this.currentProduct = product;
    this.showDeleteConfirm = true;
  }

  saveProduct(): void {
    if (!this.validateForm()) return;

    const productData = {
      tensanpham: this.productForm.tensanpham,
      mota: this.productForm.mota,
      danhmucId: this.productForm.danhmucId,
      tenloai: this.productForm.tenloai,
      giacu: this.productForm.giacu,
      giamoi: this.productForm.giamoi,
      trangthai: this.productForm.trangthai
    };

    if (this.showEditForm && this.currentProduct) {
      this.updateProduct(this.currentProduct.id, productData);
    } else {
      this.createProduct(productData);
    }
  }

  createProduct(productData: any): void {
    this.http.post('http://localhost:8080/api/sanpham', productData)
      .subscribe({
        next: () => {
          this.showNotification('Thêm sản phẩm thành công!', 'success');
          this.loadInitialData();
          this.closeModals();
        },
        error: (error: any) => {
          console.error('Error creating product:', error);
          this.showNotification('Có lỗi khi thêm sản phẩm!', 'error');
        }
      });
  }

  updateProduct(id: number, productData: any): void {
    this.http.put(`http://localhost:8080/api/sanpham/${id}`, productData)
      .subscribe({
        next: () => {
          this.showNotification('Cập nhật sản phẩm thành công!', 'success');
          this.loadInitialData();
          this.closeModals();
        },
        error: (error: any) => {
          console.error('Error updating product:', error);
          this.showNotification('Có lỗi khi cập nhật sản phẩm!', 'error');
        }
      });
  }

  deleteProduct(): void {
    if (!this.currentProduct) return;

    this.http.delete(`http://localhost:8080/api/sanpham/${this.currentProduct.id}`)
      .subscribe({
        next: () => {
          this.showNotification('Xóa sản phẩm thành công!', 'success');
          this.loadInitialData();
          this.closeModals();
        },
        error: (error: any) => {
          console.error('Error deleting product:', error);
          this.showNotification('Có lỗi khi xóa sản phẩm!', 'error');
        }
      });
  }

  toggleProductStatus(product: SanPham): void {
    const newStatus = !product.trangthai;
    
    this.http.patch(`http://localhost:8080/api/sanpham/${product.id}/status`, { trangthai: newStatus })
      .subscribe({
        next: () => {
          product.trangthai = newStatus;
          this.calculateStatistics();
          this.applyFilters();
          this.showNotification(`${newStatus ? 'Hiển thị' : 'Ẩn'} sản phẩm thành công!`, 'success');
        },
        error: (error: any) => {
          console.error('Error updating product status:', error);
          this.showNotification('Có lỗi khi cập nhật trạng thái!', 'error');
        }
      });
  }

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

  resetForm(): void {
    this.productForm = {
      tensanpham: '',
      mota: '',
      danhmucId: 0,
      tenloai: '',
      giacu: 0,
      giamoi: 0,
      trangthai: true
    };
    this.currentProduct = null;
  }

  closeModals(): void {
    this.showAddForm = false;
    this.showEditForm = false;
    this.showViewModal = false;
    this.showDeleteConfirm = false;
    this.currentProduct = null;
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND'
    }).format(amount);
  }

  formatNumber(num: number): string {
    return new Intl.NumberFormat('vi-VN').format(num);
  }

  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleDateString('vi-VN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  getCategoryName(categoryId?: number): string {
    if (!categoryId) return 'Chưa phân loại';
    const category = this.categories.find(c => c.id === categoryId);
    return category?.tendanhmuc || 'Không xác định';
  }

  getStatusClass(status: boolean): string {
    return status ? 'status-active' : 'status-inactive';
  }

  getStatusLabel(status: boolean): string {
    return status ? 'Hiển thị' : 'Ẩn';
  }

  refreshData(): void {
    this.loadInitialData();
  }

  exportProducts(): void {
    this.showNotification('Tính năng xuất dữ liệu đang được phát triển!', 'info');
  }

  trackByProductId(index: number, product: SanPham): number {
    return product.id;
  }

  private showNotification(message: string, type: 'success' | 'error' | 'info'): void {
    alert(message);
  }
}
