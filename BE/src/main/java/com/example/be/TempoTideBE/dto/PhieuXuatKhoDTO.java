package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class PhieuXuatKhoDTO {
    private Integer maPhieuXuatKho;
    private Integer maDonHang;
    private LocalDateTime ngayXuatKho;
    private BigDecimal tongGiaTri;
    private String ghiChu;
    private LocalDateTime ngayTao;
    private Boolean trangThai;
    private Integer nguoiTaoId;
}