import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-phanquyen',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="page-container">
      <div class="page-header">
        <h2>🔐 Phân quyền</h2>
        <button class="btn-primary" (click)="showAddForm = true">+ Thêm quyền</button>
      </div>

      <!-- Form Modal -->
      <div class="form-modal" *ngIf="showAddForm || showEditForm">
        <div class="form-content">
          <h3>{{ showEditForm ? 'Sửa' : 'Thêm' }} quyền</h3>
          <form (ngSubmit)="saveQuyen()">
            <div class="form-group">
              <label>Tài khoản:</label>
              <select [(ngModel)]="formData.taiKhoanId" name="taiKhoanId" required>
                <option value="">Chọn tài khoản</option>
                <option *ngFor="let tk of taiKhoanList" [value]="tk.id">{{ tk.tenDangNhap }} - {{ tk.hoTen }}</option>
              </select>
            </div>
            <div class="form-group">
              <label>Vai trò:</label>
              <select [(ngModel)]="formData.vaiTro" name="vaiTro" required>
                <option value="admin">Admin</option>
                <option value="nhanvien">Nhân viên</option>
                <option value="khachhang">Khách hàng</option>
              </select>
            </div>
            <div class="permissions-section">
              <h4>Quyền truy cập:</h4>
              <div class="permission-grid">
                <label *ngFor="let permission of availablePermissions">
                  <input type="checkbox" 
                         [checked]="formData.permissions.includes(permission.key)"
                         (change)="togglePermission(permission.key, $event)">
                  {{ permission.label }}
                </label>
              </div>
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
              <th>Tài khoản</th>
              <th>Họ tên</th>
              <th>Vai trò</th>
              <th>Quyền</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let item of phanQuyenList">
              <td>{{ item.id }}</td>
              <td>{{ getTaiKhoanName(item.taiKhoanId) }}</td>
              <td>{{ getTaiKhoanHoTen(item.taiKhoanId) }}</td>
              <td>
                <span class="role-badge" [class]="item.vaiTro">{{ item.vaiTro }}</span>
              </td>
              <td>
                <div class="permissions-list">
                  <span *ngFor="let perm of item.permissions" class="permission-tag">
                    {{ getPermissionLabel(perm) }}
                  </span>
                </div>
              </td>
              <td>
                <button class="btn-edit" (click)="editQuyen(item)">Sửa</button>
                <button class="btn-delete" (click)="deleteQuyen(item.id)">Xóa</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Role Summary -->
      <div class="role-summary">
        <h3>Thống kê phân quyền</h3>
        <div class="summary-grid">
          <div class="summary-card admin">
            <h4>Admin</h4>
            <p class="count">{{ getRoleCount('admin') }}</p>
          </div>
          <div class="summary-card nhanvien">
            <h4>Nhân viên</h4>
            <p class="count">{{ getRoleCount('nhanvien') }}</p>
          </div>
          <div class="summary-card khachhang">
            <h4>Khách hàng</h4>
            <p class="count">{{ getRoleCount('khachhang') }}</p>
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
    .form-group select {
      width: 100%;
      padding: 8px;
      border: 1px solid #ddd;
      border-radius: 4px;
      box-sizing: border-box;
    }
    
    .permissions-section {
      margin: 20px 0;
    }
    
    .permissions-section h4 {
      color: #ff6600;
      margin: 0 0 10px 0;
    }
    
    .permission-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
      gap: 10px;
    }
    
    .permission-grid label {
      display: flex;
      align-items: center;
      gap: 8px;
      padding: 8px;
      background: #f8f9fa;
      border-radius: 4px;
      cursor: pointer;
    }
    
    .permission-grid input[type="checkbox"] {
      width: auto;
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
      margin-bottom: 30px;
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
    
    .role-badge {
      padding: 4px 8px;
      border-radius: 4px;
      font-size: 0.8rem;
      font-weight: bold;
    }
    
    .role-badge.admin {
      background: #dc3545;
      color: white;
    }
    
    .role-badge.nhanvien {
      background: #007bff;
      color: white;
    }
    
    .role-badge.khachhang {
      background: #28a745;
      color: white;
    }
    
    .permissions-list {
      display: flex;
      flex-wrap: wrap;
      gap: 4px;
    }
    
    .permission-tag {
      background: #e9ecef;
      padding: 2px 6px;
      border-radius: 3px;
      font-size: 0.7rem;
      color: #495057;
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
    
    .role-summary {
      border-top: 2px solid #ff6600;
      padding-top: 20px;
    }
    
    .role-summary h3 {
      color: #ff6600;
      margin: 0 0 15px 0;
    }
    
    .summary-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
      gap: 15px;
    }
    
    .summary-card {
      border-radius: 8px;
      padding: 15px;
      text-align: center;
      color: white;
    }
    
    .summary-card.admin {
      background: #dc3545;
    }
    
    .summary-card.nhanvien {
      background: #007bff;
    }
    
    .summary-card.khachhang {
      background: #28a745;
    }
    
    .summary-card h4 {
      margin: 0 0 10px 0;
    }
    
    .summary-card .count {
      font-size: 1.5rem;
      font-weight: bold;
      margin: 0;
    }
  `]
})
export class PhanQuyenComponent implements OnInit {
  phanQuyenList: any[] = [];
  taiKhoanList: any[] = [];
  showAddForm = false;
  showEditForm = false;
  currentId: number | null = null;
  
  formData = {
    taiKhoanId: null,
    vaiTro: 'khachhang',
    permissions: [] as string[]
  };

  availablePermissions = [
    { key: 'dashboard.view', label: 'Xem dashboard' },
    { key: 'taikhoan.view', label: 'Xem tài khoản' },
    { key: 'taikhoan.create', label: 'Tạo tài khoản' },
    { key: 'taikhoan.edit', label: 'Sửa tài khoản' },
    { key: 'taikhoan.delete', label: 'Xóa tài khoản' },
    { key: 'sanpham.view', label: 'Xem sản phẩm' },
    { key: 'sanpham.create', label: 'Tạo sản phẩm' },
    { key: 'sanpham.edit', label: 'Sửa sản phẩm' },
    { key: 'sanpham.delete', label: 'Xóa sản phẩm' },
    { key: 'donhang.view', label: 'Xem đơn hàng' },
    { key: 'donhang.edit', label: 'Sửa đơn hàng' },
    { key: 'khohang.view', label: 'Xem kho hàng' },
    { key: 'khohang.manage', label: 'Quản lý kho' }
  ];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadData();
    this.loadTaiKhoan();
  }

  loadData() {
    this.http.get<any>('http://localhost:8080/api/phanquyen').subscribe({
      next: (response) => {
        this.phanQuyenList = response.data || response;
      },
      error: () => {
        this.phanQuyenList = [
          { id: 1, taiKhoanId: 1, vaiTro: 'admin', permissions: ['dashboard.view', 'taikhoan.view', 'taikhoan.create', 'taikhoan.edit', 'sanpham.view', 'sanpham.create'] },
          { id: 2, taiKhoanId: 2, vaiTro: 'nhanvien', permissions: ['dashboard.view', 'sanpham.view', 'donhang.view', 'donhang.edit'] },
          { id: 3, taiKhoanId: 3, vaiTro: 'khachhang', permissions: ['sanpham.view'] }
        ];
      }
    });
  }

  loadTaiKhoan() {
    this.http.get<any>('http://localhost:8080/api/taikhoan').subscribe({
      next: (response) => {
        this.taiKhoanList = response.data || response;
      },
      error: () => {
        this.taiKhoanList = [
          { id: 1, tenDangNhap: 'admin', hoTen: 'Administrator' },
          { id: 2, tenDangNhap: 'nv001', hoTen: 'Nguyễn Văn A' },
          { id: 3, tenDangNhap: 'kh001', hoTen: 'Trần Thị B' }
        ];
      }
    });
  }

  getTaiKhoanName(id: any) {
    const tk = this.taiKhoanList.find(t => t.id == id);
    return tk?.tenDangNhap || 'Không xác định';
  }

  getTaiKhoanHoTen(id: any) {
    const tk = this.taiKhoanList.find(t => t.id == id);
    return tk?.hoTen || 'Không xác định';
  }

  getPermissionLabel(key: string) {
    const perm = this.availablePermissions.find(p => p.key === key);
    return perm?.label || key;
  }

  getRoleCount(role: string) {
    return this.phanQuyenList.filter(item => item.vaiTro === role).length;
  }

  togglePermission(key: string, event: any) {
    if (event.target.checked) {
      if (!this.formData.permissions.includes(key)) {
        this.formData.permissions.push(key);
      }
    } else {
      this.formData.permissions = this.formData.permissions.filter(p => p !== key);
    }
  }

  saveQuyen() {
    if (this.showEditForm && this.currentId) {
      this.http.put(`http://localhost:8080/api/phanquyen/${this.currentId}`, this.formData).subscribe({
        next: () => {
          this.loadData();
          this.closeForm();
        },
        error: () => alert('Lỗi khi cập nhật phân quyền')
      });
    } else {
      this.http.post('http://localhost:8080/api/phanquyen', this.formData).subscribe({
        next: () => {
          this.loadData();
          this.closeForm();
        },
        error: () => alert('Lỗi khi thêm phân quyền')
      });
    }
  }

  editQuyen(item: any) {
    this.currentId = item.id;
    this.formData = { ...item };
    this.showEditForm = true;
  }

  deleteQuyen(id: number) {
    if (confirm('Bạn có chắc muốn xóa phân quyền này?')) {
      this.http.delete(`http://localhost:8080/api/phanquyen/${id}`).subscribe({
        next: () => this.loadData(),
        error: () => alert('Lỗi khi xóa phân quyền')
      });
    }
  }

  closeForm() {
    this.showAddForm = false;
    this.showEditForm = false;
    this.currentId = null;
    this.formData = {
      taiKhoanId: null,
      vaiTro: 'khachhang',
      permissions: []
    };
  }
}
