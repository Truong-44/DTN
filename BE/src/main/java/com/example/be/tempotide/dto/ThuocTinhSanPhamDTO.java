package com.example.be.tempotide.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ThuocTinhSanPhamDTO {
    private Integer mathuoctinh;

    @NotNull(message = "Mã sản phẩm không được để trống")
    private Integer masanpham;

    @NotBlank(message = "Tên thuộc tính không được để trống")
    @Size(max = 50, message = "Tên thuộc tính không được vượt quá 50 ký tự")
    private String tenthuoctinh;

    @NotBlank(message = "Giá trị không được để trống")
    @Size(max = 100, message = "Giá trị không được vượt quá 100 ký tự")
    private String giatri;

    private LocalDateTime ngaytao;
    private Boolean trangthai;

    // Không bao gồm nguoitao trong DTO để tránh vòng lặp tuần hoàn
}