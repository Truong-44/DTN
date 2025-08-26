import { DanhMuc } from './danhmuc.model';
import { ChiTietSanPham } from './chitietsanpham.model';

export interface SanPham {
  id: number;
  tensanpham: string;
  mota?: string;
  danhmucId?: number; // Backend field name
  danhmucid?: number; // Frontend field name for compatibility
  danhmucTen?: string; // From backend
  tenloai?: string;
  giacu?: number;
  giamoi?: number;
  ngaytao?: Date | string;
  trangthai: boolean;

  // Relationships (được load tách biệt)
  danhmuc?: DanhMuc;
  chitietsanpham?: ChiTietSanPham[];
}
