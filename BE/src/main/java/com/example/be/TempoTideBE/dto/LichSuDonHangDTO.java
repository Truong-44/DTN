package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class LichSuDonHangDTO {
    private Integer maLichSu;
    private Integer maDonHang;
    private String trangThaiCu;
    private String trangThaiMoi;
    private String lyDo;
    private LocalDateTime ngayCapNhat;
    private Integer nguoiCapNhatId;
}