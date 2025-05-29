package com.example.be.tempotide.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ChiTietDonHangDTO {
    private Integer machitietdonhang;

    @NotNull(message = "Mã đơn hàng không được để trống")
    private Integer madonhang;

    @NotNull(message = "Mã sản phẩm không được để trống")
    private Integer masanpham;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private Integer soluong;

    @NotNull(message = "Đơn giá không được để trống")
    private BigDecimal dongia;

    @Size(max = 500, message = "Ghi chú không được vượt quá 500 ký tự")
    private String ghichu;

    private LocalDateTime ngaytao;

    private LocalDateTime ngaycapnhat;

    private Boolean trangthai;

    private Integer nguoitao;
    private Integer nguoicapnhat;
}