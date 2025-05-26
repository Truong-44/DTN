package com.example.be.TempoTide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for PhuongThucVanChuyen entity")
public class PhuongThucVanChuyenDto {

    @Schema(description = "Unique identifier of the shipping method", example = "1")
    private Integer maPhuongThuc;

    @NotBlank(message = "TenPhuongThuc is required")
    @Size(max = 100, message = "TenPhuongThuc must not exceed 100 characters")
    @Schema(description = "Name of the shipping method", example = "Giao hàng nhanh")
    private String tenPhuongThuc;

    @Size(max = 500, message = "MoTa must not exceed 500 characters")
    @Schema(description = "Description of the shipping method", example = "Giao hàng trong 24h")
    private String moTa;

    @NotNull(message = "ChiPhi is required")
    @Schema(description = "Shipping cost", example = "30000.0")
    private Double chiPhi;

    @NotNull(message = "ThoiGianDuKien is required")
    @Schema(description = "Estimated delivery time in days", example = "1")
    private Integer thoiGianDuKien;

    @NotNull(message = "NgayTao is required")
    @Schema(description = "Creation date", example = "2025-05-24T23:58:00")
    private LocalDateTime ngayTao;

    @Schema(description = "Status of the shipping method", example = "true")
    private Boolean trangThai;

    @Schema(description = "ID of the creator", example = "1")
    private Integer nguoiTao;

    @Schema(description = "ID of the last updater", example = "1")
    private Integer nguoiCapNhat;
}