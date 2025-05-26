package com.example.be.TempoTide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for PhienDangNhap entity")
public class PhienDangNhapDto {

    @Schema(description = "Unique identifier of the login session", example = "1")
    private Integer maPhien;

    @NotNull(message = "MaNhanVien is required")
    @Schema(description = "Employee ID", example = "1")
    private Integer maNhanVien;

    @NotBlank(message = "Token is required")
    @Size(max = 500, message = "Token must not exceed 500 characters")
    @Schema(description = "Login token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @NotNull(message = "NgayDangNhap is required")
    @Schema(description = "Login date", example = "2025-05-24T22:00:00")
    private LocalDateTime ngayDangNhap;

    @NotNull(message = "NgayHetHan is required")
    @Schema(description = "Expiration date", example = "2025-05-25T22:00:00")
    private LocalDateTime ngayHetHan;

    @Size(max = 50, message = "DiaChiIP must not exceed 50 characters")
    @Schema(description = "IP address", example = "192.168.1.1")
    private String diaChiIP;

    @Schema(description = "Status of the session", example = "true")
    private Boolean trangThai;
}