import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { ApiService } from './api.service';
import { DanhMuc } from '../models/danhmuc.model';
import { ApiResponse } from '../models/api-response.model';
import { _ENDPOINTS } from '../constants/api.constants';

@Injectable({
  providedIn: 'root',
})
export class DanhMucService {
  constructor(private apiService: ApiService) {}

  // Lấy tất cả danh mục
  getAll(): Observable<DanhMuc[]> {
    return this.apiService.get<ApiResponse<DanhMuc[]>>(_ENDPOINTS.DANHMUC).pipe(
      map((response) => response.data || []),
      catchError((error) => {
        console.error('Error fetching danhmuc:', error);
        return of([]);
      })
    );
  }

  // Lấy danh mục theo ID
  getById(id: number): Observable<DanhMuc> {
    return this.apiService.get<DanhMuc>(`${_ENDPOINTS.DANHMUC}/${id}`);
  }

  // Thêm mới danh mục
  create(data: Partial<DanhMuc>): Observable<DanhMuc> {
    return this.apiService.post<DanhMuc>(_ENDPOINTS.DANHMUC, data);
  }

  // Cập nhật danh mục
  update(id: number, data: Partial<DanhMuc>): Observable<DanhMuc> {
    return this.apiService.put<DanhMuc>(`${_ENDPOINTS.DANHMUC}/${id}`, data);
  }

  // Xóa danh mục
  delete(id: number): Observable<void> {
    return this.apiService.delete<void>(`${_ENDPOINTS.DANHMUC}/${id}`);
  }
}
