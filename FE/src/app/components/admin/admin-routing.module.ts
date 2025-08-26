import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProductListComponent } from './product-management/product-list/product-list.component';
import { CategoryListComponent } from './category-management/category-list/category-list.component';
import { CustomerListComponent } from './customer-management/customer-list/customer-list.component';
import { CustomerDetailComponent } from './customer-management/customer-detail/customer-detail.component';
import { CustomerEditComponent } from './customer-management/customer-edit/customer-edit.component';
import { AdminLoginComponent } from './admin-login/admin-login.component';
import { AdminOrderListComponent } from './order-management/order-list/order-list.component';
import { AdminOrderDetailComponent } from './order-management/order-detail/order-detail.component';
import { EmployeeListComponent } from './employee-management/employee-list/employee-list.component';

const routes: Routes = [
  {
    path: 'login',
    component: AdminLoginComponent,
  },
  {
    path: '',
    component: AdminComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'product-management', component: ProductListComponent },
      { path: 'category-management', component: CategoryListComponent },
      { path: 'customer-management', component: CustomerListComponent },
      {
        path: 'customer-management/detail/:id',
        component: CustomerDetailComponent,
      },
      {
        path: 'customer-management/edit/:id',
        component: CustomerEditComponent,
      },
      { path: 'customer-management/add', component: CustomerEditComponent },
      { path: 'order-management', component: AdminOrderListComponent },
      {
        path: 'order-management/detail/:id',
        component: AdminOrderDetailComponent,
      },
      { path: 'employee-management', component: EmployeeListComponent },
      {
        path: 'invoice-management',
        loadComponent: () =>
          import(
            './invoice-management/invoice-list/invoice-list.component'
          ).then((m) => m.InvoiceListComponent),
      },
      {
        path: 'shipping-management',
        loadComponent: () =>
          import(
            './shipping-management/shipping-list/shipping-list.component'
          ).then((m) => m.ShippingListComponent),
      },
      {
        path: 'stock-management',
        loadComponent: () =>
          import('./stock-management/stock-list/stock-list.component').then(
            (m) => m.StockListComponent
          ),
      },
      {
        path: 'permission-management',
        loadComponent: () =>
          import(
            './permission-management/permission-list/permission-list.component'
          ).then((m) => m.PermissionListComponent),
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule {}
