package com.example.tempotide.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ChiTietGioHangDTO {
    @NotNull(message = "Mã giỏ hàng không được để trống")
    private Integer magiohang;

    @NotNull(message = "Mã chi tiết sản phẩm không được để trống")
    private Integer machitietsanpham;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private Integer soluong;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Giá phải lớn hơn hoặc bằng 0")
    private BigDecimal gia;
}