export interface ChiTietDonHang {
  id: number;
  donhangid: number;
  chitietsanphamid: number;
  soluong: number;
  dongia: number;
  donhang?: DonHang;
  chitietsanpham?: ChiTietSanPham;
}
import { DonHang } from './donhang.model';
import { ChiTietSanPham } from './chitietsanpham.model';
