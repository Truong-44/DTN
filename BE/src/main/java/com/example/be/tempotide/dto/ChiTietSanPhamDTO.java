package com.example.be.tempotide.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChiTietSanPhamDTO {
    private Integer machitietsanpham;

    @NotNull(message = "Mã sản phẩm không được để trống")
    private Integer masanpham;

    @NotBlank(message = "Kích thước không được để trống")
    @Size(max = 10, message = "Kích thước không được vượt quá 10 ký tự")
    private String kichthuoc;

    @NotBlank(message = "Màu sắc không được để trống")
    @Size(max = 20, message = "Màu sắc không được vượt quá 20 ký tự")
    private String mausac;

    @NotNull(message = "Số lượng tồn không được để trống")
    @Min(value = 0, message = "Số lượng tồn không được âm")
    private Integer soluongton;

    @NotNull(message = "Ngày tạo không được để trống")
    private LocalDateTime ngaytao;

    private LocalDateTime ngaycapnhat;

    private Boolean trangthai;

    private Integer nguoitao; // Thêm trường này
    private Integer nguoicapnhat; // Thêm trường này
}