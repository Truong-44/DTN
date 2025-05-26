package com.example.be.TempoTide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for ResetPasswordToken entity")
public class ResetPasswordTokenDto {

    @Schema(description = "Unique identifier of the token", example = "1")
    private Integer maToken;

    @NotNull(message = "MaNguoiDung is required")
    @Schema(description = "User ID", example = "1")
    private Integer maNguoiDung;

    @NotBlank(message = "Token is required")
    @Size(max = 500, message = "Token must not exceed 500 characters")
    @Schema(description = "Reset password token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @NotNull(message = "NgayTao is required")
    @Schema(description = "Creation date", example = "2025-05-24T22:40:00")
    private LocalDateTime ngayTao;

    @NotNull(message = "NgayHetHan is required")
    @Schema(description = "Expiration date", example = "2025-05-25T22:40:00")
    private LocalDateTime ngayHetHan;

    @Schema(description = "Whether the token has been used", example = "false")
    private Boolean daSuDung;
}