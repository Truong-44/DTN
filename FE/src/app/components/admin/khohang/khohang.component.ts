import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-khohang',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="page-container">
      <div class="page-header">
        <h2>🏪 Quản lý kho hàng</h2>
        <button class="btn-primary" (click)="showAddForm = true">+ Nhập kho</button>
      </div>

      <!-- Form Modal -->
      <div class="form-modal" *ngIf="showAddForm || showEditForm">
        <div class="form-content">
          <h3>{{ showEditForm ? 'Sửa' : 'Nhập' }} kho hàng</h3>
          <form (ngSubmit)="saveKhoHang()">
            <div class="form-row">
              <div class="form-group">
                <label>Sản phẩm:</label>
                <select [(ngModel)]="formData.sanPhamId" name="sanPhamId" required>
                  <option value="">Chọn sản phẩm</option>
                  <option *ngFor="let sp of sanPhamList" [value]="sp.id">{{ sp.tenSanPham }}</option>
                </select>
              </div>
              <div class="form-group">
                <label>Số lượng:</label>
                <input type="number" [(ngModel)]="formData.soLuong" name="soLuong" required>
              </div>
            </div>
            <div class="form-row">
              <div class="form-group">
                <label>Giá nhập:</label>
                <input type="number" [(ngModel)]="formData.giaNhap" name="giaNhap" required>
              </div>
              <div class="form-group">
                <label>Ngày nhập:</label>
                <input type="datetime-local" [(ngModel)]="formData.ngayNhap" name="ngayNhap" required>
              </div>
            </div>
            <div class="form-group">
              <label>Ghi chú:</label>
              <textarea [(ngModel)]="formData.ghiChu" name="ghiChu" rows="3"></textarea>
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
              <th>Sản phẩm</th>
              <th>Số lượng</th>
              <th>Số lượng còn</th>
              <th>Giá nhập</th>
              <th>Ngày nhập</th>
              <th>Ghi chú</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let item of khoHangList">
              <td>{{ item.id }}</td>
              <td>{{ getSanPhamName(item.sanPhamId) }}</td>
              <td>{{ item.soLuong }}</td>
              <td>{{ item.soLuongCon }}</td>
              <td>{{ item.giaNhap | number }} VNĐ</td>
              <td>{{ item.ngayNhap | date:'dd/MM/yyyy HH:mm' }}</td>
              <td>{{ item.ghiChu }}</td>
              <td>
                <button class="btn-edit" (click)="editKhoHang(item)">Sửa</button>
                <button class="btn-delete" (click)="deleteKhoHang(item.id)">Xóa</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Inventory Summary -->
      <div class="inventory-summary">
        <h3>Tồn kho hiện tại</h3>
        <div class="summary-grid">
          <div class="summary-card" *ngFor="let item of inventorySummary">
            <h4>{{ item.tenSanPham }}</h4>
            <p class="stock-number">{{ item.tongTon }}</p>
            <p class="stock-value">Giá trị: {{ item.giaTriTon | number }} VNĐ</p>
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
    
    .inventory-summary {
      border-top: 2px solid #ff6600;
      padding-top: 20px;
    }
    
    .inventory-summary h3 {
      color: #ff6600;
      margin: 0 0 15px 0;
    }
    
    .summary-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
      gap: 15px;
    }
    
    .summary-card {
      background: #f8f9fa;
      border: 1px solid #ddd;
      border-radius: 8px;
      padding: 15px;
      text-align: center;
    }
    
    .summary-card h4 {
      color: #ff6600;
      margin: 0 0 10px 0;
      font-size: 0.9rem;
    }
    
    .stock-number {
      font-size: 1.5rem;
      font-weight: bold;
      color: #333;
      margin: 5px 0;
    }
    
    .stock-value {
      color: #666;
      font-size: 0.8rem;
      margin: 0;
    }
  `]
})
export class KhoHangComponent implements OnInit {
  khoHangList: any[] = [];
  sanPhamList: any[] = [];
  inventorySummary: any[] = [];
  showAddForm = false;
  showEditForm = false;
  currentId: number | null = null;
  
  formData = {
    sanPhamId: null,
    soLuong: 0,
    giaNhap: 0,
    ngayNhap: '',
    ghiChu: ''
  };

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadData();
    this.loadSanPham();
    this.loadInventorySummary();
  }

  loadData() {
    this.http.get<any>('http://localhost:8080/api/khohang').subscribe({
      next: (response) => {
        this.khoHangList = response.data || response;
      },
      error: () => {
        this.khoHangList = [
          { id: 1, sanPhamId: 1, soLuong: 50, soLuongCon: 30, giaNhap: 4000000, ngayNhap: '2025-08-20T10:00:00', ghiChu: 'Nhập đợt 1' },
          { id: 2, sanPhamId: 2, soLuong: 20, soLuongCon: 15, giaNhap: 6000000, ngayNhap: '2025-08-22T14:30:00', ghiChu: 'Nhập từ nhà cung cấp A' }
        ];
      }
    });
  }

  loadSanPham() {
    this.http.get<any>('http://localhost:8080/api/sanpham').subscribe({
      next: (response) => {
        this.sanPhamList = response.data || response;
      },
      error: () => {
        this.sanPhamList = [
          { id: 1, tenSanPham: 'Bàn ăn gỗ sồi' },
          { id: 2, tenSanPham: 'Ghế sofa 3 chỗ' }
        ];
      }
    });
  }

  loadInventorySummary() {
    this.http.get<any>('http://localhost:8080/api/khohang/summary').subscribe({
      next: (response) => {
        this.inventorySummary = response.data || response;
      },
      error: () => {
        this.inventorySummary = [
          { tenSanPham: 'Bàn ăn gỗ sồi', tongTon: 30, giaTriTon: 120000000 },
          { tenSanPham: 'Ghế sofa 3 chỗ', tongTon: 15, giaTriTon: 90000000 },
          { tenSanPham: 'Tủ quần áo', tongTon: 8, giaTriTon: 64000000 }
        ];
      }
    });
  }

  getSanPhamName(id: any) {
    const sp = this.sanPhamList.find(s => s.id == id);
    return sp?.tenSanPham || 'Không xác định';
  }

  saveKhoHang() {
    if (this.showEditForm && this.currentId) {
      this.http.put(`http://localhost:8080/api/khohang/${this.currentId}`, this.formData).subscribe({
        next: () => {
          this.loadData();
          this.loadInventorySummary();
          this.closeForm();
        },
        error: () => alert('Lỗi khi cập nhật kho hàng')
      });
    } else {
      this.http.post('http://localhost:8080/api/khohang', this.formData).subscribe({
        next: () => {
          this.loadData();
          this.loadInventorySummary();
          this.closeForm();
        },
        error: () => alert('Lỗi khi nhập kho')
      });
    }
  }

  editKhoHang(item: any) {
    this.currentId = item.id;
    this.formData = { ...item };
    this.showEditForm = true;
  }

  deleteKhoHang(id: number) {
    if (confirm('Bạn có chắc muốn xóa bản ghi kho này?')) {
      this.http.delete(`http://localhost:8080/api/khohang/${id}`).subscribe({
        next: () => {
          this.loadData();
          this.loadInventorySummary();
        },
        error: () => alert('Lỗi khi xóa')
      });
    }
  }

  closeForm() {
    this.showAddForm = false;
    this.showEditForm = false;
    this.currentId = null;
    this.formData = {
      sanPhamId: null,
      soLuong: 0,
      giaNhap: 0,
      ngayNhap: '',
      ghiChu: ''
    };
  }
}
