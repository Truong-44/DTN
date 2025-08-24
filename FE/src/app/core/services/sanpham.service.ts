// core/services/sanpham.service.ts
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { ApiService } from './api.service';
import { CacheService } from './cache.service';
import { SanPham } from '../models/sanpham.model';
import { ChiTietSanPham } from '../models/chitietsanpham.model';
import { DanhMuc } from '../models/danhmuc.model';
import { KhoHang } from '../models/khohang.model';
import { ApiResponse } from '../models/api-response.model';
import {
  PaginationRequest,
  PaginationResponse,
} from '../models/pagination.model';
import { _ENDPOINTS } from '../constants/api.constants';

@Injectable({
  providedIn: 'root',
})
export class SanPhamService {
  constructor(
    private apiService: ApiService,
    private cacheService: CacheService
  ) {}

  // ===================== SẢN PHẨM =====================
  // GET /api/sanpham - Lấy tất cả sản phẩm (backend trả về ApiResponse<List<SanPhamDTO>>)
  getAllSanPham(): Observable<SanPham[]> {
    const cacheKey = CacheService.CACHE_KEYS.SANPHAM.ALL;

    return this.cacheService.cacheObservable(
      cacheKey,
      this.apiService.get<ApiResponse<SanPham[]>>(_ENDPOINTS.SANPHAM).pipe(
        map((response) => response.data || []),
        catchError((error) => {
          console.error('Error fetching sanpham:', error);
          return of([]);
        })
      ),
      10 * 60 * 1000 // Cache for 10 minutes
    );
  }

  // Alias method for compatibility
  getAllSanPhamArray(): Observable<SanPham[]> {
    return this.getAllSanPham();
  }

  // GET /api/sanpham với pagination parameters (nếu backend hỗ trợ)
  getAllSanPhamWithPagination(
    pagination?: PaginationRequest
  ): Observable<SanPham[]> {
    let params = new HttpParams();

    if (pagination) {
      params = params.set('page', pagination.page.toString());
      params = params.set('size', pagination.size.toString());
      if (pagination.sort) {
        params = params.set('sort', pagination.sort);
        params = params.set('direction', pagination.direction || 'asc');
      }
    }

    return this.apiService.get<SanPham[]>(_ENDPOINTS.SANPHAM, params).pipe(
      catchError((error) => {
        console.error('Error fetching sanpham with pagination:', error);
        return of([]);
      })
    );
  }

  // GET /api/sanpham/{id}
  getSanPhamById(id: number): Observable<SanPham> {
    const cacheKey = CacheService.CACHE_KEYS.SANPHAM.BY_ID(id);

    return this.cacheService.cacheObservable(
      cacheKey,
      this.apiService.get<SanPham>(`${_ENDPOINTS.SANPHAM}/${id}`),
      5 * 60 * 1000 // Cache for 5 minutes
    );
  }

  // POST /api/sanpham
  createSanPham(sanpham: Partial<SanPham>): Observable<SanPham> {
    return this.apiService.post<SanPham>(_ENDPOINTS.SANPHAM, sanpham);
  }

  // PUT /api/sanpham/{id}
  updateSanPham(id: number, sanpham: Partial<SanPham>): Observable<SanPham> {
    return this.apiService.put<SanPham>(`${_ENDPOINTS.SANPHAM}/${id}`, sanpham);
  }

  // DELETE /api/sanpham/{id}
  deleteSanPham(id: number): Observable<void> {
    return this.apiService.delete<void>(`${_ENDPOINTS.SANPHAM}/${id}`);
  }

  // GET sản phẩm với thông tin đầy đủ bao gồm chi tiết
  getFullInfo(sanphamId: number): Observable<SanPham> {
    const endpoint = _ENDPOINTS.SPECIFIC.SANPHAM_FULL_INFO.replace(
      '{id}',
      sanphamId.toString()
    );
    return this.apiService.get<SanPham>(endpoint).pipe(
      catchError((error) => {
        console.error('Error fetching full product info:', error);
        return of({} as SanPham);
      })
    );
  }

  // GET /api/sanpham/search
  searchSanPham(keyword: string): Observable<SanPham[]> {
    let params = new HttpParams().set('keyword', keyword);
    return this.apiService
      .get<ApiResponse<SanPham[]>>(_ENDPOINTS.SPECIFIC.SANPHAM_SEARCH, params)
      .pipe(
        map((response) => response.data || []),
        catchError((error) => {
          console.error('Error searching products:', error);
          return of([]);
        })
      );
  }

  // GET /api/sanpham/search/page - Search with pagination
  searchSanPhamPaged(
    keyword: string,
    pagination?: PaginationRequest
  ): Observable<PaginationResponse<SanPham>> {
    let params = new HttpParams().set('keyword', keyword);

    if (pagination) {
      params = params.set('page', pagination.page.toString());
      params = params.set('size', pagination.size.toString());
      if (pagination.sort) {
        params = params.set('sort', pagination.sort);
        params = params.set('direction', pagination.direction || 'asc');
      }
    }

    return this.apiService
      .get<PaginationResponse<SanPham>>(
        _ENDPOINTS.SPECIFIC.SANPHAM_SEARCH_PAGED,
        params
      )
      .pipe(
        catchError((error) => {
          console.error('Error searching products with pagination:', error);
          return of({
            content: [],
            totalElements: 0,
            totalPages: 0,
            number: 0,
            size: 0,
            first: true,
            last: true,
          } as PaginationResponse<SanPham>);
        })
      );
  }

  // GET /api/sanpham/page - Get products with pagination
  getAllSanPhamPaged(
    pagination?: PaginationRequest
  ): Observable<PaginationResponse<SanPham>> {
    let params = new HttpParams();

    if (pagination) {
      params = params.set('page', pagination.page.toString());
      params = params.set('size', pagination.size.toString());
      if (pagination.sort) {
        params = params.set('sort', pagination.sort);
        params = params.set('direction', pagination.direction || 'asc');
      }
    }

    return this.apiService
      .get<PaginationResponse<SanPham>>(
        _ENDPOINTS.SPECIFIC.SANPHAM_PAGED,
        params
      )
      .pipe(
        catchError((error) => {
          console.error('Error fetching products with pagination:', error);
          return of({
            content: [],
            totalElements: 0,
            totalPages: 0,
            number: 0,
            size: 0,
            first: true,
            last: true,
          } as PaginationResponse<SanPham>);
        })
      );
  }

  // GET /api/sanpham/danhmuc/{danhMucId}
  getSanPhamByDanhMuc(danhMucId: number): Observable<SanPham[]> {
    const endpoint = _ENDPOINTS.SPECIFIC.SANPHAM_BY_CATEGORY.replace(
      '{danhMucId}',
      danhMucId.toString()
    );
    return this.apiService.get<SanPham[]>(endpoint).pipe(
      catchError((error) => {
        console.error('Error fetching products by category:', error);
        return of([]);
      })
    );
  }

  // PATCH /api/sanpham/{id}/status
  updateSanPhamStatus(id: number, status: boolean): Observable<SanPham> {
    const endpoint = _ENDPOINTS.SPECIFIC.SANPHAM_UPDATE_STATUS.replace(
      '{id}',
      id.toString()
    );
    return this.apiService.patch<SanPham>(endpoint, { trangthai: status });
  }

  // ===================== CHI TIẾT SẢN PHẨM =====================
  // GET /api/chitietsanpham
  getAllChiTietSanPham(): Observable<ChiTietSanPham[]> {
    return this.apiService
      .get<ApiResponse<ChiTietSanPham[]>>(_ENDPOINTS.CHITIETSANPHAM)
      .pipe(
        map((response) => response.data || []),
        catchError((error) => {
          console.error('Error fetching chitietsanpham:', error);
          return of([]);
        })
      );
  }

  // GET /api/chitietsanpham/{id}
  getChiTietSanPhamById(id: number): Observable<ChiTietSanPham> {
    return this.apiService
      .get<ChiTietSanPham>(`${_ENDPOINTS.CHITIETSANPHAM}/${id}`)
      .pipe(
        catchError((error) => {
          console.error('Error fetching chitietsanpham by id:', error);
          return of({} as ChiTietSanPham);
        })
      );
  }

  // GET chi tiết sản phẩm theo sanpham id - Correct endpoint from backend analysis
  getChiTietSanPhamBySanPhamId(
    sanphamId: number
  ): Observable<ChiTietSanPham[]> {
    const endpoint = `${_ENDPOINTS.CHITIETSANPHAM}/sanpham/${sanphamId}`;
    return this.apiService.get<ApiResponse<ChiTietSanPham[]>>(endpoint).pipe(
      map((response) => response.data || []),
      catchError((error) => {
        console.error('Error fetching chitietsanpham by sanpham id:', error);
        return of([]);
      })
    );
  }

  // GET chi tiết sản phẩm theo filter
  getChiTietSanPhamWithFilter(
    sanphamId: number,
    filters?: { mauSac?: string; kichThuoc?: string }
  ): Observable<ChiTietSanPham[]> {
    const endpoint = _ENDPOINTS.SPECIFIC.CHITIETSANPHAM_FILTER.replace(
      '{sanPhamId}',
      sanphamId.toString()
    );
    let params = new HttpParams();

    if (filters?.mauSac) params = params.set('mauSac', filters.mauSac);
    if (filters?.kichThuoc) params = params.set('kichThuoc', filters.kichThuoc);

    return this.apiService.get<ChiTietSanPham[]>(endpoint, params).pipe(
      catchError((error) => {
        console.error('Error fetching filtered chitietsanpham:', error);
        return of([]);
      })
    );
  }

  // GET chi tiết sản phẩm theo màu sắc
  getChiTietSanPhamByMau(
    sanphamId: number,
    mauSac: string
  ): Observable<ChiTietSanPham[]> {
    const endpoint =
      _ENDPOINTS.SPECIFIC.CHITIETSANPHAM_BY_SANPHAM_AND_MAU.replace(
        '{sanPhamId}',
        sanphamId.toString()
      ).replace('{mauSac}', mauSac);
    return this.apiService.get<ChiTietSanPham[]>(endpoint).pipe(
      catchError((error) => {
        console.error('Error fetching chitietsanpham by color:', error);
        return of([]);
      })
    );
  }

  // GET chi tiết sản phẩm theo kích thước
  getChiTietSanPhamBySize(
    sanphamId: number,
    kichThuoc: string
  ): Observable<ChiTietSanPham[]> {
    const endpoint =
      _ENDPOINTS.SPECIFIC.CHITIETSANPHAM_BY_SANPHAM_AND_SIZE.replace(
        '{sanPhamId}',
        sanphamId.toString()
      ).replace('{kichThuoc}', kichThuoc);
    return this.apiService.get<ChiTietSanPham[]>(endpoint).pipe(
      catchError((error) => {
        console.error('Error fetching chitietsanpham by size:', error);
        return of([]);
      })
    );
  }

  // POST /api/chitietsanpham
  createChiTietSanPham(
    chitiet: Partial<ChiTietSanPham>
  ): Observable<ChiTietSanPham> {
    return this.apiService.post<ChiTietSanPham>(
      _ENDPOINTS.CHITIETSANPHAM,
      chitiet
    );
  }

  // PUT /api/chitietsanpham/{id}
  updateChiTietSanPham(
    id: number,
    chitiet: Partial<ChiTietSanPham>
  ): Observable<ChiTietSanPham> {
    return this.apiService.put<ChiTietSanPham>(
      `${_ENDPOINTS.CHITIETSANPHAM}/${id}`,
      chitiet
    );
  }

  // DELETE /api/chitietsanpham/{id}
  deleteChiTietSanPham(id: number): Observable<void> {
    return this.apiService.delete<void>(`${_ENDPOINTS.CHITIETSANPHAM}/${id}`);
  }

  // ===================== DANH MỤC =====================
  // GET /api/danhmuc
  getAllDanhMuc(): Observable<DanhMuc[]> {
    return this.apiService.get<DanhMuc[]>(_ENDPOINTS.DANHMUC);
  }

  // GET /api/danhmuc/{id}
  getDanhMucById(id: number): Observable<DanhMuc> {
    return this.apiService.get<DanhMuc>(`${_ENDPOINTS.DANHMUC}/${id}`);
  }

  // POST /api/danhmuc
  createDanhMuc(danhmuc: Partial<DanhMuc>): Observable<DanhMuc> {
    return this.apiService.post<DanhMuc>(_ENDPOINTS.DANHMUC, danhmuc);
  }

  // PUT /api/danhmuc/{id}
  updateDanhMuc(id: number, danhmuc: Partial<DanhMuc>): Observable<DanhMuc> {
    return this.apiService.put<DanhMuc>(`${_ENDPOINTS.DANHMUC}/${id}`, danhmuc);
  }

  // DELETE /api/danhmuc/{id}
  deleteDanhMuc(id: number): Observable<void> {
    return this.apiService.delete<void>(`${_ENDPOINTS.DANHMUC}/${id}`);
  }

  // ===================== KHO HÀNG =====================
  // GET /api/khohang
  getAllKhoHang(): Observable<KhoHang[]> {
    return this.apiService.get<KhoHang[]>(_ENDPOINTS.KHOHANG);
  }

  // GET /api/khohang/sanpham/{ctspId}
  getKhoHangByChiTietSanPhamId(ctspId: number): Observable<KhoHang> {
    return this.apiService.get<KhoHang>(
      `${_ENDPOINTS.KHOHANG}/sanpham/${ctspId}`
    );
  }

  // POST /api/khohang
  createOrUpdateKhoHang(khohang: Partial<KhoHang>): Observable<KhoHang> {
    return this.apiService.post<KhoHang>(_ENDPOINTS.KHOHANG, khohang);
  }

  // DELETE /api/khohang/{id}
  deleteKhoHang(id: number): Observable<void> {
    return this.apiService.delete<void>(`${_ENDPOINTS.KHOHANG}/${id}`);
  }

  // ===================== UPLOAD IMAGES =====================
  uploadProductImage(file: File, sanphamId?: number): Observable<any> {
    const additionalData = sanphamId
      ? { sanphamId: sanphamId.toString() }
      : undefined;
    return this.apiService.uploadFile(
      'upload/product-image',
      file,
      additionalData
    );
  }

  // GET available colors for a product
  getAvailableColors(sanphamId: number): Observable<string[]> {
    const endpoint = _ENDPOINTS.SPECIFIC.SANPHAM_AVAILABLE_COLORS.replace(
      '{id}',
      sanphamId.toString()
    );
    return this.apiService.get<string[]>(endpoint).pipe(
      catchError((error) => {
        console.error('Error fetching available colors:', error);
        return of([]);
      })
    );
  }

  // GET available sizes for a product
  getAvailableSizes(sanphamId: number): Observable<string[]> {
    const endpoint = _ENDPOINTS.SPECIFIC.SANPHAM_AVAILABLE_SIZES.replace(
      '{id}',
      sanphamId.toString()
    );
    return this.apiService.get<string[]>(endpoint).pipe(
      catchError((error) => {
        console.error('Error fetching available sizes:', error);
        return of([]);
      })
    );
  }

  getStockByChiTietSanPham(ctspId: number): Observable<KhoHang> {
    const url = _ENDPOINTS.SPECIFIC.KHOHANG_BY_CHITIETSANPHAM.replace(
      '{ctspId}',
      ctspId.toString()
    );
    return this.apiService.get<KhoHang>(url);
  }
}
