import { SanPham } from './sanpham.model';
import { KhoHang } from './khohang.model';
import { ChiTietGioHang } from './chitietgiohang.model';
import { ChiTietDonHang } from './chitietdonhang.model';

export interface ChiTietSanPham {
  id: number;
  sanphamId: number; // Backend field name
  sanphamid?: number; // Alias for compatibility
  tensanpham?: string; // From backend DTO
  tenmau?: string;
  mamau?: string; // mã HEX hoặc tên
  chatlieu?: string;
  kichthuoc?: string; // VD: 200x80x75cm
  trongluong?: number; // đơn vị: kg
  soluong: number;
  hinhchinh?: string;
  hinhphu?: string; // nhiều hình phụ cách nhau dấu ;
  sanpham?: SanPham;
  khohang?: KhoHang;
  chitietgiohang?: ChiTietGioHang[];
  chitietdonhang?: ChiTietDonHang[];
}
