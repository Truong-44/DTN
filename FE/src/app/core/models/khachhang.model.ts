import { TaiKhoan } from './taikhoan.model';
import { DonHang } from './donhang.model';
import { GioHang } from './giohang.model';

export interface KhachHang {
  id: number;
  hoten: string;
  diachi?: string;
  ngaysinh?: Date | string;
  gioitinh?: string;
  email?: string;
  sodienthoai?: string;
  ghichu?: string;
  ngaytao?: Date | string;
  taikhoanId?: number;
  tendangnhap?: string; // Từ backend để hiển thị
  taikhoan?: TaiKhoan;
  donhang?: DonHang[];
  giohang?: GioHang[];
}
