package com.noithat.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaiKhoanDTO {

    private Integer id;
    private String tendangnhap;
    private String matkhau;
    private String email;
    private String sodienthoai;
    private Boolean trangthai;
    private String loaidangnhap;
    private Integer quyenId;
    private String quyenTen;
    private KhachHangDTO khachhang;
    private NhanVienDTO nhanvien;

    // Alias method for compatibility
    public String getUsername() {
        return this.tendangnhap;
    }
}
