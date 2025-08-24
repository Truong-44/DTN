import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DanhMuc } from '../../../../core/models/danhmuc.model';
import { DanhMucService } from '../../../../core/services/danhmuc.service';
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
    private danhMucService: DanhMucService,
    private notification: NotificationService
  ) {}

  save() {
    this.danhMucService.update(this.category.id, this.category).subscribe({
      next: () => {
        this.notification.success('Thành công', 'Đã cập nhật danh mục');
        this.updated.emit();
      },
      error: () => {
        this.notification.error('Lỗi', 'Không cập nhật được danh mục');
      },
    });
  }
}
