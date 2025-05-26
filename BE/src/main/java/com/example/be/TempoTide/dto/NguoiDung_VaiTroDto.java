package com.example.be.TempoTide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for NguoiDung_VaiTro entity")
public class NguoiDung_VaiTroDto {

    @Schema(description = "User ID", example = "1")
    @NotNull(message = "MaNguoiDung is required")
    private Integer maNguoiDung;

    @Schema(description = "Role ID", example = "1")
    @NotNull(message = "MaVaiTro is required")
    private Integer maVaiTro;

    @Schema(description = "Status of the user-role mapping", example = "true")
    private Boolean trangThai;
}