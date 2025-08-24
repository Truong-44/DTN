import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin, of } from 'rxjs';
import { Router } from '@angular/router';
import { SanPhamService } from '../../../../core/services/sanpham.service';
import { ChiTietSanPham } from '../../../../core/models/chitietsanpham.model';
import { SanPham } from '../../../../core/models/sanpham.model';
import { DanhMuc } from '../../../../core/models/danhmuc.model';
import { NotificationService } from '../../../../core/services/notification.service';
import { LoadingService } from '../../../../core/services/loading.service';
import { ProductAddComponent } from '../product-add/product-add.component';
import { ProductEditComponent } from '../product-edit/product-edit.component';
import { catchError } from 'rxjs/operators';
@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ProductAddComponent,
    ProductEditComponent,
  ],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss'],
})
export class ProductListComponent implements OnInit {
  products: (SanPham & { chitietsanpham: ChiTietSanPham[] })[] = [];
  filteredProducts: (SanPham & { chitietsanpham: ChiTietSanPham[] })[] = [];
  categories: DanhMuc[] = [];
  loading = false;

  // Filters
  searchTerm = '';
  categoryFilter = '';
  statusFilter = '';

  // Pagination
  currentPage = 1;
  pageSize = 10;
  totalPages = 0;

  // Modals
  showAddModal = false;
  showEditModal = false;
  showDeleteModal = false;
  selectedProduct: SanPham | null = null;

  constructor(
    private sanPhamService: SanPhamService,
    private notificationService: NotificationService,
    private loadingService: LoadingService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.loading = true;
    this.loadingService.show();

    forkJoin({
      products: this.sanPhamService.getAllSanPham().pipe(
        catchError((error) => {
          console.error('Error loading products:', error);
          return of([]);
        })
      ),
      chiTietSanPham: this.sanPhamService.getAllChiTietSanPham().pipe(
        catchError((error) => {
          console.error('Error loading product details:', error);
          return of([]);
        })
      ),
      categories: this.sanPhamService.getAllDanhMuc().pipe(
        catchError((error) => {
          console.error('Error loading categories:', error);
          return of([]);
        })
      ),
    }).subscribe({
      next: ({ products, chiTietSanPham, categories }) => {
        // Combine products with their details
        this.products = products.map((product) => ({
          ...product,
          chitietsanpham: chiTietSanPham.filter(
            (ct) => ct.sanphamId === product.id
          ),
        }));

        this.categories = categories;
        this.applyFilters();
        this.loading = false;
        this.loadingService.hide();
      },
      error: (error) => {
        console.error('Error loading data:', error);
        this.notificationService.error('Lỗi', 'Không thể tải dữ liệu sản phẩm');
        this.loading = false;
        this.loadingService.hide();
      },
    });
  }

  applyFilters() {
    this.filteredProducts = this.products.filter((product) => {
      const matchesSearch =
        !this.searchTerm ||
        product.tensanpham
          .toLowerCase()
          .includes(this.searchTerm.toLowerCase()) ||
        product.tenloai?.toLowerCase().includes(this.searchTerm.toLowerCase());

      const matchesCategory =
        !this.categoryFilter ||
        product.danhmucId?.toString() === this.categoryFilter;

      const matchesStatus =
        !this.statusFilter ||
        (this.statusFilter === 'active' && product.trangthai) ||
        (this.statusFilter === 'inactive' && !product.trangthai);

      return matchesSearch && matchesCategory && matchesStatus;
    });

    this.totalPages = Math.ceil(this.filteredProducts.length / this.pageSize);
    this.currentPage = 1;
  }

  onSearchChange() {
    this.applyFilters();
  }

  onCategoryFilterChange() {
    this.applyFilters();
  }

  onStatusFilterChange() {
    this.applyFilters();
  }

  getPaginatedProducts() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    return this.filteredProducts.slice(startIndex, endIndex);
  }

  goToPage(page: number) {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }

  addProduct() {
    this.router.navigate(['/admin/product-management/add']);
  }

  loadProducts() {
    this.loadData();
  }

  addNewProduct() {
    this.router.navigate(['/admin/product-management/add']);
  }

  // Additional filter properties
  filterLoai = '';
  loaiList: string[] = [];

  // Modal management
  showSubImagesModal = false;
  subImages: string[] = [];
  editModalOpen = false;
  addModalOpen = false;
  editSanPham: SanPham | null = null;
  editChiTiet: ChiTietSanPham | null = null;

  getImageUrl(imagePath: string): string {
    if (!imagePath) return '/assets/img/placeholder/product-placeholder.jpg';
    return imagePath.startsWith('http')
      ? imagePath
      : `/assets/img/sanpham/${imagePath}`;
  }

  getSubImages(detail: ChiTietSanPham): string[] {
    if (!detail.hinhphu) return [];
    return detail.hinhphu.split(';').filter((img) => img.trim());
  }

  openSubImagesModal(images: string[]) {
    this.subImages = images;
    this.showSubImagesModal = true;
  }

  closeSubImagesModal() {
    this.showSubImagesModal = false;
    this.subImages = [];
  }

  onEdit(product: SanPham, detail: ChiTietSanPham | null) {
    this.editSanPham = product;
    this.editChiTiet = detail;
    this.editModalOpen = true;
  }

  onDelete(product: SanPham, detail: ChiTietSanPham | null) {
    if (detail) {
      // Delete product detail
      if (confirm(`Bạn có chắc muốn xóa chi tiết sản phẩm này?`)) {
        this.sanPhamService.deleteChiTietSanPham(detail.id).subscribe({
          next: () => {
            this.notificationService.success(
              'Thành công',
              'Đã xóa chi tiết sản phẩm'
            );
            this.loadData();
          },
          error: (error) => {
            console.error('Error deleting product detail:', error);
            this.notificationService.error(
              'Lỗi',
              'Không thể xóa chi tiết sản phẩm'
            );
          },
        });
      }
    } else {
      // Delete entire product
      this.deleteProduct(product);
    }
  }

  closeEditModal(reload: boolean = false) {
    this.editModalOpen = false;
    this.editSanPham = null;
    this.editChiTiet = null;
    if (reload) {
      this.loadData();
    }
  }

  closeAddModal(reload: boolean = false) {
    this.addModalOpen = false;
    if (reload) {
      this.loadData();
    }
  }

  editProduct(product: SanPham) {
    this.router.navigate(['/admin/product-management/edit', product.id]);
  }

  viewProduct(product: SanPham) {
    this.router.navigate(['/admin/product-management/detail', product.id]);
  }

  deleteProduct(product: SanPham) {
    if (confirm(`Bạn có chắc muốn xóa sản phẩm "${product.tensanpham}"?`)) {
      this.loadingService.show();

      this.sanPhamService.deleteSanPham(product.id).subscribe({
        next: () => {
          this.notificationService.success('Thành công', 'Đã xóa sản phẩm');
          this.loadData(); // Reload data
        },
        error: (error) => {
          console.error('Error deleting product:', error);
          this.notificationService.error('Lỗi', 'Không thể xóa sản phẩm');
          this.loadingService.hide();
        },
      });
    }
  }

  toggleProductStatus(product: SanPham) {
    const action = product.trangthai ? 'ẩn' : 'hiển thị';
    if (
      confirm(`Bạn có chắc muốn ${action} sản phẩm "${product.tensanpham}"?`)
    ) {
      this.loadingService.show();

      const updateData = { trangthai: !product.trangthai };

      this.sanPhamService.updateSanPham(product.id, updateData).subscribe({
        next: () => {
          product.trangthai = !product.trangthai;
          this.notificationService.success(
            'Thành công',
            `Đã ${action} sản phẩm`
          );
          this.loadingService.hide();
        },
        error: (error) => {
          console.error('Error updating product status:', error);
          this.notificationService.error('Lỗi', `Không thể ${action} sản phẩm`);
          this.loadingService.hide();
        },
      });
    }
  }

  getCategoryName(categoryId: number): string {
    const category = this.categories.find((c) => c.id === categoryId);
    return category?.tendanhmuc || 'N/A';
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(amount);
  }

  formatDate(date: string | Date): string {
    return new Date(date).toLocaleDateString('vi-VN');
  }

  getMainImage(product: SanPham): string {
    return (
      product.chitietsanpham?.[0]?.hinhchinh ||
      '/assets/img/placeholder/product-placeholder.jpg'
    );
  }

  getTotalStock(product: SanPham): number {
    return (
      product.chitietsanpham?.reduce(
        (total, ct) => total + (ct.soluong || 0),
        0
      ) || 0
    );
  }
}
