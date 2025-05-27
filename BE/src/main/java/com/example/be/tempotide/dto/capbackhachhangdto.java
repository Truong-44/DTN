package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for capbackhachhang entity")
public class capbackhachhangdto {

    @Schema(description = "Unique identifier of the customer rank", example = "1")
    private Integer macapbac;

    @NotBlank(message = "tencapbac is required")
    @Size(max = 50, message = "tencapbac must not exceed 50 characters")
    @Schema(description = "Name of the customer rank", example = "Vàng")
    private String tencapbac;

    @NotNull(message = "diemtoithieu is required")
    @Min(value = 0, message = "diemtoithieu must be greater than or equal to 0")
    @Schema(description = "Minimum points required for the rank", example = "1000")
    private Integer diemtoithieu;

    @Schema(description = "Default discount percentage", example = "10.50")
    @DecimalMin(value = "0.00", message = "giamgiamacdinh must be between 0 and 100")
    @DecimalMax(value = "100.00", message = "giamgiamacdinh must be between 0 and 100")
    private Float giamgiamacdinh;

    @Schema(description = "Creation date", example = "2025-05-25T11:50:00")
    private LocalDateTime ngaytao;

    @Schema(description = "Status of the rank", example = "true")
    private Boolean trangthai;

    @Schema(description = "Creator ID", example = "1")
    private Integer nguoitao;
}