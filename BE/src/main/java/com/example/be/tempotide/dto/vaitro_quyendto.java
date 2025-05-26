package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for VaiTro_Quyen entity")
public class vaitro_quyendto {

    @Schema(description = "Role ID", example = "1")
    @NotNull(message = "MaVaiTro is required")
    private Integer maVaiTro;

    @Schema(description = "Permission ID", example = "1")
    @NotNull(message = "MaQuyen is required")
    private Integer maQuyen;

    @Schema(description = "Status of the role-permission mapping", example = "true")
    private Boolean trangThai;
}