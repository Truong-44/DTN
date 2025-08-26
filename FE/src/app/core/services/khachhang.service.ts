import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { ApiService } from './api.service';
import { CacheService } from './cache.service';
import { KhachHang } from '../models/khachhang.model';
import { ApiResponse } from '../models/api-response.model';
import { _ENDPOINTS } from '../constants/api.constants';

@Injectable({
  providedIn: 'root',
})
export class KhachHangService {
  constructor(
    private apiService: ApiService,
    private cacheService: CacheService
  ) {}

  // GET /api/khachhang - Lấy tất cả khách hàng
  getAll(): Observable<KhachHang[]> {
    const cacheKey = CacheService.CACHE_KEYS.KHACHHANG?.ALL || 'khachhang_all';

    return this.cacheService.cacheObservable(
      cacheKey,
      this.apiService.get<ApiResponse<KhachHang[]>>(_ENDPOINTS.KHACHHANG).pipe(
        map((response) => response.data || []),
        catchError((error) => {
          console.error('Error fetching khachhang:', error);
          return of([]);
        })
      ),
      5 * 60 * 1000 // Cache for 5 minutes
    );
  }

  // GET /api/khachhang/{id}
  getById(id: number): Observable<KhachHang> {
    return this.apiService
      .get<ApiResponse<KhachHang>>(`${_ENDPOINTS.KHACHHANG}/${id}`)
      .pipe(
        map((response) => response.data),
        catchError((error) => {
          console.error('Error fetching khachhang by id:', error);
          throw error;
        })
      );
  }

  // GET /api/khachhang/search?name={name}
  searchByName(name: string): Observable<KhachHang[]> {
    return this.apiService
      .get<ApiResponse<KhachHang[]>>(
        `${_ENDPOINTS.KHACHHANG}/search`,
        new URLSearchParams({ name }).toString() as any
      )
      .pipe(
        map((response) => response.data || []),
        catchError((error) => {
          console.error('Error searching khachhang:', error);
          return of([]);
        })
      );
  }

  // POST /api/khachhang
  create(khachhang: Partial<KhachHang>): Observable<KhachHang> {
    return this.apiService
      .post<ApiResponse<KhachHang>>(_ENDPOINTS.KHACHHANG, khachhang)
      .pipe(
        map((response) => response.data),
        catchError((error) => {
          console.error('Error creating khachhang:', error);
          throw error;
        })
      );
  }

  // PUT /api/khachhang/{id}
  update(id: number, khachhang: Partial<KhachHang>): Observable<KhachHang> {
    return this.apiService
      .put<ApiResponse<KhachHang>>(`${_ENDPOINTS.KHACHHANG}/${id}`, khachhang)
      .pipe(
        map((response) => response.data),
        catchError((error) => {
          console.error('Error updating khachhang:', error);
          throw error;
        })
      );
  }

  // DELETE /api/khachhang/{id}
  delete(id: number): Observable<void> {
    return this.apiService
      .delete<ApiResponse<void>>(`${_ENDPOINTS.KHACHHANG}/${id}`)
      .pipe(
        map((response) => response.data),
        catchError((error) => {
          console.error('Error deleting khachhang:', error);
          throw error;
        })
      );
  }
}
