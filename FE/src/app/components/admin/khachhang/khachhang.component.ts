import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-khachhang',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="page-container">
      <div class="page-header">
        <h2>👥 Quản lý khách hàng</h2>
        <button class="btn-primary" (click)="showAddForm = true">+ Thêm khách hàng</button>
      </div>

      <!-- Form Modal -->
      <div class="form-modal" *ngIf="showAddForm || showEditForm">
        <div class="form-content">
          <h3>{{ showEditForm ? 'Sửa' : 'Thêm' }} khách hàng</h3>
          <form (ngSubmit)="saveKhachHang()">
            <div class="form-row">
              <div class="form-group">
                <label>Họ tên:</label>
                <input type="text" [(ngModel)]="formData.hoTen" name="hoTen" required>
              </div>
              <div class="form-group">
                <label>Email:</label>
                <input type="email" [(ngModel)]="formData.email" name="email" required>
              </div>
            </div>
            <div class="form-row">
              <div class="form-group">
                <label>Số điện thoại:</label>
                <input type="tel" [(ngModel)]="formData.soDienThoai" name="soDienThoai" required>
              </div>
              <div class="form-group">
                <label>Ngày sinh:</label>
                <input type="date" [(ngModel)]="formData.ngaySinh" name="ngaySinh">
              </div>
            </div>
            <div class="form-group">
              <label>Địa chỉ:</label>
              <textarea [(ngModel)]="formData.diaChi" name="diaChi" rows="3"></textarea>
            </div>
            <div class="form-row">
              <div class="form-group">
                <label>Giới tính:</label>
                <select [(ngModel)]="formData.gioiTinh" name="gioiTinh">
                  <option value="Nam">Nam</option>
                  <option value="Nữ">Nữ</option>
                  <option value="Khác">Khác</option>
                </select>
              </div>
              <div class="form-group">
                <label>Trạng thái:</label>
                <select [(ngModel)]="formData.trangThai" name="trangThai">
                  <option [value]="true">Hoạt động</option>
                  <option [value]="false">Khóa</option>
                </select>
              </div>
            </div>
            <div class="form-actions">
              <button type="submit" class="btn-primary">Lưu</button>
              <button type="button" class="btn-secondary" (click)="closeForm()">Hủy</button>
            </div>
          </form>
        </div>
      </div>

      <!-- Search and Filter -->
      <div class="filter-section">
        <input type="text" placeholder="Tìm kiếm khách hàng..." [(ngModel)]="searchTerm" (input)="filterData()">
        <select [(ngModel)]="statusFilter" (change)="filterData()">
          <option value="">Tất cả trạng thái</option>
          <option value="true">Hoạt động</option>
          <option value="false">Khóa</option>
        </select>
      </div>

      <!-- Data Table -->
      <div class="data-table">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Họ tên</th>
              <th>Email</th>
              <th>Số điện thoại</th>
              <th>Địa chỉ</th>
              <th>Giới tính</th>
              <th>Trạng thái</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let item of filteredList">
              <td>{{ item.id }}</td>
              <td>{{ item.hoTen }}</td>
              <td>{{ item.email }}</td>
              <td>{{ item.soDienThoai }}</td>
              <td>{{ item.diaChi | slice:0:30 }}{{ item.diaChi?.length > 30 ? '...' : '' }}</td>
              <td>{{ item.gioiTinh }}</td>
              <td>
                <span class="status" [class]="item.trangThai ? 'active' : 'inactive'">
                  {{ item.trangThai ? 'Hoạt động' : 'Khóa' }}
                </span>
              </td>
              <td>
                <button class="btn-edit" (click)="editKhachHang(item)">Sửa</button>
                <button class="btn-delete" (click)="deleteKhachHang(item.id)">Xóa</button>
              </td>
            </tr>
          </tbody>
        </table>
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
    
    .btn-primary {
      background: #ff6600;
      color: white;
      border: none;
      padding: 10px 20px;
      border-radius: 4px;
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
    
    .form-content h3 {
      color: #ff6600;
      margin: 0 0 20px 0;
    }
    
    .form-row {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 15px;
    }
    
    .form-group {
      margin-bottom: 15px;
    }
    
    .form-group label {
      display: block;
      margin-bottom: 5px;
      color: #333;
      font-weight: bold;
    }
    
    .form-group input,
    .form-group select,
    .form-group textarea {
      width: 100%;
      padding: 8px;
      border: 1px solid #ddd;
      border-radius: 4px;
      box-sizing: border-box;
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
    
    .filter-section {
      display: flex;
      gap: 15px;
      margin-bottom: 20px;
    }
    
    .filter-section input,
    .filter-section select {
      padding: 8px;
      border: 1px solid #ddd;
      border-radius: 4px;
    }
    
    .filter-section input {
      flex: 1;
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
    
    .status.active {
      color: #28a745;
      font-weight: bold;
    }
    
    .status.inactive {
      color: #dc3545;
      font-weight: bold;
    }
    
    .btn-edit {
      background: #007bff;
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
  `]
})
export class KhachHangComponent implements OnInit {
  khachHangList: any[] = [];
  filteredList: any[] = [];
  showAddForm = false;
  showEditForm = false;
  currentId: number | null = null;
  searchTerm = '';
  statusFilter = '';
  
  formData = {
    hoTen: '',
    email: '',
    soDienThoai: '',
    diaChi: '',
    ngaySinh: '',
    gioiTinh: 'Nam',
    trangThai: true
  };

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.http.get<any>('http://localhost:8080/api/khachhang').subscribe({
      next: (response) => {
        this.khachHangList = response.data || response;
        this.filteredList = [...this.khachHangList];
      },
      error: () => {
        // Mock data
        this.khachHangList = [
          { id: 1, hoTen: 'Nguyễn Văn A', email: 'nva@gmail.com', soDienThoai: '0123456789', diaChi: '123 Nguyễn Trãi, Q1, HCM', gioiTinh: 'Nam', trangThai: true },
          { id: 2, hoTen: 'Trần Thị B', email: 'ttb@gmail.com', soDienThoai: '0987654321', diaChi: '456 Lê Lợi, Q3, HCM', gioiTinh: 'Nữ', trangThai: true },
          { id: 3, hoTen: 'Lê Văn C', email: 'lvc@gmail.com', soDienThoai: '0369741258', diaChi: '789 Điện Biên Phủ, Q10, HCM', gioiTinh: 'Nam', trangThai: false }
        ];
        this.filteredList = [...this.khachHangList];
      }
    });
  }

  filterData() {
    this.filteredList = this.khachHangList.filter(item => {
      const matchesSearch = !this.searchTerm || 
        item.hoTen.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        item.email.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        item.soDienThoai.includes(this.searchTerm);
      
      const matchesStatus = !this.statusFilter || 
        item.trangThai.toString() === this.statusFilter;
      
      return matchesSearch && matchesStatus;
    });
  }

  saveKhachHang() {
    if (this.showEditForm && this.currentId) {
      this.http.put(`http://localhost:8080/api/khachhang/${this.currentId}`, this.formData).subscribe({
        next: () => {
          this.loadData();
          this.closeForm();
        },
        error: () => {
          alert('Lỗi khi cập nhật khách hàng');
        }
      });
    } else {
      this.http.post('http://localhost:8080/api/khachhang', this.formData).subscribe({
        next: () => {
          this.loadData();
          this.closeForm();
        },
        error: () => {
          alert('Lỗi khi thêm khách hàng');
        }
      });
    }
  }

  editKhachHang(item: any) {
    this.currentId = item.id;
    this.formData = { ...item };
    this.showEditForm = true;
  }

  deleteKhachHang(id: number) {
    if (confirm('Bạn có chắc muốn xóa khách hàng này?')) {
      this.http.delete(`http://localhost:8080/api/khachhang/${id}`).subscribe({
        next: () => {
          this.loadData();
        },
        error: () => {
          alert('Lỗi khi xóa khách hàng');
        }
      });
    }
  }

  closeForm() {
    this.showAddForm = false;
    this.showEditForm = false;
    this.currentId = null;
    this.formData = {
      hoTen: '',
      email: '',
      soDienThoai: '',
      diaChi: '',
      ngaySinh: '',
      gioiTinh: 'Nam',
      trangThai: true
    };
  }
}
