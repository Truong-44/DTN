package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class LichSuDoiTraDTO {
    private Integer maLichSu;
    private Integer maDoiTra;
    private String trangThai;
    private String moTa;
    private LocalDateTime ngayCapNhat;
    private String ghiChu;
    private Boolean trangThai;
    private Integer nguoiCapNhatId;
}