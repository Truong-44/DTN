export interface ChiTietHoaDon {
  id: number;
  hoadonid: number;
  tensanpham?: string;
  tenmau?: string;
  chatlieu?: string;
  kichthuoc?: string;
  soluong: number;
  dongia: number;
  hoadon?: HoaDon;
}
import { HoaDon } from './hoadon.model';
