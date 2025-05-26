package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for SanPham entity")
public class sanphamdto {

    @Schema(description = "Unique identifier of the product", example = "1")
    private Integer maSanPham;

    @NotBlank(message = "TenSanPham is required")
    @Size(max = 100, message = "TenSanPham must not exceed 100 characters")
    @Schema(description = "Name of the product", example = "Laptop XYZ")
    private String tenSanPham;

    @Size(max = 500, message = "MoTa must not exceed 500 characters")
    @Schema(description = "Description of the product", example = "High-performance laptop")
    private String moTa;

    @NotNull(message = "Gia is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Gia must be greater than 0")
    @Schema(description = "Price of the product", example = "15000000.00")
    private BigDecimal gia;

    @Size(max = 500, message = "HinhAnh must not exceed 500 characters")
    @Schema(description = "Image URL of the product", example = "http://example.com/image.jpg")
    private String hinhAnh;

    @NotNull(message = "MaDanhMuc is required")
    @Schema(description = "ID of the product category", example = "1")
    private Integer maDanhMucId;

    @NotNull(message = "MaThuongHieu is required")
    @Schema(description = "ID of the product brand", example = "1")
    private Integer maThuongHieuId;

    @NotNull(message = "NgayTao is required")
    @Schema(description = "Creation date of the product record", example = "2023-01-01T10:00:00")
    private LocalDateTime ngayTao;

    @NotNull(message = "NgayCapNhat is required")
    @Schema(description = "Last update date of the product record", example = "2023-01-02T10:00:00")
    private LocalDateTime ngayCapNhat;

    @NotNull(message = "TrangThai is required")
    @Schema(description = "Status of the product", example = "true")
    private Boolean trangThai;

    @Schema(description = "ID of the employee who created this record", example = "2")
    private Integer nguoiTaoId;

    @Schema(description = "ID of the employee who last updated this record", example = "3")
    private Integer nguoiCapNhatId;
}
