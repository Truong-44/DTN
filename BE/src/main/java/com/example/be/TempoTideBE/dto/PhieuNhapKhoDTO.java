package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class PhieuNhapKhoDTO {
    private Integer maPhieuNhapKho;
    private Integer maNhaCungCap;
    private LocalDateTime ngayNhapKho;
    private BigDecimal tongGiaTri;
    private String ghiChu;
    private LocalDateTime ngayTao;
    private Boolean trangThai;
    private Integer nguoiTaoId;
}