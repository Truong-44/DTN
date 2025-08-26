import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { VanChuyen } from '../../../../core/models/vanchuyen.model';
import { DonHang } from '../../../../core/models/donhang.model';
import { ApiService } from '../../../../core/services/api.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { catchError, forkJoin, of } from 'rxjs';

@Component({
  selector: 'app-shipping-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './shipping-list.component.html',
  styleUrls: ['./shipping-list.component.scss'],
})
export class ShippingListComponent implements OnInit {
  shipments: (VanChuyen & { donhang?: DonHang })[] = [];
  filteredShipments: (VanChuyen & { donhang?: DonHang })[] = [];
  loading = false;

  searchKeyword = '';
  statusFilter = '';
  carrierFilter = '';

  showUpdateModal = false;
  editingShipment: any = {};

  totalShipments = 0;
  pendingShipments = 0;
  inTransitShipments = 0;
  deliveredShipments = 0;

  constructor(
    private apiService: ApiService,
    private notificationService: NotificationService
  ) {}

  ngOnInit() {
    this.loadShipments();
  }

  loadShipments() {
    this.loading = true;

    // Load shipments and orders from backend
    forkJoin({
      vanChuyens: this.apiService.get<VanChuyen[]>('/api/vanchuyen').pipe(
        catchError((error) => {
          console.error('Error loading shipments:', error);
          return of([]);
        })
      ),
      donHangs: this.apiService.get<DonHang[]>('/api/donhang').pipe(
        catchError((error) => {
          console.error('Error loading orders:', error);
          return of([]);
        })
      ),
    }).subscribe({
      next: (data) => {
        // Combine shipment data with order data
        this.shipments = data.vanChuyens.map((vanChuyen) => {
          const donHang = data.donHangs.find(
            (dh) => dh.id === vanChuyen.donhangid
          );
          return {
            ...vanChuyen,
            donhang: donHang,
          };
        });
        this.calculateStats();
        this.applyFilters();
        this.loading = false;
      },
      error: (error) => {
        console.error('Error loading shipment data:', error);
        this.notificationService.error(
          'Lỗi',
          'Không thể tải dữ liệu vận chuyển'
        );
        this.loading = false;
      },
    });
  }

  applyFilters() {
    this.filteredShipments = this.shipments.filter((shipment) => {
      const matchesSearch =
        !this.searchKeyword ||
        shipment.mavanchuyen
          ?.toLowerCase()
          .includes(this.searchKeyword.toLowerCase()) ||
        shipment.id?.toString().includes(this.searchKeyword) ||
        shipment.donhang?.khachhang?.hoten
          ?.toLowerCase()
          .includes(this.searchKeyword.toLowerCase());

      const matchesStatus =
        !this.statusFilter || shipment.trangthai === this.statusFilter;

      const matchesCarrier =
        !this.carrierFilter || shipment.donvivanchuyen === this.carrierFilter;

      return matchesSearch && matchesStatus && matchesCarrier;
    });
  }

  calculateStats() {
    this.totalShipments = this.shipments.length;
    this.pendingShipments = this.shipments.filter(
      (s) => s.trangthai === 'PENDING'
    ).length;
    this.inTransitShipments = this.shipments.filter(
      (s) => s.trangthai === 'IN_TRANSIT'
    ).length;
    this.deliveredShipments = this.shipments.filter(
      (s) => s.trangthai === 'DELIVERED'
    ).length;
  }

  createShipment() {
    // Navigate to shipment creation page or open modal
    this.notificationService.info(
      'Thông báo',
      'Tính năng tạo đơn vận chuyển đang được phát triển'
    );
  }

  trackShipment(shipment: VanChuyen) {
    // Track shipment functionality
    this.notificationService.info(
      'Theo dõi',
      `Tracking: ${shipment.mavanchuyen || 'Chưa có mã vận chuyển'}`
    );
  }

  updateShipment(shipment: VanChuyen) {
    this.editingShipment = { ...shipment };
    this.showUpdateModal = true;
  }

  saveShipmentUpdate() {
    if (!this.editingShipment.id) {
      this.notificationService.error('Lỗi', 'Không tìm thấy ID vận chuyển');
      return;
    }

    const updateData = {
      trangthai: this.editingShipment.trangthai,
      donvivanchuyen: this.editingShipment.donvivanchuyen,
      mavanchuyen: this.editingShipment.mavanchuyen,
      ngaygiaodk: this.editingShipment.ngaygiaodk,
    };

    this.apiService
      .put(`/api/vanchuyen/${this.editingShipment.id}`, updateData)
      .subscribe({
        next: () => {
          // Update local data
          const index = this.shipments.findIndex(
            (s) => s.id === this.editingShipment.id
          );
          if (index !== -1) {
            Object.assign(this.shipments[index], updateData);
          }
          this.calculateStats();
          this.applyFilters();
          this.showUpdateModal = false;
          this.notificationService.success(
            'Thành công',
            'Đã cập nhật thông tin vận chuyển'
          );
        },
        error: (error) => {
          console.error('Error updating shipment:', error);
          this.notificationService.error(
            'Lỗi',
            'Không thể cập nhật thông tin vận chuyển'
          );
        },
      });
  }

  cancelShipment(shipment: VanChuyen) {
    if (confirm('Bạn có chắc muốn hủy đơn vận chuyển này?')) {
      const updateData = { trangthai: 'CANCELLED' };

      this.apiService
        .put(`/api/vanchuyen/${shipment.id}`, updateData)
        .subscribe({
          next: () => {
            shipment.trangthai = 'CANCELLED';
            this.calculateStats();
            this.applyFilters();
            this.notificationService.success(
              'Thành công',
              'Đã hủy đơn vận chuyển'
            );
          },
          error: (error) => {
            console.error('Error cancelling shipment:', error);
            this.notificationService.error(
              'Lỗi',
              'Không thể hủy đơn vận chuyển'
            );
          },
        });
    }
  }

  deleteShipment(shipment: VanChuyen) {
    if (
      confirm(
        'Bạn có chắc muốn xóa đơn vận chuyển này? Hành động này không thể hoàn tác.'
      )
    ) {
      this.apiService.delete(`/api/vanchuyen/${shipment.id}`).subscribe({
        next: () => {
          this.shipments = this.shipments.filter((s) => s.id !== shipment.id);
          this.calculateStats();
          this.applyFilters();
          this.notificationService.success(
            'Thành công',
            'Đã xóa đơn vận chuyển'
          );
        },
        error: (error) => {
          console.error('Error deleting shipment:', error);
          this.notificationService.error('Lỗi', 'Không thể xóa đơn vận chuyển');
        },
      });
    }
  }

  closeModal(event: Event) {
    if (event.target === event.currentTarget) {
      this.showUpdateModal = false;
    }
  }

  formatDate(dateString: string | Date | undefined): string {
    if (!dateString) return 'N/A';
    return new Date(dateString).toLocaleDateString('vi-VN');
  }

  getStatusLabel(status: string): string {
    const statusMap: { [key: string]: string } = {
      PENDING: 'Đang chờ',
      IN_TRANSIT: 'Đang giao',
      DELIVERED: 'Đã giao',
      CANCELLED: 'Đã hủy',
    };
    return statusMap[status] || 'Đang chờ';
  }

  getCarrierLabel(carrier: string | undefined): string {
    if (!carrier) return 'N/A';
    const carrierMap: { [key: string]: string } = {
      GHN: 'Giao Hàng Nhanh',
      GHTK: 'Giao Hàng Tiết Kiệm',
      VNPOST: 'VNPost',
      VIETTEL_POST: 'Viettel Post',
    };
    return carrierMap[carrier] || carrier;
  }

  // Add missing methods from template
  refreshData() {
    this.loadShipments();
  }

  exportData() {
    this.notificationService.info('Thông báo', 'Tính năng xuất Excel đang được phát triển');
  }

  trackByShipmentId(index: number, shipment: VanChuyen): number {
    return shipment.id;
  }

  getStatusClass(status: string | undefined): string {
    if (!status) return 'status-pending';
    const statusClassMap: { [key: string]: string } = {
      'PENDING': 'status-pending',
      'IN_TRANSIT': 'status-in-transit', 
      'DELIVERED': 'status-delivered',
      'CANCELLED': 'status-cancelled',
      'Đang chờ': 'status-pending',
      'Đang giao': 'status-in-transit',
      'Đã giao': 'status-delivered',
      'Đã hủy': 'status-cancelled'
    };
    return statusClassMap[status] || 'status-pending';
  }

  formatCurrency(amount: number | undefined): string {
    if (!amount) return '0 ₫';
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND'
    }).format(amount);
  }
}
