package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class KhuyenMaiDTO {
    private Integer maKhuyenMai;
    private String tenKhuyenMai;
    private String moTa;
    private BigDecimal phanTramGiamGia;
    private String dieuKienApDung;
    private Integer soLuongApDung;
    private Boolean apDungChoDatTruoc;
    private LocalDateTime ngayBatDau;
    private LocalDateTime ngayKetThuc;
    private LocalDateTime ngayTao;
    private Boolean trangThai;
    private Integer nguoiTaoId;
}