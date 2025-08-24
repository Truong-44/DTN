import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DanhMucService } from '../../../../core/services/danhmuc.service';
import { NotificationService } from '../../../../core/services/notification.service';

@Component({
  selector: 'app-category-add',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './category-add.component.html',
  styleUrls: ['./category-add.component.scss'],
})
export class CategoryAddComponent {
  tendanhmuc = '';
  mota = '';

  @Output() added = new EventEmitter<void>();
  @Output() cancel = new EventEmitter<void>();

  constructor(
    private danhMucService: DanhMucService,
    private notification: NotificationService
  ) {}

  addCategory() {
    if (!this.tendanhmuc.trim()) {
      this.notification.error('Lỗi', 'Tên danh mục không được để trống');
      return;
    }
    this.danhMucService
      .create({ tendanhmuc: this.tendanhmuc, mota: this.mota })
      .subscribe({
        next: () => {
          this.notification.success('Thành công', 'Đã thêm danh mục');
          this.added.emit();
          this.resetForm();
        },
        error: () => {
          this.notification.error('Lỗi', 'Không thêm được danh mục');
        },
      });
  }

  resetForm() {
    this.tendanhmuc = '';
    this.mota = '';
  }
}
