package com.example.be.tempotide.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VaiTroQuyenRequestDTO {
    @NotNull(message = "Mã vai trò không được để trống")
    private Integer mavaitro;

    @NotNull(message = "Mã quyền không được để trống")
    private Integer maquyen;
}