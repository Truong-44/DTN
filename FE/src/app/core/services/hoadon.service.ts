import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { ApiService } from './api.service';
import { HoaDon } from '../models/hoadon.model';
import { ChiTietHoaDon } from '../models/chitiethoadon.model';
import { _ENDPOINTS } from '../constants/api.constants';

@Injectable({
  providedIn: 'root',
})
export class HoaDonService {
  constructor(private apiService: ApiService) {}

  // ===================== HÓA ĐƠN =====================
  // GET /api/hoadon
  getAllHoaDon(): Observable<HoaDon[]> {
    return this.apiService.get<HoaDon[]>(_ENDPOINTS.HOADON);
  }

  // GET /api/hoadon/{id}
  getHoaDonById(id: number): Observable<HoaDon> {
    return this.apiService.get<HoaDon>(`${_ENDPOINTS.HOADON}/${id}`);
  }

  // POST /api/hoadon
  createHoaDon(hoadon: Partial<HoaDon>): Observable<HoaDon> {
    return this.apiService.post<HoaDon>(_ENDPOINTS.HOADON, hoadon);
  }

  // DELETE /api/hoadon/{id}
  deleteHoaDon(id: number): Observable<void> {
    return this.apiService.delete<void>(`${_ENDPOINTS.HOADON}/${id}`);
  }

  // ===================== CHI TIẾT HÓA ĐƠN =====================
  // GET /api/chitiethoadon/{id}
  getChiTietHoaDonById(id: number): Observable<ChiTietHoaDon> {
    return this.apiService.get<ChiTietHoaDon>(
      `${_ENDPOINTS.CHITIETHOADON}/${id}`
    );
  }

  // GET /api/chitiethoadon/hoadon/{hoadonId}
  getChiTietHoaDonByHoaDonId(hoadonId: number): Observable<ChiTietHoaDon[]> {
    return this.apiService.get<ChiTietHoaDon[]>(
      `${_ENDPOINTS.CHITIETHOADON}/hoadon/${hoadonId}`
    );
  }

  // POST /api/chitiethoadon
  createChiTietHoaDon(
    chitiethoadon: Partial<ChiTietHoaDon>
  ): Observable<ChiTietHoaDon> {
    return this.apiService.post<ChiTietHoaDon>(
      _ENDPOINTS.CHITIETHOADON,
      chitiethoadon
    );
  }

  // DELETE /api/chitiethoadon/{id}
  deleteChiTietHoaDon(id: number): Observable<void> {
    return this.apiService.delete<void>(`${_ENDPOINTS.CHITIETHOADON}/${id}`);
  }

  // ===================== UTILITY METHODS =====================
  downloadHoaDon(hoadonId: number): Observable<Blob> {
    return this.apiService.downloadFile(
      `${_ENDPOINTS.HOADON}/${hoadonId}/download`
    );
  }

  printHoaDon(hoadonId: number): Observable<Blob> {
    return this.apiService.downloadFile(
      `${_ENDPOINTS.HOADON}/${hoadonId}/print`
    );
  }

  sendHoaDonEmail(hoadonId: number, email: string): Observable<any> {
    return this.apiService.post<any>(`${_ENDPOINTS.HOADON}/${hoadonId}/email`, {
      email,
    });
  }
}
