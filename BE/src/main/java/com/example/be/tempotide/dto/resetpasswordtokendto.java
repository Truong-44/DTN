package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for ResetPasswordToken entity")
public class resetpasswordtokendto {

    @Schema(description = "Unique identifier of the token", example = "1")
    private Integer maToken;

    @NotNull(message = "MaNhanVien is required")
    @Schema(description = "Employee ID", example = "1")
    private Integer maNhanVien;

    @NotNull(message = "MaKhachHang is required")
    @Schema(description = "Customer ID", example = "1")
    private Integer maKhachHang;

    @NotBlank(message = "Token is required")
    @Size(max = 500, message = "Token must not exceed 500 characters")
    @Schema(description = "Reset password token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @NotNull(message = "NgayTao is required")
    @Schema(description = "Creation date", example = "2025-05-26T13:24:00")
    private LocalDateTime ngayTao;

    @NotNull(message = "NgayHetHan is required")
    @Schema(description = "Expiration date", example = "2025-05-27T13:24:00")
    private LocalDateTime ngayHetHan;

    @NotNull(message = "TrangThai is required")
    @Schema(description = "Status of the token", example = "true")
    private Boolean trangThai;

    @Schema(description = "Whether the token has been used", example = "false")
    private Boolean daSuDung;

    @Schema(description = "ID of the employee who created this record", example = "2")
    private Integer nguoiTaoId;
}