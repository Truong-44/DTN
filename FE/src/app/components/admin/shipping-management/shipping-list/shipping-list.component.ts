import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

interface VanChuyen {
  id: number;
  mavanchuyen: string;
  donhangid: number;
  donvivanchuyen: string;
  trangthai: string;
  ngaygui?: string;
  ngaygiaodk?: string;
  ngaygiaothucte?: string;
  phivanchuyen: number;
  ghichu?: string;
}

@Component({
  selector: 'app-shipping-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './shipping-list.component.html',
  styleUrls: ['./shipping-list.component.scss'],
})
export class ShippingListComponent implements OnInit {
  shipments: VanChuyen[] = [];
  filteredShipments: VanChuyen[] = [];
  loading = true;
  
  private apiUrl = 'http://localhost:8080/api';

  searchKeyword = '';
  statusFilter = '';
  carrierFilter = '';

  totalShipments = 0;
  pendingShipments = 0;
  inTransitShipments = 0;
  deliveredShipments = 0;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadShipments();
  }

  loadShipments() {
    this.loading = true;

    this.http.get<any>(`${this.apiUrl}/vanchuyen`)
      .subscribe({
        next: (response) => {
          this.shipments = response.data || response || [];
          this.calculateStats();
          this.applyFilters();
          this.loading = false;
        },
        error: (error: any) => {
          console.error('Error loading shipments:', error);
          this.shipments = this.getMockShipments();
          this.calculateStats();
          this.applyFilters();
          this.loading = false;
        }
      });
  }

  getMockShipments(): VanChuyen[] {
    return [
      {
        id: 1,
        mavanchuyen: 'VC001',
        donhangid: 1,
        donvivanchuyen: 'GHN',
        trangthai: 'Đang giao',
        ngaygui: '2025-08-25T10:00:00',
        ngaygiaodk: '2025-08-27T16:00:00',
        phivanchuyen: 30000,
        ghichu: 'Giao hàng nhanh'
      },
      {
        id: 2,
        mavanchuyen: 'VC002',
        donhangid: 2,
        donvivanchuyen: 'GHTK',
        trangthai: 'Đã giao',
        ngaygui: '2025-08-24T14:30:00',
        ngaygiaodk: '2025-08-26T10:00:00',
        ngaygiaothucte: '2025-08-26T09:30:00',
        phivanchuyen: 25000
      },
      {
        id: 3,
        mavanchuyen: 'VC003',
        donhangid: 3,
        donvivanchuyen: 'VNPOST',
        trangthai: 'Đang chờ',
        ngaygui: '2025-08-26T08:00:00',
        ngaygiaodk: '2025-08-28T17:00:00',
        phivanchuyen: 20000,
        ghichu: 'Hàng dễ vỡ'
      }
    ];
  }

  applyFilters() {
    this.filteredShipments = this.shipments.filter((shipment) => {
      const matchesSearch = !this.searchKeyword ||
        shipment.mavanchuyen.toLowerCase().includes(this.searchKeyword.toLowerCase()) ||
        shipment.id.toString().includes(this.searchKeyword);

      const matchesStatus = !this.statusFilter || shipment.trangthai === this.statusFilter;
      const matchesCarrier = !this.carrierFilter || shipment.donvivanchuyen === this.carrierFilter;

      return matchesSearch && matchesStatus && matchesCarrier;
    });
  }

  calculateStats() {
    this.totalShipments = this.shipments.length;
    this.pendingShipments = this.shipments.filter(s => s.trangthai === 'Đang chờ').length;
    this.inTransitShipments = this.shipments.filter(s => s.trangthai === 'Đang giao').length;
    this.deliveredShipments = this.shipments.filter(s => s.trangthai === 'Đã giao').length;
  }

  createShipment() {
    console.log('Tính năng tạo đơn vận chuyển đang được phát triển!');
    alert('Tính năng tạo đơn vận chuyển đang được phát triển!');
  }

  trackShipment(shipment: VanChuyen) {
    console.log('Tracking:', shipment.mavanchuyen);
    alert(`Tracking: ${shipment.mavanchuyen}`);
  }

  updateShipment(shipment: VanChuyen) {
    console.log('Tính năng cập nhật đang được phát triển!', shipment);
    alert('Tính năng cập nhật đang được phát triển!');
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

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND'
    }).format(amount);
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'Đã giao': return 'status-delivered';
      case 'Đang giao': return 'status-shipping';
      case 'Đang chờ': return 'status-pending';
      case 'Đã hủy': return 'status-cancelled';
      default: return 'status-pending';
    }
  }

  getCarrierLabel(carrier: string): string {
    const carrierMap: { [key: string]: string } = {
      GHN: 'Giao Hàng Nhanh',
      GHTK: 'Giao Hàng Tiết Kiệm',
      VNPOST: 'VNPost',
      VIETTEL_POST: 'Viettel Post',
    };
    return carrierMap[carrier] || carrier;
  }

  refreshData(): void {
    this.loadShipments();
  }

  exportData(): void {
    console.log('Tính năng xuất dữ liệu đang được phát triển!');
    alert('Tính năng xuất dữ liệu đang được phát triển!');
  }

  trackByShipmentId(index: number, shipment: VanChuyen): number {
    return shipment.id;
  }

  getStatusLabel(status: string): string {
    return status;
  }
}
