package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for chitietdonhang entity")
public class chitietdonhangdto {

    @Schema(description = "Unique identifier of the order detail", example = "1")
    private Integer machitietdonhang;

    @NotNull(message = "madonhang is required")
    @Schema(description = "ID of the order", example = "1")
    private Integer madonhang;

    @NotNull(message = "machitetsanpham is required")
    @Schema(description = "ID of the product", example = "1")
    private Integer machitetsanpham;

    @NotNull(message = "soluong is required")
    @Min(value = 1, message = "soluong must be at least 1")
    @Schema(description = "Quantity of the product", example = "2")
    private Integer soluong;

    @NotNull(message = "dongia is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "dongia must be non-negative")
    @Schema(description = "Unit price of the product", example = "25000000.00")
    private BigDecimal dongia;

    @Schema(description = "Discount amount", example = "5000.00")
    @DecimalMin(value = "0.0", inclusive = true, message = "giamgia must be non-negative")
    private BigDecimal giamgia;

    @Schema(description = "Prepaid status", example = "false")
    private Boolean ladattruoc;

    @Schema(description = "Creation date", example = "2025-05-26T22:44:00")
    private LocalDateTime ngaytao;

    @Schema(description = "Last update date", example = "2025-05-26T22:44:00")
    private LocalDateTime ngaycapnhat;

    @Schema(description = "Status of the order detail", example = "true")
    private Boolean trangthai;

    @Schema(description = "Creator ID", example = "1")
    private Integer nguoitao;

    @Schema(description = "Updater ID", example = "1")
    private Integer nguoicapnhat;
}