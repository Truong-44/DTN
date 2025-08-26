import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderManagerService } from '../../../core/services/order-manager.service';
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
    private orderManagerService: OrderManagerService,
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
    this.orderManagerService.getOrderById(orderId).subscribe({
      next: (order) => {
        this.order = order;
        this.loading = false;
        if (!order) {
          this.notificationService.error('Lỗi', 'Không tìm thấy đơn hàng');
          this.router.navigate(['/order-list']);
        }
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

  getStatusLabel(status: string): string {
    return this.orderManagerService.getStatusLabel(status);
  }

  getStatusClass(status: string): string {
    return this.orderManagerService.getStatusClass(status);
  }

  getOrderCode(): string {
    return this.order
      ? this.orderManagerService.getOrderCode(this.order.id)
      : '';
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

  formatPrice(price: number): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(price);
  }

  formatCurrency(price: number): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(price);
  }

  formatDate(date: Date | string): string {
    return new Date(date).toLocaleDateString('vi-VN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
    });
  }
}
