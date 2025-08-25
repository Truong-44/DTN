import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SanPhamService } from '../../../../core/services/sanpham.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { DanhMuc } from '../../../../core/models/danhmuc.model';

@Component({
  selector: 'app-category-add',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './category-add.component.html',
  styleUrls: ['./category-add.component.scss'],
})
export class CategoryAddComponent {
  category: Partial<DanhMuc> = {
    tendanhmuc: '',
    mota: '',
  };

  @Output() added = new EventEmitter<void>();
  @Output() cancel = new EventEmitter<void>();

  constructor(
    private sanPhamService: SanPhamService,
    private notification: NotificationService
  ) {}

  addCategory() {
    if (!this.category.tendanhmuc?.trim()) {
      this.notification.error('Lỗi', 'Tên danh mục không được để trống');
      return;
    }

    this.sanPhamService.createDanhMuc(this.category).subscribe({
      next: () => {
        this.notification.success('Thành công', 'Đã thêm danh mục');
        this.added.emit();
        this.resetForm();
      },
      error: (error) => {
        console.error('Error adding category:', error);
        this.notification.error('Lỗi', 'Không thêm được danh mục');
      },
    });
  }

  resetForm() {
    this.category = {
      tendanhmuc: '',
      mota: '',
    };
  }

  onCancel() {
    this.cancel.emit();
  }
}
