import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-customer-management',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './customer-management.component.html',
  styleUrls: ['./customer-management.component.scss']
})
export class CustomerManagementComponent {
  customers = [
    { id: 1, name: 'Nguyễn Văn A', email: 'nva@email.com', phone: '0123456789' },
    { id: 2, name: 'Trần Thị B', email: 'ttb@email.com', phone: '0987654321' },
    { id: 3, name: 'Lê Văn C', email: 'lvc@email.com', phone: '0369741258' }
  ];
}
