package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class ThanhToanDTO {
    private Integer maThanhToan;
    private Integer maDonHang;
    private Integer maPhuongThucThanhToan;
    private BigDecimal soTien;
    private LocalDateTime ngayThanhToan;
    private String trangThaiThanhToan;
    private String maGiaoDich;
    private String ghiChu;
    private Boolean trangThai;
    private Integer nguoiTaoId;
}