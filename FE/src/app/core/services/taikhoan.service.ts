import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { ApiService } from './api.service';
import { TaiKhoan, TaiKhoanLienKet } from '../models/taikhoan.model';
import { KhachHang } from '../models/khachhang.model';
import { NhanVien } from '../models/nhanvien.model';
import { Quyen } from '../models/quyen.model';
import { _ENDPOINTS } from '../constants/api.constants';

@Injectable({
  providedIn: 'root',
})
export class TaiKhoanService {
  constructor(private apiService: ApiService) {}

  // ===================== TÀI KHOẢN =====================
  // GET /api/taikhoan
  getAllTaiKhoan(): Observable<TaiKhoan[]> {
    return this.apiService.get<TaiKhoan[]>(_ENDPOINTS.TAIKHOAN);
  }

  // GET /api/taikhoan/{id}
  getTaiKhoanById(id: number): Observable<TaiKhoan> {
    return this.apiService.get<TaiKhoan>(`${_ENDPOINTS.TAIKHOAN}/${id}`);
  }

  // POST /api/taikhoan
  createTaiKhoan(taikhoan: Partial<TaiKhoan>): Observable<TaiKhoan> {
    return this.apiService.post<TaiKhoan>(_ENDPOINTS.TAIKHOAN, taikhoan);
  }

  // PUT /api/taikhoan/{id}
  updateTaiKhoan(
    id: number,
    taikhoan: Partial<TaiKhoan>
  ): Observable<TaiKhoan> {
    return this.apiService.put<TaiKhoan>(
      `${_ENDPOINTS.TAIKHOAN}/${id}`,
      taikhoan
    );
  }

  // DELETE /api/taikhoan/{id}
  deleteTaiKhoan(id: number): Observable<void> {
    return this.apiService.delete<void>(`${_ENDPOINTS.TAIKHOAN}/${id}`);
  }

  // ===================== KHÁCH HÀNG =====================
  // GET /api/khachhang
  getAllKhachHang(): Observable<KhachHang[]> {
    return this.apiService.get<KhachHang[]>(_ENDPOINTS.KHACHHANG);
  }

  // GET /api/khachhang/{id}
  getKhachHangById(id: number): Observable<KhachHang> {
    return this.apiService.get<KhachHang>(`${_ENDPOINTS.KHACHHANG}/${id}`);
  }

  // POST /api/khachhang
  createKhachHang(khachhang: Partial<KhachHang>): Observable<KhachHang> {
    return this.apiService.post<KhachHang>(_ENDPOINTS.KHACHHANG, khachhang);
  }

  // PUT /api/khachhang/{id}
  updateKhachHang(
    id: number,
    khachhang: Partial<KhachHang>
  ): Observable<KhachHang> {
    return this.apiService.put<KhachHang>(
      `${_ENDPOINTS.KHACHHANG}/${id}`,
      khachhang
    );
  }

  // DELETE /api/khachhang/{id}
  deleteKhachHang(id: number): Observable<void> {
    return this.apiService.delete<void>(`${_ENDPOINTS.KHACHHANG}/${id}`);
  }

  // ===================== NHÂN VIÊN =====================
  // GET /api/nhanvien
  getAllNhanVien(): Observable<NhanVien[]> {
    return this.apiService.get<NhanVien[]>(_ENDPOINTS.NHANVIEN);
  }

  // GET /api/nhanvien/{id}
  getNhanVienById(id: number): Observable<NhanVien> {
    return this.apiService.get<NhanVien>(`${_ENDPOINTS.NHANVIEN}/${id}`);
  }

  // POST /api/nhanvien
  createNhanVien(nhanvien: Partial<NhanVien>): Observable<NhanVien> {
    return this.apiService.post<NhanVien>(_ENDPOINTS.NHANVIEN, nhanvien);
  }

  // PUT /api/nhanvien/{id}
  updateNhanVien(
    id: number,
    nhanvien: Partial<NhanVien>
  ): Observable<NhanVien> {
    return this.apiService.put<NhanVien>(
      `${_ENDPOINTS.NHANVIEN}/${id}`,
      nhanvien
    );
  }

  // DELETE /api/nhanvien/{id}
  deleteNhanVien(id: number): Observable<void> {
    return this.apiService.delete<void>(`${_ENDPOINTS.NHANVIEN}/${id}`);
  }

  // ===================== QUYỀN =====================
  // GET /api/quyen
  getAllQuyen(): Observable<Quyen[]> {
    return this.apiService.get<Quyen[]>(_ENDPOINTS.QUYEN);
  }

  // GET /api/quyen/{id}
  getQuyenById(id: number): Observable<Quyen> {
    return this.apiService.get<Quyen>(`${_ENDPOINTS.QUYEN}/${id}`);
  }

  // POST /api/quyen
  createQuyen(quyen: Partial<Quyen>): Observable<Quyen> {
    return this.apiService.post<Quyen>(_ENDPOINTS.QUYEN, quyen);
  }

  // PUT /api/quyen/{id}
  updateQuyen(id: number, quyen: Partial<Quyen>): Observable<Quyen> {
    return this.apiService.put<Quyen>(`${_ENDPOINTS.QUYEN}/${id}`, quyen);
  }

  // DELETE /api/quyen/{id}
  deleteQuyen(id: number): Observable<void> {
    return this.apiService.delete<void>(`${_ENDPOINTS.QUYEN}/${id}`);
  }

  // ===================== TÀI KHOẢN LIÊN KẾT =====================
  // GET /api/taikhoanlienket
  getAllTaiKhoanLienKet(): Observable<TaiKhoanLienKet[]> {
    return this.apiService.get<TaiKhoanLienKet[]>(_ENDPOINTS.TAIKHOANLIENKET);
  }

  // GET /api/taikhoanlienket/{id}
  getTaiKhoanLienKetById(id: number): Observable<TaiKhoanLienKet> {
    return this.apiService.get<TaiKhoanLienKet>(
      `${_ENDPOINTS.TAIKHOANLIENKET}/${id}`
    );
  }

  // POST /api/taikhoanlienket
  createTaiKhoanLienKet(
    taikhoanlienket: Partial<TaiKhoanLienKet>
  ): Observable<TaiKhoanLienKet> {
    return this.apiService.post<TaiKhoanLienKet>(
      _ENDPOINTS.TAIKHOANLIENKET,
      taikhoanlienket
    );
  }

  // DELETE /api/taikhoanlienket/{id}
  deleteTaiKhoanLienKet(id: number): Observable<void> {
    return this.apiService.delete<void>(`${_ENDPOINTS.TAIKHOANLIENKET}/${id}`);
  }

  // UPDATE /api/taikhoan/{id}/status
  updateTaiKhoanStatus(id: number, active: boolean): Observable<TaiKhoan> {
    return this.apiService.patch<TaiKhoan>(
      `${_ENDPOINTS.TAIKHOAN}/${id}/status`,
      { active }
    );
  }
}
