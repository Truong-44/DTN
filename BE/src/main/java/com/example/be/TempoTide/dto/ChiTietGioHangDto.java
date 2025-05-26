package com.example.be.TempoTide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for ChiTietGioHang entity")
public class ChiTietGioHangDto {

    @Schema(description = "Unique identifier of the cart detail", example = "1")
    private Integer maChiTietGioHang;

    @NotNull(message = "MaGioHang is required")
    @Schema(description = "ID of the cart", example = "1")
    private Integer maGioHang;

    @NotNull(message = "MaSanPham is required")
    @Schema(description = "ID of the product", example = "1")
    private Integer maSanPham;

    @NotNull(message = "SoLuong is required")
    @Min(value = 1, message = "SoLuong must be at least 1")
    @Schema(description = "Quantity of the product in the cart", example = "2")
    private Integer soLuong;
}