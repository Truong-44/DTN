package com.example.be.tempotide.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class NhanVienDTO {
    private Integer manhanvien;
    private String ho;
    private String ten;
    private String email;
    private String sodienthoai;
    private LocalDate ngaytuyendung;
    private String matkhau;
    private LocalDateTime ngaytao;
    private LocalDateTime ngaycapnhat;
    private Boolean trangthai;
    private Integer nguoitao;
    private Integer nguoicapnhat;
}