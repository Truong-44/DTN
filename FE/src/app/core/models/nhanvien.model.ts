import { TaiKhoan } from './taikhoan.model';

export interface NhanVien {
  id: number;
  hoten: string;
  email?: string;
  sodienthoai?: string;
  chucvu?: string;
  taikhoanid?: number;
  ngayvaolam: Date;
  taikhoan?: TaiKhoan;
}
