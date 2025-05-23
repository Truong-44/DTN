package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class YeuCauBaoHanhDTO {
    private Integer maYeuCau;
    private Integer maBaoHanh;
    private Integer maKhachHang;
    private String moTaLoi;
    private String trangThaiYeuCau;
    private LocalDateTime ngayYeuCau;
    private LocalDateTime ngayXuLy;
    private String ghiChu;
    private Boolean trangThai;
    private Integer nguoiXuLyId;
}