package com.example.be.tempotide.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DonHangDTO {
    private Integer madonhang;

    @NotNull(message = "Mã khách hàng không được để trống")
    private Integer makhachhang;

    @NotNull(message = "Ngày đặt hàng không được để trống")
    private LocalDateTime ngaydathang;

    @NotBlank(message = "Địa chỉ giao hàng không được để trống")
    @Size(max = 200, message = "Địa chỉ giao hàng không được vượt quá 200 ký tự")
    private String diachigiaohang;

    @NotBlank(message = "Phương thức thanh toán không được để trống")
    private String phuongthucthanhtoan;

    private String trangthai;

    private Integer manhanvienxuly;

    @NotNull(message = "Ngày tạo không được để trống")
    private LocalDateTime ngaytao;

    private LocalDateTime ngaycapnhat;

    private Integer nguoitao; // Thêm trường này
    private Integer nguoicapnhat; // Thêm trường này
}