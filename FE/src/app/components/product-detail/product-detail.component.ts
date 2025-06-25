import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { CartService } from '../../services/cart.service';
import { WishlistService } from '../../services/wishlist.service';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [CommonModule, CurrencyPipe],
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.scss'],
})
export class ProductDetailComponent implements OnInit {
  private productService = inject(ProductService);
  private cartService = inject(CartService);
  private wishlistService = inject(WishlistService);
  private authService = inject(AuthService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  product: any;
  isLoggedIn: boolean = false;
  cartAttempted: boolean = false;

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.product = this.productService.getProductById(id);
    } else {
      console.error('ID sản phẩm không hợp lệ');
      this.router.navigate(['/products']); // Chuyển hướng về trang danh sách sản phẩm nếu id không hợp lệ
    }
    this.authService.isLoggedIn$.subscribe((status) => {
      this.isLoggedIn = status;
    });
  }

  addToCart() {
    if (this.isLoggedIn) {
      this.cartService.addToCart(this.product);
      alert('Đã thêm vào giỏ hàng!');
    } else {
      this.cartAttempted = true;
    }
  }

  addToWishlist() {
    this.wishlistService.addToWishlist(this.product);
    alert('Đã thêm vào danh sách yêu thích!');
  }
}
