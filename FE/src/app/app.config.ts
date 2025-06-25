import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { provideHttpClient } from '@angular/common/http';
import { ProductService } from './services/product.service';
import { CartService } from './services/cart.service';
import { WishlistService } from './services/wishlist.service';
import { AuthService } from './services/auth.service';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    ProductService,
    CartService,
    WishlistService,
    AuthService,
  ],
};
