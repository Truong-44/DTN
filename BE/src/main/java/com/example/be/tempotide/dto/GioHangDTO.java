package com.example.be.tempotide.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GioHangDTO {
    private Integer magiohang;
    private Integer makhachhang;
    @Size(max = 15, message = "Số điện thoại không được vượt quá 15 ký tự")
    private String sodienthoai;
    private LocalDateTime ngaytao;
    private LocalDateTime ngaycapnhat;
    private Boolean trangthai;
    private Integer nguoitao;
}