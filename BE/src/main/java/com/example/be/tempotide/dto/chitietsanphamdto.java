package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for chitietsanpham entity")
public class chitietsanphamdto {

    @Schema(description = "Unique identifier of the product detail", example = "1")
    private Integer machitetsanpham;

    @NotNull(message = "masanpham is required")
    @Schema(description = "Product ID", example = "1")
    private Integer masanpham;

    @Schema(description = "Available quantity", example = "100")
    private Integer soluongton;

    @Schema(description = "Selling price", example = "199.99")
    private Double giaban;

    @Schema(description = "Creation date", example = "2025-05-26T22:49:00")
    private LocalDateTime ngaytao;

    @Schema(description = "Status of the product detail", example = "true")
    private Boolean trangthai;

    @Schema(description = "Creator ID", example = "1")
    private Integer nguoitao;
}