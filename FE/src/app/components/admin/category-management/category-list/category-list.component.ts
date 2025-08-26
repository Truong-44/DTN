import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

interface DanhMuc {
  id: number;
  tendanhmuc: string;
  mota?: string;
  trangthai: boolean;
  ngaytao?: string;
  ngaycapnhat?: string;
}

@Component({
  selector: 'app-category-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.scss']
})
export class CategoryListComponent implements OnInit {
  categories: DanhMuc[] = [];
  filteredCategories: DanhMuc[] = [];
  isLoading = true;

  // Filters
  searchTerm = '';
  statusFilter = '';

  // Statistics
  totalCategories = 0;
  activeCategories = 0;
  inactiveCategories = 0;

  // Modals
  showAddForm = false;
  showEditForm = false;
  showViewModal = false;
  showDeleteConfirm = false;
  currentCategory: DanhMuc | null = null;

  // Form data
  categoryForm = {
    tendanhmuc: '',
    mota: '',
    trangthai: true
  };

  constructor(
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.isLoading = true;
    
    this.http.get<any>('http://localhost:8080/api/danhmuc')
      .subscribe({
        next: (response) => {
          this.categories = response.data || [];
          this.calculateStatistics();
          this.applyFilters();
          this.isLoading = false;
        },
        error: (error: any) => {
          console.error('Error loading categories:', error);
          // Use mock data on error
          this.categories = this.getMockCategories();
          this.calculateStatistics();
          this.applyFilters();
          this.isLoading = false;
        }
      });
  }

  getMockCategories(): DanhMuc[] {
    return [
      {
        id: 1,
        tendanhmuc: 'Bàn',
        mota: 'Các loại bàn: bàn ăn, bàn làm việc, bàn trà',
        trangthai: true,
        ngaytao: '2025-08-20T10:00:00',
        ngaycapnhat: '2025-08-26T14:30:00'
      },
      {
        id: 2,
        tendanhmuc: 'Ghế',
        mota: 'Các loại ghế: ghế ăn, ghế sofa, ghế làm việc',
        trangthai: true,
        ngaytao: '2025-08-20T10:15:00',
        ngaycapnhat: '2025-08-25T16:20:00'
      },
      {
        id: 3,
        tendanhmuc: 'Giường',
        mota: 'Các loại giường ngủ và phụ kiện',
        trangthai: true,
        ngaytao: '2025-08-20T10:30:00'
      },
      {
        id: 4,
        tendanhmuc: 'Tủ',
        mota: 'Tủ quần áo, tủ bếp, tủ sách',
        trangthai: false,
        ngaytao: '2025-08-20T10:45:00',
        ngaycapnhat: '2025-08-24T09:15:00'
      }
    ];
  }

  calculateStatistics(): void {
    this.totalCategories = this.categories.length;
    this.activeCategories = this.categories.filter(c => c.trangthai).length;
    this.inactiveCategories = this.totalCategories - this.activeCategories;
  }

  applyFilters(): void {
    this.filteredCategories = this.categories.filter(category => {
      const matchesSearch = !this.searchTerm || 
        category.tendanhmuc.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        category.mota?.toLowerCase().includes(this.searchTerm.toLowerCase());

      const matchesStatus = !this.statusFilter || 
        category.trangthai.toString() === this.statusFilter;

      return matchesSearch && matchesStatus;
    });
  }

  onSearchChange(): void {
    this.applyFilters();
  }

  onStatusFilterChange(): void {
    this.applyFilters();
  }

  openAddForm(): void {
    this.resetForm();
    this.showAddForm = true;
  }

  openEditForm(category: DanhMuc): void {
    this.currentCategory = category;
    this.categoryForm = {
      tendanhmuc: category.tendanhmuc,
      mota: category.mota || '',
      trangthai: category.trangthai
    };
    this.showEditForm = true;
  }

  openViewModal(category: DanhMuc): void {
    this.currentCategory = category;
    this.showViewModal = true;
  }

  openDeleteConfirm(category: DanhMuc): void {
    this.currentCategory = category;
    this.showDeleteConfirm = true;
  }

  saveCategory(): void {
    if (!this.validateForm()) return;

    const categoryData = {
      tendanhmuc: this.categoryForm.tendanhmuc,
      mota: this.categoryForm.mota,
      trangthai: this.categoryForm.trangthai
    };

    if (this.showEditForm && this.currentCategory) {
      this.updateCategory(this.currentCategory.id, categoryData);
    } else {
      this.createCategory(categoryData);
    }
  }

  createCategory(categoryData: any): void {
    this.http.post('http://localhost:8080/api/danhmuc', categoryData)
      .subscribe({
        next: () => {
          this.showNotification('Thêm danh mục thành công!', 'success');
          this.loadCategories();
          this.closeModals();
        },
        error: (error: any) => {
          console.error('Error creating category:', error);
          this.showNotification('Có lỗi khi thêm danh mục!', 'error');
        }
      });
  }

  updateCategory(id: number, categoryData: any): void {
    this.http.put(`http://localhost:8080/api/danhmuc/${id}`, categoryData)
      .subscribe({
        next: () => {
          this.showNotification('Cập nhật danh mục thành công!', 'success');
          this.loadCategories();
          this.closeModals();
        },
        error: (error: any) => {
          console.error('Error updating category:', error);
          this.showNotification('Có lỗi khi cập nhật danh mục!', 'error');
        }
      });
  }

  deleteCategory(): void {
    if (!this.currentCategory) return;

    this.http.delete(`http://localhost:8080/api/danhmuc/${this.currentCategory.id}`)
      .subscribe({
        next: () => {
          this.showNotification('Xóa danh mục thành công!', 'success');
          this.loadCategories();
          this.closeModals();
        },
        error: (error: any) => {
          console.error('Error deleting category:', error);
          this.showNotification('Có lỗi khi xóa danh mục!', 'error');
        }
      });
  }

  toggleCategoryStatus(category: DanhMuc): void {
    const newStatus = !category.trangthai;
    
    this.http.patch(`http://localhost:8080/api/danhmuc/${category.id}/status`, { trangthai: newStatus })
      .subscribe({
        next: () => {
          category.trangthai = newStatus;
          this.calculateStatistics();
          this.applyFilters();
          this.showNotification(`${newStatus ? 'Kích hoạt' : 'Vô hiệu hóa'} danh mục thành công!`, 'success');
        },
        error: (error: any) => {
          console.error('Error updating category status:', error);
          this.showNotification('Có lỗi khi cập nhật trạng thái!', 'error');
        }
      });
  }

  validateForm(): boolean {
    if (!this.categoryForm.tendanhmuc.trim()) {
      this.showNotification('Vui lòng nhập tên danh mục!', 'error');
      return false;
    }
    return true;
  }

  resetForm(): void {
    this.categoryForm = {
      tendanhmuc: '',
      mota: '',
      trangthai: true
    };
    this.currentCategory = null;
  }

  closeModals(): void {
    this.showAddForm = false;
    this.showEditForm = false;
    this.showViewModal = false;
    this.showDeleteConfirm = false;
    this.currentCategory = null;
  }

  formatDate(dateString?: string): string {
    if (!dateString) return 'Chưa có';
    return new Date(dateString).toLocaleDateString('vi-VN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  getStatusClass(status: boolean): string {
    return status ? 'status-active' : 'status-inactive';
  }

  getStatusLabel(status: boolean): string {
    return status ? 'Hoạt động' : 'Vô hiệu hóa';
  }

  refreshData(): void {
    this.loadCategories();
  }

  exportCategories(): void {
    this.showNotification('Tính năng xuất dữ liệu đang được phát triển!', 'info');
  }

  getActivePercentage(): number {
    if (this.totalCategories === 0) return 0;
    return Math.round((this.activeCategories / this.totalCategories) * 100);
  }

  trackByCategoryId(index: number, category: DanhMuc): number {
    return category.id;
  }

  private showNotification(message: string, type: 'success' | 'error' | 'info'): void {
    alert(message);
  }
}
