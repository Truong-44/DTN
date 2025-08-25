import { Routes } from '@angular/router';
import { SearchComponent } from './components/shared/search/search.component';
import { CartComponent } from './components/cart/cart.component';
import { AdminGuard } from './components/shared/guards/admin.guard';
import { authGuard } from './core/guards/auth.guard';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { ProductListComponent } from './components/product-list/product-list.component';
import { OrderListComponent } from './components/shared/order-list/order-list.component';
import { OrderDetailComponent } from './components/shared/order-detail/order-detail.component';
import { ProfileComponent } from './components/shared/profile/profile.component';
import { DebugComponent } from './components/debug/debug.component';
export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./components/home/home.component').then((m) => m.HomeComponent),
  },
  { path: 'debug', component: DebugComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'search', component: SearchComponent },
  { path: 'products-list', component: ProductListComponent },
  {
    path: 'product/:id',
    loadComponent: () =>
      import('./components/product-detail/product-detail.component').then(
        (m) => m.ProductDetailComponent
      ),
  },
  { path: 'cart', component: CartComponent, canActivate: [authGuard] },
  { path: 'giohang', component: CartComponent, canActivate: [authGuard] },
  {
    path: 'checkout',
    loadComponent: () =>
      import('./components/checkout/checkout.component').then(
        (m) => m.CheckoutComponent
      ),
    canActivate: [authGuard],
  },
  // {
  //   path: 'order-success',
  //   loadComponent: () =>
  //     import('./components/order-success/order-success.component').then(
  //       (m) => m.OrderSuccessComponent
  //     ),
  //   canActivate: [authGuard],
  // },
  {
    path: 'order-list',
    component: OrderListComponent,
  },
  {
    path: 'order-detail/:id',
    component: OrderDetailComponent,
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [authGuard],
  },

  // Admin routes
  {
    path: 'admin',
    children: [
      {
        path: 'login',
        loadComponent: () =>
          import('./components/admin/admin-login/admin-login.component').then(
            (m) => m.AdminLoginComponent
          ),
      },
      {
        path: '',
        loadComponent: () =>
          import('./components/admin/admin.component').then(
            (m) => m.AdminComponent
          ),
        children: [
          { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
          {
            path: 'dashboard',
            loadComponent: () =>
              import('./components/admin/dashboard/dashboard.component').then(
                (m) => m.DashboardComponent
              ),
          },
          {
            path: 'category-management',
            loadComponent: () =>
              import(
                './components/admin/category-management/category-list/category-list.component'
              ).then((m) => m.CategoryListComponent),
          },
          {
            path: 'product-management',
            loadComponent: () =>
              import(
                './components/admin/product-management/product-list/product-list.component'
              ).then((m) => m.ProductListComponent),
          },
          {
            path: 'customer-management',
            loadComponent: () =>
              import(
                './components/admin/customer-management/customer-list/customer-list.component'
              ).then((m) => m.CustomerListComponent),
          },
          {
            path: 'order-management',
            loadComponent: () =>
              import(
                './components/admin/order-management/order-list/order-list.component'
              ).then((m) => m.AdminOrderListComponent),
          },
          {
            path: 'order-management/detail/:id',
            loadComponent: () =>
              import(
                './components/admin/order-management/order-detail/order-detail.component'
              ).then((m) => m.AdminOrderDetailComponent),
          },
          {
            path: 'employee-management',
            loadComponent: () =>
              import(
                './components/admin/employee-management/employee-list/employee-list.component'
              ).then((m) => m.EmployeeListComponent),
          },
          {
            path: 'invoice-management',
            loadComponent: () =>
              import(
                './components/admin/invoice-management/invoice-list/invoice-list.component'
              ).then((m) => m.InvoiceListComponent),
          },
          {
            path: 'shipping-management',
            loadComponent: () =>
              import(
                './components/admin/shipping-management/shipping-list/shipping-list.component'
              ).then((m) => m.ShippingListComponent),
          },
          {
            path: 'stock-management',
            loadComponent: () =>
              import(
                './components/admin/stock-management/stock-list/stock-list.component'
              ).then((m) => m.StockListComponent),
          },
          {
            path: 'permission-management',
            loadComponent: () =>
              import(
                './components/admin/permission-management/permission-list/permission-list.component'
              ).then((m) => m.PermissionListComponent),
          },
        ],
        // canActivate: [AdminGuard]
      },
    ],
  },
];
