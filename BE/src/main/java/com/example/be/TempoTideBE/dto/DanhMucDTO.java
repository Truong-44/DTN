package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class DanhMucDTO {
    private Integer maDanhMuc;
    private String tenDanhMuc;
    private String moTa;
    private Integer maDanhMucCha;
    private Integer thuTuHienThi;
    private String hinhAnh;
    private LocalDateTime ngayTao;
    private Boolean trangThai;
    private Integer nguoiTaoId;
}