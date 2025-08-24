import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SanPhamService } from '../../../../core/services/sanpham.service';
import { DanhMucService } from '../../../../core/services/danhmuc.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { SanPham } from '../../../../core/models/sanpham.model';
import { DanhMuc } from '../../../../core/models/danhmuc.model';
import { ChiTietSanPham } from '../../../../core/models/chitietsanpham.model';

@Component({
  selector: 'app-product-edit',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.scss'],
})
export class ProductEditComponent implements OnInit {
  @Input() product!: SanPham;
  @Output() updated = new EventEmitter<void>();
  @Output() cancel = new EventEmitter<void>();
  @Input() detail: ChiTietSanPham | null = null;

  tensanpham = '';
  giacu: number | null = null;
  giamoi: number | null = null;
  mota = '';
  danhmucId: number | null = null;
  categories: DanhMuc[] = [];
  loading = false;

  constructor(
    private sanPhamService: SanPhamService,
    private danhMucService: DanhMucService,
    private notification: NotificationService
  ) {}

  ngOnInit() {
    this.danhMucService.getAll().subscribe({
      next: (data: any) => {
        this.categories = Array.isArray(data)
          ? data
          : data.data || data.items || [];
      },
      error: () => {
        this.categories = [];
      },
    });

    if (this.product) {
      this.tensanpham = this.product.tensanpham;
      this.giacu = this.product.giacu ?? null;
      this.giamoi = this.product.giamoi ?? null;
      this.mota = this.product.mota ?? '';
      this.danhmucId = this.product.danhmucId || this.product.danhmucid || null;
    }
  }

  save() {
    if (
      !this.tensanpham.trim() ||
      (!this.giacu && !this.giamoi) ||
      !this.danhmucId
    ) {
      this.notification.error('Lỗi', 'Vui lòng nhập đầy đủ thông tin');
      return;
    }
    this.loading = true;
    this.sanPhamService
      .updateSanPham(this.product.id, {
        tensanpham: this.tensanpham,
        giacu: this.giacu ?? undefined,
        giamoi: this.giamoi ?? undefined,
        mota: this.mota,
        danhmucid: this.danhmucId,
      })
      .subscribe({
        next: () => {
          this.notification.success('Thành công', 'Đã cập nhật sản phẩm');
          this.updated.emit();
          this.loading = false;
        },
        error: () => {
          this.notification.error('Lỗi', 'Không cập nhật được sản phẩm');
          this.loading = false;
        },
      });
  }

  updateDetailImage() {
    if (!this.detail) return;
    this.sanPhamService
      .updateChiTietSanPham(this.detail.id, {
        hinhchinh: this.detail.hinhchinh,
        hinhphu: this.detail.hinhphu,
        // Có thể thêm các trường khác nếu cần
      })
      .subscribe({
        next: () => {
          this.notification.success('Thành công', 'Đã cập nhật hình ảnh');
          this.updated.emit();
        },
        error: () => {
          this.notification.error('Lỗi', 'Không cập nhật được hình ảnh');
        },
      });
  }

  get mainImgUrl(): string | null {
    if (!this.detail?.hinhchinh) return null;
    return this.detail.hinhchinh.startsWith('assets/img')
      ? this.detail.hinhchinh
      : `assets/img${this.detail.hinhchinh}`;
  }

  get subImgUrls(): string[] {
    if (!this.detail?.hinhphu) return [];
    return this.detail.hinhphu
      .split(';')
      .map((s) => s.trim())
      .filter((s) => !!s)
      .map((s) => (s.startsWith('assets/img') ? s : 'assets/img' + s));
  }
}
