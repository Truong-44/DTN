import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-features',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './features.component.html',
  styleUrls: ['./features.component.scss'],
})
export class FeaturesComponent {
  features = [
    {
      icon: 'fas fa-truck-moving',
      title: 'Giao hàng nhanh',
      desc: 'Giao hàng trong ngày cho khu vực TP.HCM',
    },
    {
      icon: 'fas fa-couch',
      title: 'Chất lượng cao',
      desc: 'Sản phẩm gỗ tự nhiên, bền đẹp và sang trọng',
    },
    {
      icon: 'fas fa-tools',
      title: 'Lắp đặt miễn phí',
      desc: 'Lắp đặt tại nhà nhanh chóng, an toàn',
    },
  ];
}
