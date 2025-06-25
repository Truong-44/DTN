import { Routes } from '@angular/router';
import { ProductListComponent } from './components/product-list/product-list.component';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';
// import { CartComponent } from './components/cart/cart.component';
// import { WishlistComponent } from './components/wishlist/wishlist.component';
// import { LoginComponent } from './components/login/login.component';
// import { RegisterComponent } from './components/register/register.component';

export const routes: Routes = [
  { path: 'products', component: ProductListComponent },
  { path: 'product/:id', component: ProductDetailComponent },
  // { path: 'cart', component: CartComponent },
  // { path: 'wishlist', component: WishlistComponent },
  // { path: 'login', component: LoginComponent },
  // { path: 'register', component: RegisterComponent },
  { path: '', redirectTo: '/products', pathMatch: 'full' },
];
