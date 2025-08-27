import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-management',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="product-management">
      <h2>Quản Lý Sản Phẩm</h2>
      <p>Trang quản lý sản phẩm đang phát triển...</p>
    </div>
  `,
  styles: [`
    .product-management {
      padding: 20px;
      background: white;
    }
    h2 { color: #ff6600; }
  `]
})
export class ProductManagementComponent {
}
