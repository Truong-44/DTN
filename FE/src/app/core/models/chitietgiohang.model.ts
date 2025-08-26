import { GioHang } from './giohang.model';
import { ChiTietSanPham } from './chitietsanpham.model';

export interface ChiTietGioHang {
  id: number;
  giohangid: number;
  chitietsanphamid: number;
  soluong: number;
  dongia: number;
  thanhtien?: number; // Calculated field (soluong * dongia)
  ngaythem?: Date | string; // Optional for computed items

  // Relations
  giohang?: GioHang;
  chitietsanpham?: ChiTietSanPham;
}

export interface ChiTietGioHangRequest {
  giohangid: number;
  sanphamid: number;
  soluong: number;
  dongia: number;
}

export interface ChiTietGioHangUpdate {
  soluong: number;
  dongia?: number;
}
