package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for cauhinhhethong entity")
public class cauhinhhethongdto {

    @Schema(description = "Unique identifier of the system configuration", example = "1")
    private Integer macauhinh;

    @NotBlank(message = "tencauhinh is required")
    @Size(max = 100, message = "tencauhinh must not exceed 100 characters")
    @Schema(description = "Configuration name", example = "Thời gian giao hàng")
    private String tencauhinh;

    @NotBlank(message = "giatri is required")
    @Size(max = 500, message = "giatri must not exceed 500 characters")
    @Schema(description = "Configuration value", example = "48")
    private String giatri;

    @Size(max = 200, message = "mota must not exceed 200 characters")
    @Schema(description = "Configuration description", example = "Thời gian giao hàng tối đa (giờ)")
    private String mota;

    @Schema(description = "Creation date", example = "2025-05-26T22:36:00")
    private LocalDateTime ngaytao;

    @Schema(description = "Status of the configuration", example = "true")
    private Boolean trangthai;

    @Schema(description = "Creator ID", example = "1")
    private Integer nguoitao;
}