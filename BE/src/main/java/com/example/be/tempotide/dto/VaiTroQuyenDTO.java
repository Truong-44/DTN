package com.example.be.tempotide.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VaiTroQuyenDTO {
    private Integer mavaitroQuyen;

    @NotNull(message = "Mã vai trò không được để trống")
    private Integer mavaitro;

    @NotNull(message = "Mã quyền không được để trống")
    private Integer maquyen;

    private LocalDateTime ngaytao;
    private Boolean trangthai;

    // Không bao gồm nguoitao trong DTO để tránh vòng lặp tuần hoàn khi serialize
}