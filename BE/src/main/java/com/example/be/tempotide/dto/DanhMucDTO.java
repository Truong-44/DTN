package com.example.be.tempotide.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DanhMucDTO {
    private Integer madanhmuc;

    @NotBlank(message = "Tên danh mục không được để trống")
    @Size(max = 100, message = "Tên danh mục không được vượt quá 100 ký tự")
    private String tendanhmuc;

    private Integer madanhmuccha; // ID của danh mục cha

    @Size(max = 200, message = "Mô tả không được vượt quá 200 ký tự")
    private String mota;

    private LocalDateTime ngaytao;
    private Boolean trangthai;

    // Không bao gồm nguoitao trong DTO để tránh vòng lặp tuần hoàn
}