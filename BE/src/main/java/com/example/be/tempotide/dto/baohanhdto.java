package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for baohanh entity")
public class baohanhdto {

    @Schema(description = "Unique identifier of the warranty", example = "1")
    private Integer mabaohanh;

    @NotNull(message = "masanpham is required")
    @Schema(description = "Product ID", example = "1")
    private Integer masanpham;

    @NotNull(message = "thoigianbaohanh is required")
    @Min(value = 1, message = "thoigianbaohanh must be greater than 0")
    @Schema(description = "Warranty duration in months", example = "12")
    private Integer thoigianbaohanh;

    @Size(max = 500, message = "dieukienbaohanh must not exceed 500 characters")
    @Schema(description = "Warranty conditions", example = "Bảo hành 1 đổi 1 trong 12 tháng nếu lỗi do nhà sản xuất")
    private String dieukienbaohanh;

    @Schema(description = "Creation date of the warranty", example = "2025-05-25T11:17:00")
    private LocalDateTime ngaytao;

    @Schema(description = "Status of the warranty", example = "true")
    private Boolean trangthai;

    @Schema(description = "ID of the creator", example = "1")
    private Integer nguoitao;
}