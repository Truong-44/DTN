package com.example.be.tempotide.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NguoiDungVaiTroDTO {
    private Integer manguoidungVaitro;
    private Integer manhanvien;
    private Integer makhachhang;
    private Integer mavaitro;
    private LocalDateTime ngaytao;
    private Boolean trangthai;
    private Integer nguoitao;
}