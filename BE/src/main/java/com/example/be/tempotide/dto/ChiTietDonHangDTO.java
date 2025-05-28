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
    @DecimalMin(value = "0.0", inclusive = false, message = "Đơn giá phải lớn hơn 0")
    private Double dongia;

    @NotNull(message = "Ngày tạo không được để trống")
    private LocalDateTime ngaytao;

    private Boolean trangthai;

    private Integer nguoitao; // Thêm trường này
}