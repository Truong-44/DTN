package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class BaoCaoBanHangDTO {
    private Integer maBaoCao;
    private LocalDateTime thoiGianBatDau;
    private LocalDateTime thoiGianKetThuc;
    private Double tongDoanhThu;
    private Integer soDonHang;
    private LocalDateTime ngayTao;
    private Boolean trangThai;
    private Integer nguoiTaoId;
}