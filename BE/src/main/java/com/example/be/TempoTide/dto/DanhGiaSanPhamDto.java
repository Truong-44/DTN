package com.example.be.TempoTide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for DanhGiaSanPham entity")
public class DanhGiaSanPhamDto {

    @Schema(description = "Unique identifier of the product review", example = "1")
    private Integer maDanhGia;

    @NotNull(message = "MaSanPham is required")
    @Schema(description = "ID of the product", example = "1")
    private Integer maSanPham;

    @NotNull(message = "MaKhachHang is required")
    @Schema(description = "ID of the customer", example = "1")
    private Integer maKhachHang;

    @NotNull(message = "DiemDanhGia is required")
    @Min(value = 1, message = "DiemDanhGia must be between 1 and 5")
    @Max(value = 5, message = "DiemDanhGia must be between 1 and 5")
    @Schema(description = "Rating score (1-5)", example = "5")
    private Integer diemDanhGia;

    @Size(max = 1000, message = "BinhLuan must not exceed 1000 characters")
    @Schema(description = "Comment about the product", example = "Great product!")
    private String binhLuan;

    @NotNull(message = "NgayDanhGia is required")
    @Schema(description = "Review date", example = "2025-01-01T10:00:00")
    private LocalDateTime ngayDanhGia;
}