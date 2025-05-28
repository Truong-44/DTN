package com.example.be.tempotide.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NguoiDungVaiTroDTO {
    private Integer manguoidungVaitro;

    @NotNull(message = "Mã nhân viên không được để trống")
    private Integer manhanvien;

    @NotNull(message = "Mã vai trò không được để trống")
    private Integer mavaitro;

    @NotNull(message = "Ngày tạo không được để trống")
    private LocalDateTime ngaytao;

    private Boolean trangthai;
}