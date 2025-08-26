import { DonHang } from './donhang.model';

export interface VanChuyen {
  id: number;
  donhangid: number;
  mavanchuyen?: string;
  donvivanchuyen?: string;
  trangthai?: string;
  ngaytao?: Date;
  ngaygui?: Date; // Added missing property
  ngaygiaodk?: Date;
  ngaygiaothucte?: Date;
  phivanchuyen?: number; // Added missing property
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
