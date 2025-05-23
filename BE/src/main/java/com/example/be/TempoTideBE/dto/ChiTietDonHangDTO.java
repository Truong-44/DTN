package com.example.be.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class ChiTietDonHangDTO {
    private Integer maChiTietDonHang;
    private Integer maDonHang;
    private Integer maChiTietSanPham;
    private Integer soLuong;
    private BigDecimal donGia;
    private BigDecimal giamGia;
    private Boolean laDatTruoc;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
    private Boolean trangThai;
    private Integer nguoiTaoId;
    private Integer nguoiCapNhatId;
}