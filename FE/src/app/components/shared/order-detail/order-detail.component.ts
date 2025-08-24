import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { DonHangService } from '../../../core/services/donhang.service';
import { NotificationService } from '../../../core/services/notification.service';
import { DonHang } from '../../../core/models/donhang.model';
import { ImageService } from '../../../core/services/image.service';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.scss'],
  imports: [CommonModule],
  standalone: true,
})
export class OrderDetailComponent implements OnInit {
  @Input() order: DonHang | null = null;
  @Input() isModal = false;
  @Output() closeModal = new EventEmitter<void>();

  loading = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private donHangService: DonHangService,
    private notificationService: NotificationService,
    private imageService: ImageService
  ) {}

  ngOnInit() {
    // Nếu không phải modal và không có order input, load từ API qua route param
    if (!this.isModal && !this.order) {
      const orderId = Number(this.route.snapshot.paramMap.get('id'));
      if (orderId) {
        this.loadOrderDetail(orderId);
      }
    }
  }

  loadOrderDetail(orderId: number) {
    this.loading = true;
    this.donHangService.getDonHangById(orderId).subscribe({
      next: (response: any) => {
        this.order = response.data || response;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error loading order detail:', err);
        this.notificationService.error(
          'Lỗi',
          'Không thể tải chi tiết đơn hàng. Vui lòng thử lại.'
        );
        this.order = null; // Xóa đơn hàng cũ nếu có lỗi
        this.loading = false;
      },
    });
  }

  goBack() {
    if (this.isModal) {
      this.closeModal.emit();
    } else {
      this.router.navigate(['/order-list']);
    }
  }

  onClose() {
    this.closeModal.emit();
  }

  getImageUrl(imageName: string | undefined): string {
    if (!imageName) {
      return 'assets/img/placeholder/product-placeholder.png';
    }
    return this.imageService.getImageUrl(imageName);
  }

  getPhoneNumber(order: DonHang): string {
    const orderWithPhone = order as any;
    return (
      orderWithPhone.sodienthoai || order.khachhang?.taikhoan?.sodienthoai || ''
    );
  }

  getGhiChu(order: DonHang): string {
    const orderWithNote = order as any;
    return orderWithNote.ghichu || '';
  }

  getPaymentMethodLabel(method: string | undefined): string {
    switch (method) {
      case 'COD':
        return 'Thanh toán khi nhận hàng';
      case 'BANK':
        return 'Chuyển khoản ngân hàng';
      case 'MOMO':
        return 'Ví MoMo';
      case 'VNPAY':
        return 'VNPay';
      default:
        return method || '';
    }
  }

  formatDate(dateString: string | Date): string {
    const date = new Date(dateString);
    return date.toLocaleDateString('vi-VN');
  }

  formatCurrency(amount: number | undefined): string {
    return new Intl.NumberFormat('vi-VN').format(amount || 0) + 'đ';
  }

  getStatusLabel(status: string): string {
    const statusMap: { [key: string]: string } = {
      CHO_XAC_NHAN: 'Chờ xác nhận',
      DA_XAC_NHAN: 'Đã xác nhận',
      DANG_GIAO: 'Đang giao',
      DA_GIAO: 'Đã giao',
      DA_HUY: 'Đã hủy',
    };
    return statusMap[status] || status;
  }

  getTotalOrderValue(): number {
    if (!this.order?.chitietdonhang) return 0;
    return this.order.chitietdonhang.reduce(
      (total, item) => total + item.dongia * item.soluong,
      0
    );
  }

  getItemCount(): number {
    if (!this.order?.chitietdonhang) return 0;
    return this.order.chitietdonhang.reduce(
      (total, item) => total + item.soluong,
      0
    );
  }
}
