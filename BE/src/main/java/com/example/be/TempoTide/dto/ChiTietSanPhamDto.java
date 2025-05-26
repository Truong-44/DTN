package com.example.be.TempoTide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for ChiTietSanPham entity")
public class ChiTietSanPhamDto {

    @Schema(description = "Unique identifier of the product detail", example = "1")
    private Integer maChiTietSanPham;

    @NotNull(message = "MaSanPham is required")
    @Schema(description = "Product ID", example = "1")
    private Integer maSanPham;

    @Schema(description = "Available quantity", example = "100")
    private Integer soLuongTon;

    @Schema(description = "Selling price", example = "199.99")
    private Double giaBan;

    @Schema(description = "Status of the product detail", example = "true")
    private Boolean trangThai;
}