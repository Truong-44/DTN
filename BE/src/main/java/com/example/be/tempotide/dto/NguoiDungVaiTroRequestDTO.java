package com.example.be.tempotide.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NguoiDungVaiTroRequestDTO {
    @NotNull(message = "Mã người dùng không được để trống")
    private Integer manguoidung;

    @NotNull(message = "Mã vai trò không được để trống")
    private Integer mavaitro;

    @NotBlank(message = "Loại người dùng không được để trống")
    private String loainguoidung;
}