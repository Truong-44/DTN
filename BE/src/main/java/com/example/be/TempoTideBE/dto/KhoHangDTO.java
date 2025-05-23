package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class KhoHangDTO {
    private Integer maKhoHang;
    private Integer maChiTietSanPham;
    private Integer soLuong;
    private String viTriKho;
    private LocalDateTime ngayNhapKho;
    private LocalDateTime ngayXuatKho;
    private LocalDateTime ngayCapNhat;
    private Boolean trangThai;
    private Integer nguoiCapNhatId;
}