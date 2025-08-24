import { TaiKhoan } from './taikhoan.model';
import { DonHang } from './donhang.model';
import { GioHang } from './giohang.model';

export interface KhachHang {
  id: number;
  taikhoanid: number;
  hoten?: string;
  diachi?: string;
  ngaysinh?: Date;
  gioitinh?: string;
  taikhoan?: TaiKhoan;
  donhang?: DonHang[];
  giohang?: GioHang[];
}
