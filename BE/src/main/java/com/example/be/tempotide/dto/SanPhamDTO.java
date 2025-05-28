package com.example.be.tempotide.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SanPhamDTO {
    private Integer masanpham;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 100, message = "Tên sản phẩm không được vượt quá 100 ký tự")
    private String tensanpham;

    @Size(max = 500, message = "Mô tả không được vượt quá 500 ký tự")
    private String mota;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    private Double gia;

    @NotNull(message = "Mã danh mục không được để trống")
    private Integer madanhmuc;

    @NotNull(message = "Ngày tạo không được để trống")
    private LocalDateTime ngaytao;

    private LocalDateTime ngaycapnhat;

    private Boolean trangthai;

    private Integer nguoitao; // Thêm trường này
    private Integer nguoicapnhat; // Thêm trường này
}