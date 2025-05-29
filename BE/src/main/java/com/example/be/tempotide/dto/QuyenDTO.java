package com.example.be.tempotide.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuyenDTO {
    private Integer maquyen;
    @NotNull(message = "Tên quyền không được để trống")
    @Size(max = 50, message = "Tên quyền không được vượt quá 50 ký tự")
    private String tenquyen;
    @Size(max = 200, message = "Mô tả không được vượt quá 200 ký tự")
    private String mota;
    private LocalDateTime ngaytao;
    private Boolean trangthai;
    private Integer nguoitao;
}