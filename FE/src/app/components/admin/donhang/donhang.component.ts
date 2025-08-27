import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-donhang',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="page-container">
      <div class="page-header">
        <h2>🛒 Quản lý đơn hàng</h2>
        <div class="filter-section">
          <select [(ngModel)]="statusFilter" (change)="filterData()">
            <option value="">Tất cả trạng thái</option>
            <option value="Chờ xử lý">Chờ xử lý</option>
            <option value="Đang xử lý">Đang xử lý</option>
            <option value="Đang giao">Đang giao</option>
            <option value="Hoàn thành">Hoàn thành</option>
            <option value="Đã hủy">Đã hủy</option>
          </select>
        </div>
      </div>

      <!-- Data Table -->
      <div class="data-table">
        <table>
          <thead>
            <tr>
              <th>Mã đơn hàng</th>
              <th>Khách hàng</th>
              <th>Ngày đặt</th>
              <th>Tổng tiền</th>
              <th>Trạng thái</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let item of filteredList">
              <td>{{ item.maDonHang }}</td>
              <td>{{ item.tenKhachHang }}</td>
              <td>{{ item.ngayDat | date:'dd/MM/yyyy HH:mm' }}</td>
              <td>{{ item.tongTien | number }} VNĐ</td>
              <td>
                <select [(ngModel)]="item.trangThai" (change)="updateStatus(item)">
                  <option value="Chờ xử lý">Chờ xử lý</option>
                  <option value="Đang xử lý">Đang xử lý</option>
                  <option value="Đang giao">Đang giao</option>
                  <option value="Hoàn thành">Hoàn thành</option>
                  <option value="Đã hủy">Đã hủy</option>
                </select>
              </td>
              <td>
                <button class="btn-view" (click)="viewDetail(item)">Chi tiết</button>
                <button class="btn-delete" (click)="deleteDonHang(item.id)">Xóa</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Detail Modal -->
      <div class="form-modal" *ngIf="showDetailModal">
        <div class="form-content large">
          <h3>Chi tiết đơn hàng {{ selectedOrder?.maDonHang }}</h3>
          <div class="order-info">
            <div class="info-section">
              <h4>Thông tin khách hàng</h4>
              <p><strong>Tên:</strong> {{ selectedOrder?.tenKhachHang }}</p>
              <p><strong>Email:</strong> {{ selectedOrder?.email }}</p>
              <p><strong>Điện thoại:</strong> {{ selectedOrder?.soDienThoai }}</p>
              <p><strong>Địa chỉ:</strong> {{ selectedOrder?.diaChiGiao }}</p>
            </div>
            <div class="info-section">
              <h4>Thông tin đơn hàng</h4>
              <p><strong>Ngày đặt:</strong> {{ selectedOrder?.ngayDat | date:'dd/MM/yyyy HH:mm' }}</p>
              <p><strong>Trạng thái:</strong> {{ selectedOrder?.trangThai }}</p>
              <p><strong>Tổng tiền:</strong> {{ selectedOrder?.tongTien | number }} VNĐ</p>
            </div>
          </div>
          <div class="order-items">
            <h4>Sản phẩm đã đặt</h4>
            <table>
              <thead>
                <tr>
                  <th>Sản phẩm</th>
                  <th>Số lượng</th>
                  <th>Đơn giá</th>
                  <th>Thành tiền</th>
                </tr>
              </thead>
              <tbody>
                <tr *ngFor="let item of selectedOrder?.chiTietItems">
                  <td>{{ item.tenSanPham }}</td>
                  <td>{{ item.soLuong }}</td>
                  <td>{{ item.donGia | number }} VNĐ</td>
                  <td>{{ item.soLuong * item.donGia | number }} VNĐ</td>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="form-actions">
            <button class="btn-secondary" (click)="showDetailModal = false">Đóng</button>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .page-container {
      background: white;
      border-radius: 8px;
      padding: 20px;
      box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }
    
    .page-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;
    }
    
    .page-header h2 {
      color: #ff6600;
      margin: 0;
    }
    
    .filter-section select {
      padding: 8px;
      border: 1px solid #ddd;
      border-radius: 4px;
    }
    
    .data-table {
      overflow-x: auto;
    }
    
    table {
      width: 100%;
      border-collapse: collapse;
    }
    
    th, td {
      padding: 12px;
      text-align: left;
      border-bottom: 1px solid #ddd;
    }
    
    th {
      background: #f8f9fa;
      color: #333;
      font-weight: bold;
    }
    
    td select {
      padding: 5px;
      border: 1px solid #ddd;
      border-radius: 3px;
    }
    
    .btn-view {
      background: #17a2b8;
      color: white;
      border: none;
      padding: 5px 10px;
      border-radius: 3px;
      cursor: pointer;
      margin-right: 5px;
    }
    
    .btn-delete {
      background: #dc3545;
      color: white;
      border: none;
      padding: 5px 10px;
      border-radius: 3px;
      cursor: pointer;
    }
    
    .form-modal {
      position: fixed;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background: rgba(0,0,0,0.5);
      display: flex;
      align-items: center;
      justify-content: center;
      z-index: 1000;
    }
    
    .form-content {
      background: white;
      padding: 30px;
      border-radius: 8px;
      width: 600px;
      max-width: 90%;
      max-height: 90vh;
      overflow-y: auto;
    }
    
    .form-content.large {
      width: 800px;
    }
    
    .form-content h3 {
      color: #ff6600;
      margin: 0 0 20px 0;
    }
    
    .order-info {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 20px;
      margin-bottom: 20px;
    }
    
    .info-section h4 {
      color: #ff6600;
      margin: 0 0 10px 0;
    }
    
    .info-section p {
      margin: 5px 0;
    }
    
    .order-items {
      margin-bottom: 20px;
    }
    
    .order-items h4 {
      color: #ff6600;
      margin: 0 0 10px 0;
    }
    
    .form-actions {
      display: flex;
      gap: 10px;
      margin-top: 20px;
    }
    
    .btn-secondary {
      background: #6c757d;
      color: white;
      border: none;
      padding: 10px 20px;
      border-radius: 4px;
      cursor: pointer;
    }
  `]
})
export class DonHangComponent implements OnInit {
  donHangList: any[] = [];
  filteredList: any[] = [];
  statusFilter = '';
  showDetailModal = false;
  selectedOrder: any = null;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.http.get<any>('http://localhost:8080/api/donhang').subscribe({
      next: (response) => {
        this.donHangList = response.data || response;
        this.filteredList = [...this.donHangList];
      },
      error: () => {
        this.donHangList = [
          {
            id: 1,
            maDonHang: 'DH001',
            tenKhachHang: 'Nguyễn Văn A',
            email: 'nva@gmail.com',
            soDienThoai: '0123456789',
            diaChiGiao: '123 Nguyễn Trãi, Q1, HCM',
            ngayDat: '2025-08-27T10:30:00',
            tongTien: 5000000,
            trangThai: 'Chờ xử lý',
            chiTietItems: [
              { tenSanPham: 'Bàn ăn gỗ sồi', soLuong: 1, donGia: 4500000 },
              { tenSanPham: 'Ghế gỗ', soLuong: 4, donGia: 125000 }
            ]
          },
          {
            id: 2,
            maDonHang: 'DH002',
            tenKhachHang: 'Trần Thị B',
            email: 'ttb@gmail.com',
            soDienThoai: '0987654321',
            diaChiGiao: '456 Lê Lợi, Q3, HCM',
            ngayDat: '2025-08-26T14:15:00',
            tongTien: 7200000,
            trangThai: 'Hoàn thành',
            chiTietItems: [
              { tenSanPham: 'Ghế sofa 3 chỗ', soLuong: 1, donGia: 7200000 }
            ]
          }
        ];
        this.filteredList = [...this.donHangList];
      }
    });
  }

  filterData() {
    this.filteredList = this.donHangList.filter(item => {
      return !this.statusFilter || item.trangThai === this.statusFilter;
    });
  }

  updateStatus(order: any) {
    this.http.put(`http://localhost:8080/api/donhang/${order.id}/status`, { trangThai: order.trangThai }).subscribe({
      next: () => {},
      error: () => alert('Lỗi khi cập nhật trạng thái')
    });
  }

  viewDetail(order: any) {
    this.selectedOrder = order;
    this.showDetailModal = true;
  }

  deleteDonHang(id: number) {
    if (confirm('Bạn có chắc muốn xóa đơn hàng này?')) {
      this.http.delete(`http://localhost:8080/api/donhang/${id}`).subscribe({
        next: () => this.loadData(),
        error: () => alert('Lỗi khi xóa đơn hàng')
      });
    }
  }
}
