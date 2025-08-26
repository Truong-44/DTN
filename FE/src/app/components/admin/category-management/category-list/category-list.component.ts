import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DanhMuc } from '../../../../core/models/danhmuc.model';
import { SanPhamService } from '../../../../core/services/sanpham.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { LoadingService } from '../../../../core/services/loading.service';
import { CategoryAddComponent } from '../category-add/category-add.component';
import { CategoryEditComponent } from '../category-edit/category-edit.component';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-category-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    CategoryAddComponent,
    CategoryEditComponent,
  ],
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.scss'],
})
export class CategoryListComponent implements OnInit {
  categories: DanhMuc[] = [];
  filteredCategories: DanhMuc[] = [];
  loading = false;

  // Filters
  searchTerm = '';

  // Modals
  showAddModal = false;
  showEditModal = false;
  selectedCategory: DanhMuc | null = null;

  // Form data
  newCategory: Partial<DanhMuc> = {};
  editCategory: DanhMuc = { id: 0, tendanhmuc: '', mota: '' };

  constructor(
    private sanPhamService: SanPhamService,
    private notificationService: NotificationService,
    private loadingService: LoadingService
  ) {}

  ngOnInit() {
    this.loadCategories();
  }

  loadCategories() {
    this.loading = true;
    this.loadingService.show();

    this.sanPhamService
      .getAllDanhMuc()
      .pipe(
        catchError((error) => {
          console.error('Error loading categories:', error);
          this.notificationService.error(
            'Lỗi',
            'Không thể tải danh sách danh mục'
          );
          return of([]);
        })
      )
      .subscribe({
        next: (categories) => {
          this.categories = categories;
          this.applyFilters();
          this.loading = false;
          this.loadingService.hide();
        },
        error: (error) => {
          console.error('Error loading categories:', error);
          this.loading = false;
          this.loadingService.hide();
        },
      });
  }

  applyFilters() {
    this.filteredCategories = this.categories.filter((category) => {
      const matchesSearch =
        !this.searchTerm ||
        category.tendanhmuc
          .toLowerCase()
          .includes(this.searchTerm.toLowerCase());
      return matchesSearch;
    });
  }

  onSearchChange() {
    this.applyFilters();
  }

  openAddModal() {
    this.newCategory = {};
    this.showAddModal = true;
  }

  openEditModal(category: DanhMuc) {
    this.editCategory = { ...category };
    this.showEditModal = true;
  }

  closeModal() {
    this.showAddModal = false;
    this.showEditModal = false;
    this.newCategory = {};
    this.editCategory = { id: 0, tendanhmuc: '', mota: '' };
  }

  closeEditModal(reload = false) {
    this.closeModal();
    if (reload) {
      this.loadCategories();
    }
  }

  addCategory() {
    if (!this.newCategory.tendanhmuc?.trim()) {
      this.notificationService.error('Lỗi', 'Vui lòng nhập tên danh mục');
      return;
    }

    this.loadingService.show();

    this.sanPhamService.createDanhMuc(this.newCategory).subscribe({
      next: () => {
        this.notificationService.success('Thành công', 'Đã thêm danh mục mới');
        this.closeModal();
        this.loadCategories();
      },
      error: (error) => {
        console.error('Error creating category:', error);
        this.notificationService.error('Lỗi', 'Không thể thêm danh mục');
        this.loadingService.hide();
      },
    });
  }

  updateCategory() {
    if (!this.editCategory.tendanhmuc?.trim()) {
      this.notificationService.error('Lỗi', 'Vui lòng nhập tên danh mục');
      return;
    }

    if (!this.editCategory.id) {
      this.notificationService.error('Lỗi', 'Không tìm thấy ID danh mục');
      return;
    }

    this.loadingService.show();

    this.sanPhamService
      .updateDanhMuc(this.editCategory.id, this.editCategory)
      .subscribe({
        next: () => {
          this.notificationService.success(
            'Thành công',
            'Đã cập nhật danh mục'
          );
          this.closeModal();
          this.loadCategories();
        },
        error: (error) => {
          console.error('Error updating category:', error);
          this.notificationService.error('Lỗi', 'Không thể cập nhật danh mục');
          this.loadingService.hide();
        },
      });
  }

  deleteCategory(category: DanhMuc) {
    if (confirm(`Bạn có chắc muốn xóa danh mục "${category.tendanhmuc}"?`)) {
      this.loadingService.show();

      this.sanPhamService.deleteDanhMuc(category.id).subscribe({
        next: () => {
          this.notificationService.success('Thành công', 'Đã xóa danh mục');
          this.loadCategories();
        },
        error: (error) => {
          console.error('Error deleting category:', error);
          this.notificationService.error('Lỗi', 'Không thể xóa danh mục');
          this.loadingService.hide();
        },
      });
    }
  }

  closeAddModal(reload = false) {
    this.closeModal();
    if (reload) {
      this.loadCategories();
    }
  }

  trackByCategory(index: number, category: DanhMuc): number {
    return category.id;
  }
}
