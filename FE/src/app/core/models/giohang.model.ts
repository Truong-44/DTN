import { KhachHang } from './khachhang.model';
import { ChiTietGioHang } from './chitietgiohang.model';

export interface GioHang {
  id: number;
  khachhangid: number;
  ngaycapnhat: Date;
  khachhang?: KhachHang;
  chitietgiohang?: ChiTietGioHang[];
}
