import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-category-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="category-list">
      <h2>Quản Lý Danh Mục</h2>
      <p>Trang quản lý danh mục đang phát triển...</p>
    </div>
  `,
  styles: [`
    .category-list {
      padding: 20px;
      background: white;
    }
    h2 { color: #ff6600; }
  `]
})
export class CategoryListComponent {
}
