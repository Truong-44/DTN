package com.example.be.tempotide.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChiTietGioHangDTO {
    private Integer machitietgiohang;

    @NotNull(message = "Mã giỏ hàng không được để trống")
    private Integer magiohang;

    @NotNull(message = "Mã chi tiết sản phẩm không được để trống")
    private Integer machitietsanpham;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private Integer soluong;

    @NotNull(message = "Ngày tạo không được để trống")
    private LocalDateTime ngaytao;

    private Boolean trangthai;

    private Integer nguoitao; // Thêm trường này
}