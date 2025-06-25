import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private cart: any[] = JSON.parse(localStorage.getItem('cart') || '[]');

  constructor(private authService: AuthService) {}

  addToCart(product: any) {
    if (this.authService.isLoggedIn()) {
      this.cart.push(product);
      localStorage.setItem('cart', JSON.stringify(this.cart));
    } else {
      throw new Error('Vui lòng đăng nhập để thêm vào giỏ hàng!');
    }
  }

  getCart(): any[] {
    return this.cart;
  }

  clearCart() {
    this.cart = [];
    localStorage.removeItem('cart');
  }
}
