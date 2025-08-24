import { Quyen } from './quyen.model';
import { KhachHang } from './khachhang.model';
import { NhanVien } from './nhanvien.model';

export interface TaiKhoan {
  id: number;
  tendangnhap: string;
  matkhau?: string; // Optional for security in frontend
  email?: string;
  sodienthoai?: string;
  trangthai: boolean;
  ngaytao: Date;
  quyenid: number;
  loaidangnhap: string;
  quyen?: Quyen;
  khachhang?: KhachHang;
  nhanvien?: NhanVien;
}

export interface TaiKhoanLienKet {
  id: number;
  taikhoanid: number;
  loaidangnhap: string;
  giatridangnhap: string;
  taikhoan?: TaiKhoan;
}
