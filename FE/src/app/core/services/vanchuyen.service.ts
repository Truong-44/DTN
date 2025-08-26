import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { ApiService } from './api.service';
import { PhuongThucVanChuyen } from '../models/phuongthucvanchuyen.model';
import { _ENDPOINTS } from '../constants/api.constants';

@Injectable({
  providedIn: 'root',
})
export class VanChuyenService {
  constructor(private apiService: ApiService) {}

  // ===================== PHƯƠNG THỨC VẬN CHUYỂN =====================
  // GET /api/vanchuyen
  getAllVanChuyen(): Observable<PhuongThucVanChuyen[]> {
    return this.apiService.get<PhuongThucVanChuyen[]>(_ENDPOINTS.VANCHUYEN);
  }

  // GET /api/vanchuyen/{id}
  getVanChuyenById(id: number): Observable<PhuongThucVanChuyen> {
    return this.apiService.get<PhuongThucVanChuyen>(
      `${_ENDPOINTS.VANCHUYEN}/${id}`
    );
  }

  // POST /api/vanchuyen
  createVanChuyen(
    vanchuyen: Partial<PhuongThucVanChuyen>
  ): Observable<PhuongThucVanChuyen> {
    return this.apiService.post<PhuongThucVanChuyen>(
      _ENDPOINTS.VANCHUYEN,
      vanchuyen
    );
  }

  // DELETE /api/vanchuyen/{id}
  deleteVanChuyen(id: number): Observable<void> {
    return this.apiService.delete<void>(`${_ENDPOINTS.VANCHUYEN}/${id}`);
  }

  // ===================== TÍNH PHÍCH VẬN CHUYỂN =====================
  calculateShippingFee(
    vanchuyenId: number,
    weight: number,
    distance?: number,
    address?: string
  ): Observable<{ fee: number; estimatedTime: string }> {
    const params = {
      weight: weight.toString(),
      ...(distance && { distance: distance.toString() }),
      ...(address && { address }),
    };

    return this.apiService.post<{ fee: number; estimatedTime: string }>(
      `${_ENDPOINTS.VANCHUYEN}/${vanchuyenId}/calculate-fee`,
      params
    );
  }

  // Lấy danh sách phương thức vận chuyển có sẵn cho một địa chỉ
  getAvailableShippingMethods(
    address: string
  ): Observable<PhuongThucVanChuyen[]> {
    const params = new HttpParams().set('address', address);
    return this.apiService.get<PhuongThucVanChuyen[]>(
      `${_ENDPOINTS.VANCHUYEN}/available`,
      params
    );
  }
}

