import { SanPham } from './sanpham.model';

export interface DanhMuc {
  id: number;
  danhmucChaId?: number; // Backend field name
  danhmucchaid?: number; // Frontend field name for compatibility
  tendanhmuc: string;
  mota?: string;
  danhmucChaTen?: string; // From backend
  children?: DanhMuc[];
  parent?: DanhMuc;
  sanpham?: SanPham[];
}
