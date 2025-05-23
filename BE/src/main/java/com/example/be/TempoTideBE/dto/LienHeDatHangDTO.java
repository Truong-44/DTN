package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class LienHeDatHangDTO {
    private Integer maLienHe;
    private String hoTen;
    private String soDienThoai;
    private String email;
    private Integer maSanPham;
    private Integer maChiTietSanPham;
    private Integer soLuong;
    private String ghiChu;
    private Integer maDonHang;
    private LocalDateTime ngayLienHe;
    private Boolean trangThai;
    private Integer nguoiTaoId;
}