package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class DonHangDTO {
    private Integer maDonHang;
    private Integer maKhachHang;
    private Integer maNhanVienXuLy;
    private LocalDateTime ngayDatHang;
    private BigDecimal tongTien;
    private Integer maPhuongThucVanChuyen;
    private Integer maDiaChiGiaoHang;
    private String trangThaiDonHang;
    private Integer maKhuyenMai;
    private String ghiChu;
    private Boolean laDonHangVangLai;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
    private Boolean trangThai;
    private Integer nguoiTaoId;
    private Integer nguoiCapNhatId;
}