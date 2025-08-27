import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-sanpham',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="page-container">
      <div class="page-header">
        <h2>📦 Quản lý sản phẩm</h2>
        <div class="header-actions">
          <select [(ngModel)]="activeTab" (change)="loadTabData()">
            <option value="sanpham">Sản phẩm</option>
            <option value="chitietsanpham">Chi tiết sản phẩm</option>
            <option value="danhmuc">Danh mục</option>
          </select>
          <button class="btn-primary" (click)="showAddForm = true">+ Thêm mới</button>
        </div>
      </div>

      <!-- Form Modal -->
      <div class="form-modal" *ngIf="showAddForm || showEditForm">
        <div class="form-content">
          <h3>{{ showEditForm ? 'Sửa' : 'Thêm' }} {{ getTabLabel() }}</h3>
          
          <!-- Sản phẩm Form -->
          <form *ngIf="activeTab === 'sanpham'" (ngSubmit)="save()">
            <div class="form-row">
              <div class="form-group">
                <label>Tên sản phẩm:</label>
                <input type="text" [(ngModel)]="sanPhamForm.tenSanPham" name="tenSanPham" required>
              </div>
              <div class="form-group">
                <label>Danh mục:</label>
                <select [(ngModel)]="sanPhamForm.danhMucId" name="danhMucId" required>
                  <option value="">Chọn danh mục</option>
                  <option *ngFor="let dm of danhMucList" [value]="dm.id">{{ dm.tenDanhMuc }}</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label>Mô tả:</label>
              <textarea [(ngModel)]="sanPhamForm.moTa" name="moTa" rows="3"></textarea>
            </div>
            <div class="form-row">
              <div class="form-group">
                <label>Giá cũ:</label>
                <input type="number" [(ngModel)]="sanPhamForm.giaCu" name="giaCu">
              </div>
              <div class="form-group">
                <label>Giá mới:</label>
                <input type="number" [(ngModel)]="sanPhamForm.giaMoi" name="giaMoi" required>
              </div>
            </div>
            <div class="form-group">
              <label>Trạng thái:</label>
              <select [(ngModel)]="sanPhamForm.trangThai" name="trangThai">
                <option [value]="true">Hiển thị</option>
                <option [value]="false">Ẩn</option>
              </select>
            </div>
            <div class="form-actions">
              <button type="submit" class="btn-primary">Lưu</button>
              <button type="button" class="btn-secondary" (click)="closeForm()">Hủy</button>
            </div>
          </form>

          <!-- Chi tiết sản phẩm Form -->
          <form *ngIf="activeTab === 'chitietsanpham'" (ngSubmit)="save()">
            <div class="form-row">
              <div class="form-group">
                <label>Sản phẩm:</label>
                <select [(ngModel)]="chiTietForm.sanPhamId" name="sanPhamId" required>
                  <option value="">Chọn sản phẩm</option>
                  <option *ngFor="let sp of sanPhamList" [value]="sp.id">{{ sp.tenSanPham }}</option>
                </select>
              </div>
              <div class="form-group">
                <label>Tên màu:</label>
                <input type="text" [(ngModel)]="chiTietForm.tenMau" name="tenMau" required>
              </div>
            </div>
            <div class="form-row">
              <div class="form-group">
                <label>Mã màu:</label>
                <input type="color" [(ngModel)]="chiTietForm.maMau" name="maMau">
              </div>
              <div class="form-group">
                <label>Chất liệu:</label>
                <input type="text" [(ngModel)]="chiTietForm.chatLieu" name="chatLieu">
              </div>
            </div>
            <div class="form-row">
              <div class="form-group">
                <label>Kích thước:</label>
                <input type="text" [(ngModel)]="chiTietForm.kichThuoc" name="kichThuoc">
              </div>
              <div class="form-group">
                <label>Số lượng:</label>
                <input type="number" [(ngModel)]="chiTietForm.soLuong" name="soLuong" required>
              </div>
            </div>
            <div class="form-group">
              <label>Hình ảnh:</label>
              <input type="text" [(ngModel)]="chiTietForm.hinhChinh" name="hinhChinh" placeholder="URL hình ảnh">
            </div>
            <div class="form-actions">
              <button type="submit" class="btn-primary">Lưu</button>
              <button type="button" class="btn-secondary" (click)="closeForm()">Hủy</button>
            </div>
          </form>

          <!-- Danh mục Form -->
          <form *ngIf="activeTab === 'danhmuc'" (ngSubmit)="save()">
            <div class="form-group">
              <label>Tên danh mục:</label>
              <input type="text" [(ngModel)]="danhMucForm.tenDanhMuc" name="tenDanhMuc" required>
            </div>
            <div class="form-group">
              <label>Mô tả:</label>
              <textarea [(ngModel)]="danhMucForm.moTa" name="moTa" rows="3"></textarea>
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
        <!-- Sản phẩm Table -->
        <table *ngIf="activeTab === 'sanpham'">
          <thead>
            <tr>
              <th>ID</th>
              <th>Tên sản phẩm</th>
              <th>Danh mục</th>
              <th>Giá cũ</th>
              <th>Giá mới</th>
              <th>Trạng thái</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let item of currentList">
              <td>{{ item.id }}</td>
              <td>{{ item.tenSanPham }}</td>
              <td>{{ getDanhMucName(item.danhMucId) }}</td>
              <td>{{ item.giaCu | number }} VNĐ</td>
              <td>{{ item.giaMoi | number }} VNĐ</td>
              <td>
                <span class="status" [class]="item.trangThai ? 'active' : 'inactive'">
                  {{ item.trangThai ? 'Hiển thị' : 'Ẩn' }}
                </span>
              </td>
              <td>
                <button class="btn-edit" (click)="edit(item)">Sửa</button>
                <button class="btn-delete" (click)="delete(item.id)">Xóa</button>
              </td>
            </tr>
          </tbody>
        </table>

        <!-- Chi tiết sản phẩm Table -->
        <table *ngIf="activeTab === 'chitietsanpham'">
          <thead>
            <tr>
              <th>ID</th>
              <th>Sản phẩm</th>
              <th>Màu sắc</th>
              <th>Chất liệu</th>
              <th>Kích thước</th>
              <th>Số lượng</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let item of currentList">
              <td>{{ item.id }}</td>
              <td>{{ getSanPhamName(item.sanPhamId) }}</td>
              <td>
                <div class="color-cell">
                  <span class="color-box" [style.background-color]="item.maMau"></span>
                  {{ item.tenMau }}
                </div>
              </td>
              <td>{{ item.chatLieu }}</td>
              <td>{{ item.kichThuoc }}</td>
              <td>{{ item.soLuong }}</td>
              <td>
                <button class="btn-edit" (click)="edit(item)">Sửa</button>
                <button class="btn-delete" (click)="delete(item.id)">Xóa</button>
              </td>
            </tr>
          </tbody>
        </table>

        <!-- Danh mục Table -->
        <table *ngIf="activeTab === 'danhmuc'">
          <thead>
            <tr>
              <th>ID</th>
              <th>Tên danh mục</th>
              <th>Mô tả</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let item of currentList">
              <td>{{ item.id }}</td>
              <td>{{ item.tenDanhMuc }}</td>
              <td>{{ item.moTa }}</td>
              <td>
                <button class="btn-edit" (click)="edit(item)">Sửa</button>
                <button class="btn-delete" (click)="delete(item.id)">Xóa</button>
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
    
    .header-actions {
      display: flex;
      gap: 10px;
      align-items: center;
    }
    
    .header-actions select {
      padding: 8px;
      border: 1px solid #ddd;
      border-radius: 4px;
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
    
    .color-cell {
      display: flex;
      align-items: center;
      gap: 8px;
    }
    
    .color-box {
      width: 20px;
      height: 20px;
      border-radius: 3px;
      border: 1px solid #ddd;
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
export class SanPhamComponent implements OnInit {
  activeTab = 'sanpham';
  currentList: any[] = [];
  sanPhamList: any[] = [];
  chiTietList: any[] = [];
  danhMucList: any[] = [];
  
  showAddForm = false;
  showEditForm = false;
  currentId: number | null = null;
  
  sanPhamForm = {
    tenSanPham: '',
    moTa: '',
    danhMucId: null,
    giaCu: 0,
    giaMoi: 0,
    trangThai: true
  };
  
  chiTietForm = {
    sanPhamId: null,
    tenMau: '',
    maMau: '#000000',
    chatLieu: '',
    kichThuoc: '',
    soLuong: 0,
    hinhChinh: ''
  };
  
  danhMucForm = {
    tenDanhMuc: '',
    moTa: ''
  };

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadAllData();
  }

  loadAllData() {
    // Load all data for dropdowns
    this.http.get<any>('http://localhost:8080/api/danhmuc').subscribe({
      next: (response) => this.danhMucList = response.data || response,
      error: () => this.danhMucList = [
        { id: 1, tenDanhMuc: 'Bàn', moTa: 'Các loại bàn' },
        { id: 2, tenDanhMuc: 'Ghế', moTa: 'Các loại ghế' }
      ]
    });
    
    this.http.get<any>('http://localhost:8080/api/sanpham').subscribe({
      next: (response) => this.sanPhamList = response.data || response,
      error: () => this.sanPhamList = [
        { id: 1, tenSanPham: 'Bàn ăn gỗ', danhMucId: 1, giaCu: 5000000, giaMoi: 4500000, trangThai: true }
      ]
    });
    
    this.loadTabData();
  }

  loadTabData() {
    const endpoints = {
      sanpham: 'http://localhost:8080/api/sanpham',
      chitietsanpham: 'http://localhost:8080/api/chitietsanpham',
      danhmuc: 'http://localhost:8080/api/danhmuc'
    };
    
    this.http.get<any>(endpoints[this.activeTab as keyof typeof endpoints]).subscribe({
      next: (response) => {
        this.currentList = response.data || response;
      },
      error: () => {
        // Mock data based on tab
        if (this.activeTab === 'sanpham') {
          this.currentList = [
            { id: 1, tenSanPham: 'Bàn ăn gỗ sồi', danhMucId: 1, giaCu: 5000000, giaMoi: 4500000, trangThai: true },
            { id: 2, tenSanPham: 'Ghế sofa 3 chỗ', danhMucId: 2, giaCu: 8000000, giaMoi: 7200000, trangThai: true }
          ];
        } else if (this.activeTab === 'chitietsanpham') {
          this.currentList = [
            { id: 1, sanPhamId: 1, tenMau: 'Nâu gỗ', maMau: '#8B4513', chatLieu: 'Gỗ sồi', kichThuoc: '120x80cm', soLuong: 10 },
            { id: 2, sanPhamId: 2, tenMau: 'Đen', maMau: '#000000', chatLieu: 'Da thật', kichThuoc: '200x90cm', soLuong: 5 }
          ];
        } else {
          this.currentList = this.danhMucList;
        }
      }
    });
  }

  getTabLabel() {
    const labels = {
      sanpham: 'sản phẩm',
      chitietsanpham: 'chi tiết sản phẩm',
      danhmuc: 'danh mục'
    };
    return labels[this.activeTab as keyof typeof labels];
  }

  getDanhMucName(id: any) {
    const dm = this.danhMucList.find(d => d.id == id);
    return dm?.tenDanhMuc || 'Chưa phân loại';
  }

  getSanPhamName(id: any) {
    const sp = this.sanPhamList.find(s => s.id == id);
    return sp?.tenSanPham || 'Không xác định';
  }

  save() {
    const endpoints = {
      sanpham: 'http://localhost:8080/api/sanpham',
      chitietsanpham: 'http://localhost:8080/api/chitietsanpham',
      danhmuc: 'http://localhost:8080/api/danhmuc'
    };
    
    const formData = this.activeTab === 'sanpham' ? this.sanPhamForm :
                     this.activeTab === 'chitietsanpham' ? this.chiTietForm : this.danhMucForm;
    
    const url = endpoints[this.activeTab as keyof typeof endpoints];
    
    if (this.showEditForm && this.currentId) {
      this.http.put(`${url}/${this.currentId}`, formData).subscribe({
        next: () => { this.loadTabData(); this.closeForm(); },
        error: () => alert('Lỗi khi cập nhật')
      });
    } else {
      this.http.post(url, formData).subscribe({
        next: () => { this.loadTabData(); this.closeForm(); },
        error: () => alert('Lỗi khi thêm mới')
      });
    }
  }

  edit(item: any) {
    this.currentId = item.id;
    if (this.activeTab === 'sanpham') {
      this.sanPhamForm = { ...item };
    } else if (this.activeTab === 'chitietsanpham') {
      this.chiTietForm = { ...item };
    } else {
      this.danhMucForm = { ...item };
    }
    this.showEditForm = true;
  }

  delete(id: number) {
    if (confirm(`Bạn có chắc muốn xóa ${this.getTabLabel()} này?`)) {
      const endpoints = {
        sanpham: 'http://localhost:8080/api/sanpham',
        chitietsanpham: 'http://localhost:8080/api/chitietsanpham',
        danhmuc: 'http://localhost:8080/api/danhmuc'
      };
      
      this.http.delete(`${endpoints[this.activeTab as keyof typeof endpoints]}/${id}`).subscribe({
        next: () => this.loadTabData(),
        error: () => alert('Lỗi khi xóa')
      });
    }
  }

  closeForm() {
    this.showAddForm = false;
    this.showEditForm = false;
    this.currentId = null;
    this.sanPhamForm = { tenSanPham: '', moTa: '', danhMucId: null, giaCu: 0, giaMoi: 0, trangThai: true };
    this.chiTietForm = { sanPhamId: null, tenMau: '', maMau: '#000000', chatLieu: '', kichThuoc: '', soLuong: 0, hinhChinh: '' };
    this.danhMucForm = { tenDanhMuc: '', moTa: '' };
  }
}
