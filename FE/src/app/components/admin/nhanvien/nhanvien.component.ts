import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-nhanvien',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="page-container">
      <div class="page-header">
        <h2>👨‍💼 Quản lý nhân viên</h2>
        <button class="btn-primary" (click)="showAddForm = true">+ Thêm nhân viên</button>
      </div>

      <!-- Form Modal -->
      <div class="form-modal" *ngIf="showAddForm || showEditForm">
        <div class="form-content">
          <h3>{{ showEditForm ? 'Sửa' : 'Thêm' }} nhân viên</h3>
          <form (ngSubmit)="saveNhanVien()">
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
                <label>Chức vụ:</label>
                <select [(ngModel)]="formData.chucVu" name="chucVu" required>
                  <option value="Nhân viên">Nhân viên</option>
                  <option value="Trưởng phòng">Trưởng phòng</option>
                  <option value="Quản lý">Quản lý</option>
                </select>
              </div>
            </div>
            <div class="form-row">
              <div class="form-group">
                <label>Lương cơ bản:</label>
                <input type="number" [(ngModel)]="formData.luongCoBan" name="luongCoBan" required>
              </div>
              <div class="form-group">
                <label>Ngày vào làm:</label>
                <input type="date" [(ngModel)]="formData.ngayVaoLam" name="ngayVaoLam" required>
              </div>
            </div>
            <div class="form-group">
              <label>Địa chỉ:</label>
              <textarea [(ngModel)]="formData.diaChi" name="diaChi" rows="3"></textarea>
            </div>
            <div class="form-actions">
              <button type="submit" class="btn-primary">Lưu</button>
              <button type="button" class="btn-secondary" (click)="closeForm()">Hủy</button>
            </div>
          </form>
        </div>
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
              <th>Chức vụ</th>
              <th>Lương cơ bản</th>
              <th>Ngày vào làm</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let item of nhanVienList">
              <td>{{ item.id }}</td>
              <td>{{ item.hoTen }}</td>
              <td>{{ item.email }}</td>
              <td>{{ item.soDienThoai }}</td>
              <td>{{ item.chucVu }}</td>
              <td>{{ item.luongCoBan | number }} VNĐ</td>
              <td>{{ item.ngayVaoLam | date:'dd/MM/yyyy' }}</td>
              <td>
                <button class="btn-edit" (click)="editNhanVien(item)">Sửa</button>
                <button class="btn-delete" (click)="deleteNhanVien(item.id)">Xóa</button>
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
export class NhanVienComponent implements OnInit {
  nhanVienList: any[] = [];
  showAddForm = false;
  showEditForm = false;
  currentId: number | null = null;
  
  formData = {
    hoTen: '',
    email: '',
    soDienThoai: '',
    chucVu: 'Nhân viên',
    luongCoBan: 0,
    ngayVaoLam: '',
    diaChi: ''
  };

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.http.get<any>('http://localhost:8080/api/nhanvien').subscribe({
      next: (response) => {
        this.nhanVienList = response.data || response;
      },
      error: () => {
        this.nhanVienList = [
          { id: 1, hoTen: 'Nguyễn Văn A', email: 'nva@dtn.com', soDienThoai: '0123456789', chucVu: 'Quản lý', luongCoBan: 15000000, ngayVaoLam: '2023-01-15' },
          { id: 2, hoTen: 'Trần Thị B', email: 'ttb@dtn.com', soDienThoai: '0987654321', chucVu: 'Nhân viên', luongCoBan: 8000000, ngayVaoLam: '2023-03-20' }
        ];
      }
    });
  }

  saveNhanVien() {
    if (this.showEditForm && this.currentId) {
      this.http.put(`http://localhost:8080/api/nhanvien/${this.currentId}`, this.formData).subscribe({
        next: () => {
          this.loadData();
          this.closeForm();
        },
        error: () => alert('Lỗi khi cập nhật nhân viên')
      });
    } else {
      this.http.post('http://localhost:8080/api/nhanvien', this.formData).subscribe({
        next: () => {
          this.loadData();
          this.closeForm();
        },
        error: () => alert('Lỗi khi thêm nhân viên')
      });
    }
  }

  editNhanVien(item: any) {
    this.currentId = item.id;
    this.formData = { ...item };
    this.showEditForm = true;
  }

  deleteNhanVien(id: number) {
    if (confirm('Bạn có chắc muốn xóa nhân viên này?')) {
      this.http.delete(`http://localhost:8080/api/nhanvien/${id}`).subscribe({
        next: () => this.loadData(),
        error: () => alert('Lỗi khi xóa nhân viên')
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
      chucVu: 'Nhân viên',
      luongCoBan: 0,
      ngayVaoLam: '',
      diaChi: ''
    };
  }
}
