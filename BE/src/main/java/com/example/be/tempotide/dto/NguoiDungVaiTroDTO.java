package com.example.be.tempotide.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NguoiDungVaiTroDTO {
    private Integer manguoidungVaitro;
    private Integer manhanvien;
    private Integer makhachhang;
    @NotNull(message = "Mã vai trò không được để trống")
    private Integer mavaitro;
    private LocalDateTime ngaytao;
    private Boolean trangthai;
    private Integer nguoitao;
}