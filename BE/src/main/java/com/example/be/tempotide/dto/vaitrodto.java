package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for VaiTro entity")
public class vaitrodto {

    @Schema(description = "Unique identifier of the role", example = "1")
    private Integer maVaiTro;

    @NotBlank(message = "TenVaiTro is required")
    @Size(max = 50, message = "TenVaiTro must not exceed 50 characters")
    @Schema(description = "Name of the role", example = "ADMIN")
    private String tenVaiTro;

    @Size(max = 255, message = "MoTa must not exceed 255 characters")
    @Schema(description = "Description of the role", example = "Administrator role")
    private String moTa;

    @Schema(description = "List of permission IDs associated with the role", example = "[1, 2]")
    private List<Integer> quyenIds;

    @NotNull(message = "NgayTao is required")
    @Schema(description = "Creation date of the role", example = "2025-05-24T18:34:00")
    private LocalDateTime ngayTao;

    @NotNull(message = "NgayCapNhat is required")
    @Schema(description = "Last update date of the role", example = "2025-05-24T18:34:00")
    private LocalDateTime ngayCapNhat;
}