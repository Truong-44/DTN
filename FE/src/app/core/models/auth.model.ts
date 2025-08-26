import { TaiKhoan } from './taikhoan.model';
export interface LoginRequest {
  username: string; // Changed from tendangnhap
  password: string; // Changed from matkhau
  loaidangnhap?: string;
}
export interface LoginResponse {
  token: string;
  refreshToken?: string;
  user: TaiKhoan;
  expiresIn: number;
}

export interface RegisterRequest {
  tendangnhap: string;
  matkhau: string;
  email?: string;
  sodienthoai?: string;
  hoten?: string;
  loaidangnhap: string;
}
