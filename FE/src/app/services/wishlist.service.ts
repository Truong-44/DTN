import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class WishlistService {
  private wishlist: any[] = JSON.parse(
    localStorage.getItem('wishlist') || '[]'
  );

  addToWishlist(product: any) {
    const exists = this.wishlist.find((p) => p.id === product.id);
    if (!exists) {
      this.wishlist.push(product);
      localStorage.setItem('wishlist', JSON.stringify(this.wishlist));
    }
  }

  getWishlist(): any[] {
    return this.wishlist;
  }

  removeFromWishlist(productId: string) {
    this.wishlist = this.wishlist.filter((p) => p.id !== productId);
    localStorage.setItem('wishlist', JSON.stringify(this.wishlist));
  }

  clearWishlist() {
    this.wishlist = [];
    localStorage.removeItem('wishlist');
  }
}
