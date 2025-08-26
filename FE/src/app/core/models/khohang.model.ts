import { ChiTietSanPham } from './chitietsanpham.model';
import { SanPham } from './sanpham.model';

export interface KhoHang {
  id: number;
  chitietsanphamid?: number;
  sanphamid?: number;
  soluongton: number;
  gianhap?: number;
  ngaycapnhat: Date;
  chitietsanpham?: ChiTietSanPham;
  sanpham?: SanPham;
}
