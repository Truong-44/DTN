package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class LichSuVanChuyenDTO {
    private Integer maLichSu;
    private Integer maDonHang;
    private Integer maPhuongThucVanChuyen;
    private String trangThaiVanChuyen;
    private String diaDiem;
    private LocalDateTime ngayCapNhat;
    private String ghiChu;
    private Boolean trangThai;
    private Integer nguoiCapNhatId;
}