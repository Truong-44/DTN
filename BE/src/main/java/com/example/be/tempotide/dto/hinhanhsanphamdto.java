package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for HinhAnhSanPham entity")
public class hinhanhsanphamdto {

    @Schema(description = "Unique identifier of the product image", example = "1")
    private Integer maHinhAnh;

    @NotNull(message = "MaSanPham is required")
    @Schema(description = "Product ID", example = "1")
    private Integer maSanPham;

    @NotBlank(message = "DuongDan is required")
    @Size(max = 500, message = "DuongDan must not exceed 500 characters")
    @Schema(description = "Image URL or path", example = "https://example.com/images/product1.jpg")
    private String duongDan;

    @Schema(description = "Whether this is the main image", example = "false")
    private Boolean laAnhChinh;

    @Schema(description = "Status of the image", example = "true")
    private Boolean trangThai;
}