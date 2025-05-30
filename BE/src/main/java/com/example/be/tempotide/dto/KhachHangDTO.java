package com.example.be.tempotide.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class KhachHangDTO {
    private Integer makhachhang;
    private String ho;
    private String ten;
    private String email;
    private String sodienthoai;
    private String matkhau;
    private Integer diemtichluy;
    private Boolean nhanthongbao;
    private LocalDateTime ngaytao;
    private Boolean trangthai;
    private Integer nguoitao;
    private Integer nguoicapnhat;
}