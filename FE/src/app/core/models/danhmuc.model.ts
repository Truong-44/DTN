import { SanPham } from './sanpham.model';

export interface DanhMuc {
  id: number;
  tendanhmuc: string;
  mota?: string;
  danhmucChaId?: number;
  danhmucChaTen?: string;
  children?: DanhMuc[];
  parent?: DanhMuc;
  sanpham?: SanPham[];
}
