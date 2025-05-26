package com.example.be.TempoTide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for DanhSachYeuThich entity")
public class DanhSachYeuThichDto {

    @Schema(description = "Unique identifier of the wishlist item", example = "1")
    private Integer maYeuThich;

    @NotNull(message = "MaKhachHang is required")
    @Schema(description = "Customer ID", example = "1")
    private Integer maKhachHang;

    @NotNull(message = "MaSanPham is required")
    @Schema(description = "Product ID", example = "1")
    private Integer maSanPham;

    @Schema(description = "Status of the wishlist item", example = "true")
    private Boolean trangThai;
}