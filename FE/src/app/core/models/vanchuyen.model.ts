import { DonHang } from './donhang.model';

export interface VanChuyen {
  id: number;
  donhangid: number;
  mavanchuyen?: string;
  donvivanchuyen?: string;
  trangthai?: string;
  ngaytao?: Date;
  ngaygiaodk?: Date;
  ngaygiaothucte?: Date;
  ghichu?: string;
  donhang?: DonHang;
}

export interface ChiTietVanChuyen {
  id: number;
  vanchuyenid: number;
  thoigian: Date;
  vitri?: string;
  trangthai: string;
  mota?: string;
}
