import { Routes } from '@angular/router';
import { AdminOrderListComponent } from './order-list/order-list.component';
import { AdminOrderDetailComponent } from './order-detail/order-detail.component';

export const OrderManagementRoutes: Routes = [
  {
    path: '',
    component: AdminOrderListComponent,
  },
  {
    path: 'detail/:id',
    component: AdminOrderDetailComponent,
  },
];
