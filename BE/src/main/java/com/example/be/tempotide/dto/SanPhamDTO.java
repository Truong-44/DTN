package com.example.be.tempotide.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SanPhamDTO {
    private Integer masanpham;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 100, message = "Tên sản phẩm không được vượt quá 100 ký tự")
    private String tensanpham;

    @Size(max = 500, message = "Mô tả không được vượt quá 500 ký tự")
    private String mota;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Giá phải lớn hơn hoặc bằng 0")
    private BigDecimal gia;

    private Boolean trangthai;

    @NotNull(message = "Mã danh mục không được để trống")
    private Integer madanhmuc;
}