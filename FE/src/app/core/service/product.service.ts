import { Injectable } from '@angular/core';
import { Product } from '../models/product.model';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private products: Product[] = [
    { id: 1, name: 'Sofa', price: 599.99, description: 'Comfortable modern sofa', imageUrl: '/assets/images/sofa.jpg' },
    { id: 2, name: 'Dining Table', price: 299.99, description: 'Elegant wooden dining table', imageUrl: '/assets/images/table.jpg' }
  ];

  getProducts(): Observable<Product[]> {
    return of(this.products);
  }

  getProduct(id: number): Observable<Product | undefined> {
    return of(this.products.find(p => p.id === id));
  }
}
