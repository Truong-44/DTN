export interface HoaDon {
  id: number;
  donhangid: number;
  ngayxuathoadon: Date;
  nguoixuathoadon?: string;
  tenkhachhang?: string;
  diachinguoinhan?: string;
  tongtien?: number;
  ghichu?: string;
  donhang?: DonHang;
  chitiethoadon?: ChiTietHoaDon[];
}
import { DonHang } from './donhang.model';
import { ChiTietHoaDon } from './chitiethoadon.model';
