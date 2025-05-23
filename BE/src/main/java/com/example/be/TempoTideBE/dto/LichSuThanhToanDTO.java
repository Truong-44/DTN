package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class LichSuThanhToanDTO {
    private Integer maLichSu;
    private Integer maThanhToan;
    private String trangThai;
    private BigDecimal soTien;
    private LocalDateTime ngayCapNhat;
    private String ghiChu;
    private Boolean trangThai;
    private Integer nguoiCapNhatId;
}