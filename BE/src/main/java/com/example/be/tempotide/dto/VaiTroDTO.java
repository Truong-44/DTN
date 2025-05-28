package com.example.be.tempotide.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VaiTroDTO {
    private Integer mavaitro;

    @NotBlank(message = "Tên vai trò không được để trống")
    @Size(max = 50, message = "Tên vai trò không được vượt quá 50 ký tự")
    private String tenvaitro;

    @Size(max = 200, message = "Mô tả không được vượt quá 200 ký tự")
    private String mota;

    private LocalDateTime ngaytao;
    private Boolean trangthai;

}