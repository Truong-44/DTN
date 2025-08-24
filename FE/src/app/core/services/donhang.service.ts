// core/services/donhang.service.ts
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { ApiService } from './api.service';
import { DonHang } from '../models/donhang.model';
import { ChiTietDonHang } from '../models/chitietdonhang.model';
import { HoaDon } from '../models/hoadon.model';
import { PhuongThucVanChuyen } from '../models/phuongthucvanchuyen.model';
import {
  PaginationRequest,
  PaginationResponse,
} from '../models/pagination.model';
import { _ENDPOINTS } from '../constants/api.constants';

export interface CreateDonHangRequest {
  khachhangid: number;
  diachinhan: string;
  phuongthucthanhtoan: string;
  vanchuyenid?: number;
  ghichu?: string;
  chitietdonhang: {
    chitietsanphamid: number;
    soluong: number;
    dongia: number;
  }[];
}

@Injectable({
  providedIn: 'root',
})
export class DonHangService {
  constructor(private apiService: ApiService) {}

  // ===================== ĐƠN HÀNG =====================
  getAllDonHang(
    pagination?: PaginationRequest
  ): Observable<PaginationResponse<DonHang>> {
    let params = new HttpParams();

    if (pagination) {
      params = params.set('page', pagination.page.toString());
      params = params.set('size', pagination.size.toString());
      if (pagination.sort) {
        params = params.set('sort', pagination.sort);
        params = params.set('direction', pagination.direction || 'desc');
      }
    }

    return this.apiService.get<PaginationResponse<DonHang>>(
      _ENDPOINTS.DONHANG,
      params
    );
  }

  getDonHangById(id: number): Observable<DonHang> {
    return this.apiService.get<DonHang>(`${_ENDPOINTS.DONHANG}/${id}`);
  }

  getDonHangByKhachHangId(
    khachhangId: number,
    pagination?: PaginationRequest
  ): Observable<PaginationResponse<DonHang>> {
    let params = new HttpParams();

    if (pagination) {
      params = params.set('page', pagination.page.toString());
      params = params.set('size', pagination.size.toString());
    }

    return this.apiService.get<PaginationResponse<DonHang>>(
      `${_ENDPOINTS.DONHANG}/khachhang/${khachhangId}`,
      params
    );
  }

  createDonHang(donhangData: CreateDonHangRequest): Observable<DonHang> {
    return this.apiService.post<DonHang>(_ENDPOINTS.DONHANG, donhangData);
  }

  updateDonHang(id: number, donhang: Partial<DonHang>): Observable<DonHang> {
    return this.apiService.put<DonHang>(
      `${_ENDPOINTS.DONHANG}/${id}`,
      donhang
    );
  }

  updateDonHangStatus(id: number, status: string): Observable<DonHang> {
    return this.apiService.patch<DonHang>(
      `${_ENDPOINTS.DONHANG}/${id}/status`,
      { trangthaidonhang: status }
    );
  }

  updatePaymentStatus(
    id: number,
    paid: boolean,
    ngaythanhtoan?: Date
  ): Observable<DonHang> {
    return this.apiService.patch<DonHang>(
      `${_ENDPOINTS.DONHANG}/${id}/payment`,
      {
        trangthaithanhtoan: paid,
        ngaythanhtoan: ngaythanhtoan || new Date(),
      }
    );
  }

  cancelDonHang(id: number, reason?: string): Observable<DonHang> {
    return this.apiService.patch<DonHang>(
      `${_ENDPOINTS.DONHANG}/${id}/cancel`,
      {
        trangthaidonhang: 'Đã hủy',
        ghichu: reason,
      }
    );
  }

  // ===================== CHI TIẾT ĐƠN HÀNG =====================
  getChiTietDonHangByDonHangId(
    donhangId: number
  ): Observable<ChiTietDonHang[]> {
    return this.apiService.get<ChiTietDonHang[]>(
      `${_ENDPOINTS.CHITIETDONHANG}/donhang/${donhangId}`
    );
  }

  // ===================== HÓA ĐƠN =====================
  createHoaDon(hoadonData: Partial<HoaDon>): Observable<HoaDon> {
    return this.apiService.post<HoaDon>(_ENDPOINTS.HOADON, hoadonData);
  }

  getHoaDonByDonHangId(donhangId: number): Observable<HoaDon> {
    return this.apiService.get<HoaDon>(
      `${_ENDPOINTS.HOADON}/donhang/${donhangId}`
    );
  }

  downloadHoaDon(hoadonId: number): Observable<Blob> {
    return this.apiService.downloadFile(
      `${_ENDPOINTS.HOADON}/${hoadonId}/download`
    );
  }

  // ===================== PHƯƠNG THỨC VẬN CHUYỂN =====================
  getAllPhuongThucVanChuyen(): Observable<PhuongThucVanChuyen[]> {
    return this.apiService.get<PhuongThucVanChuyen[]>(_ENDPOINTS.VANCHUYEN);
  }

  getPhuongThucVanChuyenById(id: number): Observable<PhuongThucVanChuyen> {
    return this.apiService.get<PhuongThucVanChuyen>(
      `${_ENDPOINTS.VANCHUYEN}/${id}`
    );
  }

  // ===================== THỐNG KÊ =====================
  getDonHangStats(fromDate?: Date, toDate?: Date): Observable<any> {
    let params = new HttpParams();

    if (fromDate) {
      params = params.set('fromDate', fromDate.toISOString());
    }
    if (toDate) {
      params = params.set('toDate', toDate.toISOString());
    }

    return this.apiService.get<any>(`${_ENDPOINTS.DONHANG}/stats`, params);
  }

  getRevenueByMonth(year: number): Observable<any> {
    return this.apiService.get<any>(
      `${_ENDPOINTS.DONHANG}/revenue/monthly/${year}`
    );
  }

  getTopSellingSanPham(limit: number = 10): Observable<any> {
    const params = new HttpParams().set('limit', limit.toString());
    return this.apiService.get<any>(
      `${_ENDPOINTS.DONHANG}/top-selling`,
      params
    );
  }
}

