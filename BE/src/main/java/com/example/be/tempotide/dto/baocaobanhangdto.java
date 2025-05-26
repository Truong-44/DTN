package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for baocaobanhang entity")
public class baocaobanhangdto {

    @Schema(description = "Unique identifier of the sales report", example = "1")
    private Integer ma_bao_cao;

    @NotNull(message = "thoi_gian_bat_dau is required")
    @Schema(description = "Start time of the report period", example = "2025-05-01T00:00:00")
    private LocalDateTime thoi_gian_bat_dau;

    @NotNull(message = "thoi_gian_ket_thuc is required")
    @Schema(description = "End time of the report period", example = "2025-05-31T23:59:59")
    private LocalDateTime thoi_gian_ket_thuc;

    @Schema(description = "Total revenue", example = "15000.50")
    private Double tong_doanh_thu;

    @Schema(description = "Number of orders", example = "50")
    private Integer so_don_hang;

    @Schema(description = "Status of the report", example = "true")
    private Boolean trang_thai;
}