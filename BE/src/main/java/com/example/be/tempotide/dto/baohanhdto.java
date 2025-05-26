package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for BaoHanh entity")
public class baohanhdto {

    @Schema(description = "Unique identifier of the warranty", example = "1")
    private Integer maBaoHanh;

    @NotNull(message = "MaSanPham is required")
    @Schema(description = "Product ID", example = "1")
    private Integer maSanPham;

    @NotNull(message = "ThoiHanBaoHanh is required")
    @Schema(description = "Warranty duration in months", example = "12")
    private Integer thoiHanBaoHanh;

    @Schema(description = "Start date of the warranty", example = "2025-05-25T11:17:00")
    private LocalDateTime ngayBatDau;

    @Schema(description = "End date of the warranty", example = "2026-05-25T11:17:00")
    private LocalDateTime ngayKetThuc;

    @Size(max = 500, message = "DieuKienBaoHanh must not exceed 500 characters")
    @Schema(description = "Warranty conditions", example = "Bảo hành 1 đổi 1 trong 12 tháng nếu lỗi do nhà sản xuất")
    private String dieuKienBaoHanh;

    @Schema(description = "Status of the warranty", example = "true")
    private Boolean trangThai;
}