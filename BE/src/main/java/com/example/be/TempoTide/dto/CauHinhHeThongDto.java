package com.example.be.TempoTide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for CauHinhHeThong entity")
public class CauHinhHeThongDto {

    @Schema(description = "Unique identifier of the system configuration", example = "1")
    private Integer maCauHinh;

    @NotBlank(message = "TenCauHinh is required")
    @Size(max = 100, message = "TenCauHinh must not exceed 100 characters")
    @Schema(description = "Configuration name", example = "Thời gian giao hàng")
    private String tenCauHinh;

    @NotBlank(message = "GiaTri is required")
    @Size(max = 500, message = "GiaTri must not exceed 500 characters")
    @Schema(description = "Configuration value", example = "48")
    private String giaTri;

    @Size(max = 1000, message = "MoTa must not exceed 1000 characters")
    @Schema(description = "Configuration description", example = "Thời gian giao hàng tối đa (giờ)")
    private String moTa;

    @Schema(description = "Status of the configuration", example = "true")
    private Boolean trangThai;
}