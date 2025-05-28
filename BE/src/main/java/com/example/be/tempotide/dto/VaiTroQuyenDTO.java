package com.example.be.tempotide.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VaiTroQuyenDTO {
    private Integer mavaitroquyen;

    @NotNull(message = "Mã vai trò không được để trống")
    private Integer mavaitro;

    @NotNull(message = "Mã quyền không được để trống")
    private Integer maquyen;

    @NotNull(message = "Ngày tạo không được để trống")
    private LocalDateTime ngaytao;

    private Boolean trangthai;

    private Integer nguoitao; // Thêm trường này
}