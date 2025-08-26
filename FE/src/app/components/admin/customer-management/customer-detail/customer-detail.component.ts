import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { KhachHangService } from '../../../../core/services/khachhang.service';
import { KhachHang } from '../../../../core/models/khachhang.model';
import { NotificationService } from '../../../../core/services/notification.service';
import { LoadingService } from '../../../../core/services/loading.service';

@Component({
  selector: 'app-customer-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './customer-detail.component.html',
  styleUrls: ['./customer-detail.component.scss'],
})
export class CustomerDetailComponent implements OnInit {
  customer: KhachHang | null = null;
  loading = false;
  customerId: number = 0;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private khachHangService: KhachHangService,
    private notificationService: NotificationService,
    private loadingService: LoadingService
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.customerId = +params['id'];
      if (this.customerId) {
        this.loadCustomerDetail();
      } else {
        this.notificationService.error('Lỗi', 'Không tìm thấy ID khách hàng');
        this.goBack();
      }
    });
  }

  loadCustomerDetail() {
    this.loading = true;
    this.loadingService.show();

    this.khachHangService
      .getById(this.customerId)
      .pipe(
        catchError((error) => {
          console.error('Error loading customer detail:', error);
          this.notificationService.error(
            'Lỗi',
            'Không thể tải thông tin khách hàng'
          );
          return of(null);
        })
      )
      .subscribe({
        next: (customer) => {
          this.customer = customer;
          this.loading = false;
          this.loadingService.hide();

          if (!customer) {
            this.notificationService.error('Lỗi', 'Không tìm thấy khách hàng');
            this.goBack();
          }
        },
        error: (error: any) => {
          console.error('Error loading customer detail:', error);
          this.notificationService.error(
            'Lỗi',
            'Không thể tải thông tin khách hàng'
          );
          this.loading = false;
          this.loadingService.hide();
          this.goBack();
        },
      });
  }

  editCustomer() {
    if (this.customer) {
      this.router.navigate([
        '/admin/customer-management/edit',
        this.customer.id,
      ]);
    }
  }

  deleteCustomer() {
    if (!this.customer) return;

    if (confirm(`Bạn có chắc muốn xóa khách hàng "${this.customer.hoten}"?`)) {
      this.loadingService.show();

      this.khachHangService.delete(this.customer.id).subscribe({
        next: () => {
          this.notificationService.success('Thành công', 'Đã xóa khách hàng');
          this.goBack();
        },
        error: (error: any) => {
          console.error('Error deleting customer:', error);
          this.notificationService.error('Lỗi', 'Không thể xóa khách hàng');
          this.loadingService.hide();
        },
      });
    }
  }

  goBack() {
    this.router.navigate(['/admin/customer-management']);
  }

  formatDate(date: Date | string | undefined): string {
    if (!date) return 'Chưa có';

    try {
      const d = typeof date === 'string' ? new Date(date) : date;
      return d.toLocaleDateString('vi-VN');
    } catch {
      return 'Không hợp lệ';
    }
  }

  getGenderDisplay(gender: string | undefined): string {
    const genderMap: { [key: string]: string } = {
      Nam: 'Nam',
      Nữ: 'Nữ',
      Khác: 'Khác',
    };
    return genderMap[gender || ''] || 'Chưa xác định';
  }

  getInitials(name: string | undefined): string {
    if (!name) return '?';

    const words = name.trim().split(' ');
    if (words.length === 1) {
      return words[0].charAt(0).toUpperCase();
    }

    return (
      words[0].charAt(0) + words[words.length - 1].charAt(0)
    ).toUpperCase();
  }
}
