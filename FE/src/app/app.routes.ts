import { Routes } from '@angular/router';
import { SearchComponent } from './components/shared/search/search.component';
import { CartComponent } from './components/cart/cart.component';
import { adminGuard } from './core/guards/role.guard';
import { authGuard } from './core/guards/auth.guard';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { OrderListComponent } from './components/shared/order-list/order-list.component';
import { OrderDetailComponent } from './components/shared/order-detail/order-detail.component';
import { ProfileComponent } from './components/shared/profile/profile.component';
import { DebugComponent } from './components/debug/debug.component';
import { AuthDebugComponent } from './components/auth-debug/auth-debug.component';
export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./components/home/home.component').then((m) => m.HomeComponent),
  },
  { path: 'debug', component: DebugComponent },
  { path: 'auth-debug', component: AuthDebugComponent },
  { 
    path: 'debug-user', 
    loadComponent: () => import('./components/debug-user/debug-user.component').then(m => m.DebugUserComponent) 
  },
  {
    path: 'admin-debug',
    loadComponent: () =>
      import('./components/admin/admin-debug/admin-debug.component').then(
        (m) => m.AdminDebugComponent
      ),
  },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'search', component: SearchComponent },
  {
    path: 'product/:id',
    loadComponent: () =>
      import('./components/product-detail/product-detail.component').then(
        (m) => m.ProductDetailComponent
      ),
  },
  {
    path: 'products',
    loadComponent: () =>
      import('./components/product-list/product-list.component').then(
        (m) => m.ProductListComponent
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
    loadComponent: () =>
      import('./components/admin/admin.component').then((m) => m.AdminComponent),
    canActivate: [adminGuard],
    children: [
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      },
      {
        path: 'dashboard',
        loadComponent: () =>
          import('./components/admin/dashboard-new/dashboard-new').then(
            (m) => m.DashboardNew
          ),
      }
    ],
  },
  {
    path: 'admin-login',
    loadComponent: () =>
      import('./components/admin/admin-login/admin-login.component').then(
        (m) => m.AdminLoginComponent
      ),
  },
  {
    path: 'auth-debug',
    loadComponent: () =>
      import('./components/admin/auth-debug/auth-debug.component').then(
        (m) => m.AuthDebugComponent
      ),
  },
  {
    path: 'unauthorized',
    loadComponent: () =>
      import('./shared/components/unauthorized/unauthorized.component').then(
        (m) => m.UnauthorizedComponent
      ),
  },
];
