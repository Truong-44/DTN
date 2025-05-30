package com.example.be.tempotide.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChatBoxLichSuDTO {
    private Integer machat;
    private Integer makhachhang;
    private String sodienthoai;
    private String noidung;
    private String loaiCauhoi;
    private Integer giosanpham;
    private LocalDateTime ngaytao;
    private Boolean trangthai;
    private Integer nguoitao;
}