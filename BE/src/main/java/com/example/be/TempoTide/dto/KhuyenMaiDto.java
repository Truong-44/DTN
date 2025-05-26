package com.example.be.TempoTide.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class KhuyenMaiDto {
    private Integer maKhuyenMai;

    @NotBlank(message = "TenKhuyenMai is required")
    @Size(max = 100, message = "TenKhuyenMai must not exceed 100 characters")
    private String tenKhuyenMai;

    @Size(max = 500, message = "MoTa must not exceed 500 characters")
    private String moTa;

    @NotNull(message = "PhanTramGiamGia is required")
    @Min(value = 0, message = "PhanTramGiamGia must be between 0 and 100")
    @Max(value = 100, message = "PhanTramGiamGia must be between 0 and 100")
    private Integer phanTramGiamGia;

    @NotNull(message = "NgayBatDau is required")
    private LocalDateTime ngayBatDau;

    @NotNull(message = "NgayKetThuc is required")
    private LocalDateTime ngayKetThuc;

    private LocalDateTime ngayTao;

    private LocalDateTime ngayCapNhat;

    private Boolean trangThai;

    private Integer nguoiTaoId;

    private Integer nguoiCapNhatId;
}
