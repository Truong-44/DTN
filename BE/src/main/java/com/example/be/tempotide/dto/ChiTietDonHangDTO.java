package com.example.be.tempotide.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChiTietDonHangDTO {
    private Integer machitietdonhang;

    @NotNull(message = "Mã đơn hàng không được để trống")
    private Integer madonhang;

    @NotNull(message = "Mã chi tiết sản phẩm không được để trống")
    private Integer machitietsanpham;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private Integer soluong;

    @NotNull(message = "Đơn giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Đơn giá phải lớn hơn hoặc bằng 0")
    private Double dongia;

    private LocalDateTime ngaytao;
    private Boolean trangthai;
}