package com.example.be.TempoTide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for Quyen entity")
public class QuyenDto {

    @Schema(description = "Unique identifier of the permission", example = "1")
    private Integer maQuyen;

    @NotBlank(message = "TenQuyen is required")
    @Size(max = 50, message = "TenQuyen must not exceed 50 characters")
    @Schema(description = "Name of the permission", example = "ROLE_ADMIN")
    private String tenQuyen;

    @Size(max = 255, message = "MoTa must not exceed 255 characters")
    @Schema(description = "Description of the permission", example = "Admin role with full access")
    private String moTa;

    @NotNull(message = "NgayTao is required")
    @Schema(description = "Creation date of the permission", example = "2025-05-24T18:26:00")
    private LocalDateTime ngayTao;

    @NotNull(message = "NgayCapNhat is required")
    @Schema(description = "Last update date of the permission", example = "2025-05-24T18:26:00")
    private LocalDateTime ngayCapNhat;
}