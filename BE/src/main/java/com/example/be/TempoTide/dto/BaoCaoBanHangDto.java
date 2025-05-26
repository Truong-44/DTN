package com.example.be.TempoTide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for BaoCaoBanHang entity")
public class BaoCaoBanHangDto {

    @Schema(description = "Unique identifier of the sales report", example = "1")
    private Integer maBaoCao;

    @NotNull(message = "ThoiGianBatDau is required")
    @Schema(description = "Start time of the report period", example = "2025-05-01T00:00:00")
    private LocalDateTime thoiGianBatDau;

    @NotNull(message = "ThoiGianKetThuc is required")
    @Schema(description = "End time of the report period", example = "2025-05-31T23:59:59")
    private LocalDateTime thoiGianKetThuc;

    @Schema(description = "Total revenue", example = "15000.50")
    private Double tongDoanhThu;

    @Schema(description = "Number of orders", example = "50")
    private Integer soDonHang;

    @Schema(description = "Status of the report", example = "true")
    private Boolean trangThai;
}