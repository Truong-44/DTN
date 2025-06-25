import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChatboxComponent } from '../chatbox/chatbox.component';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, ChatboxComponent],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss'],
})
export class ProductListComponent {
  products = [
    {
      name: 'Ghế Sofa Cao Cấp',
      description: 'Thiết kế sang trọng, bọc nệm cao cấp',
      image: '/assets/products/sofa.jpg',
    },
    {
      name: 'Giường Gỗ Tự Nhiên',
      description: 'Chất liệu gỗ sồi bền đẹp theo năm tháng',
      image: '/assets/products/giuong.jpg',
    },
    {
      name: 'Tủ Quần Áo Đa Năng',
      description: 'Thiết kế thông minh tiết kiệm diện tích',
      image: '/assets/products/tu.jpg',
    },
  ];
}
