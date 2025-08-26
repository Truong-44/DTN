import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { ApiService } from './api.service';
import { SanPham } from '../models/sanpham.model';
import { _ENDPOINTS } from '../constants/api.constants';

@Injectable({
  providedIn: 'root',
})
export class SearchService {
  constructor(private apiService: ApiService) {}

  // Tìm kiếm sản phẩm sử dụng endpoint backend (không phân biệt dấu, khoảng trắng, hoa/thường)
  searchProducts(
    keyword: string,
    page: number = 0,
    size: number = 20
  ): Observable<{ products: SanPham[]; total: number }> {
    let params = new HttpParams()
      .set('keyword', keyword)
      .set('page', page)
      .set('size', size);

    return this.apiService.get<any>('/api/sanpham/search/page', params).pipe(
      map((res) => ({
        products: res.content || [],
        total: res.totalElements || 0,
      }))
    );
  }

  // Gợi ý tìm kiếm (lấy 5 sản phẩm đầu tiên phù hợp)
  getSearchSuggestions(keyword: string): Observable<SanPham[]> {
    let params = new HttpParams()
      .set('keyword', keyword)
      .set('page', '0')
      .set('size', '5');

    return this.apiService
      .get<any>('/api/sanpham/search/page', params)
      .pipe(map((res) => res.content || []));
  }
}
