package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for ThuocTinhSanPham entity")
public class thuoctinhsanphamdto {

    @Schema(description = "Unique identifier of the product attribute", example = "1")
    private Integer maThuocTinh;

    @NotNull(message = "MaSanPham is required")
    @Schema(description = "Product ID", example = "1")
    private Integer maSanPham;

    @NotBlank(message = "TenThuocTinh is required")
    @Size(max = 100, message = "TenThuocTinh must not exceed 100 characters")
    @Schema(description = "Attribute name", example = "Màu sắc")
    private String tenThuocTinh;

    @NotBlank(message = "GiaTri is required")
    @Size(max = 200, message = "GiaTri must not exceed 200 characters")
    @Schema(description = "Attribute value", example = "Đen")
    private String giaTri;

    @Schema(description = "Status of the attribute", example = "true")
    private Boolean trangThai;
}