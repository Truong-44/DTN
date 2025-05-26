package com.example.be.TempoTide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for ChiTietPhieuNhap entity")
public class ChiTietPhieuNhapDto {

    @Schema(description = "Unique identifier of the import detail", example = "1")
    private Integer maChiTietPhieuNhap;

    @NotNull(message = "MaPhieuNhap is required")
    @Schema(description = "ID of the import voucher", example = "1")
    private Integer maPhieuNhap;

    @NotNull(message = "MaSanPham is required")
    @Schema(description = "ID of the product", example = "1")
    private Integer maSanPham;

    @NotNull(message = "SoLuong is required")
    @Min(value = 1, message = "SoLuong must be at least 1")
    @Schema(description = "Quantity of the product", example = "100")
    private Integer soLuong;

    @NotNull(message = "DonGia is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "DonGia must be non-negative")
    @Schema(description = "Unit price of the product", example = "50000.00")
    private BigDecimal donGia;
}