import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DanhMuc } from '../../../../core/models/danhmuc.model';
import { SanPhamService } from '../../../../core/services/sanpham.service';
import { NotificationService } from '../../../../core/services/notification.service';

@Component({
  selector: 'app-category-edit',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './category-edit.component.html',
  styleUrls: ['./category-edit.component.scss'],
})
export class CategoryEditComponent {
  @Input() category!: DanhMuc;
  @Output() updated = new EventEmitter<void>();
  @Output() cancel = new EventEmitter<void>();

  constructor(
    private sanPhamService: SanPhamService,
    private notification: NotificationService
  ) {}

  save() {
    if (!this.category.tendanhmuc?.trim()) {
      this.notification.error('Lỗi', 'Tên danh mục không được để trống');
      return;
    }

    this.sanPhamService
      .updateDanhMuc(this.category.id, this.category)
      .subscribe({
        next: () => {
          this.notification.success('Thành công', 'Đã cập nhật danh mục');
          this.updated.emit();
        },
        error: (error) => {
          console.error('Error updating category:', error);
          this.notification.error('Lỗi', 'Không cập nhật được danh mục');
        },
      });
  }

  onCancel() {
    this.cancel.emit();
  }
}
