import { KhachHang } from './khachhang.model';
import { ChiTietDonHang } from './chitietdonhang.model';
import { PhuongThucVanChuyen } from './phuongthucvanchuyen.model';
import { HoaDon } from './hoadon.model';

export interface DonHang {
  id: number;
  khachhangid: number;
  ngaydat: Date | string;
  diachinhan?: string;
  trangthaidonhang: string;
  tongtien?: number;
  phuongthucthanhtoan?: string;
  trangthaithanhtoan: boolean;
  ngaythanhtoan?: Date | string;
  vanchuyenid?: number;

  // Relations
  khachhang?: KhachHang;
  chitietdonhang?: ChiTietDonHang[];
  phuongthucvanchuyen?: PhuongThucVanChuyen;
  hoadon?: HoaDon[];
}
