import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { SanPham } from '../models/sanpham.model';
import { DanhMuc } from '../models/danhmuc.model';
import { _ENDPOINTS } from '../constants/api.constants';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  constructor(private apiService: ApiService) {}

  // Lấy tất cả sản phẩm
  getAllSanPham(): Observable<SanPham[]> {
    return this.apiService.get<SanPham[]>(_ENDPOINTS.SANPHAM);
  }

  // Lấy sản phẩm theo ID
  getSanPhamById(id: number): Observable<SanPham> {
    return this.apiService.get<SanPham>(`${_ENDPOINTS.SANPHAM}/${id}`);
  }

  // Lấy tất cả danh mục
  getAllDanhMuc(): Observable<DanhMuc[]> {
    return this.apiService.get<DanhMuc[]>(_ENDPOINTS.DANHMUC);
  }
}
