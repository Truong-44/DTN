package com.example.be.tempotide.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VaiTroDTO {
    private Integer mavaitro;

    @NotBlank(message = "Tên vai trò không được để trống")
    private String tenvaitro;

    private String mota;

    private Boolean trangthai;
}