package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for NhanVien entity")
public class nhanviendto {

    @Schema(description = "Unique identifier of the employee", example = "1")
    private Integer maNhanVien;

    @NotBlank(message = "Ho is required")
    @Size(max = 50, message = "Ho must not exceed 50 characters")
    @Schema(description = "Last name of the employee", example = "Nguyen")
    private String ho;

    @NotBlank(message = "Ten is required")
    @Size(max = 50, message = "Ten must not exceed 50 characters")
    @Schema(description = "First name of the employee", example = "Van A")
    private String ten;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Schema(description = "Email address of the employee", example = "nva@example.com")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$|^$", message = "SoDienThoai must be a 10-digit number or null")
    @Size(max = 15, message = "SoDienThoai must not exceed 15 characters")
    @Schema(description = "Phone number of the employee", example = "0123456789")
    private String soDienThoai;

    @NotNull(message = "NgayTuyenDung is required")
    @Schema(description = "Hire date of the employee", example = "2023-01-01")
    private LocalDate ngayTuyenDung;

    @Size(max = 50, message = "MaSoThue must not exceed 50 characters")
    @Schema(description = "Tax code of the employee", example = "1234567890")
    private String maSoThue;

    @Schema(description = "Termination date of the employee", example = "2024-12-31")
    private LocalDate ngayNghiViec;

    @NotBlank(message = "MatKhau is required")
    @Size(max = 255, message = "MatKhau must not exceed 255 characters")
    @Schema(description = "Password of the employee", example = "password123")
    private String matKhau;

    @NotNull(message = "TrangThai is required")
    @Schema(description = "Status of the employee", example = "true")
    private Boolean trangThai;

    @NotNull(message = "NgayTao is required")
    @Schema(description = "Creation date of the employee record", example = "2025-05-26T13:11:00")
    private LocalDateTime ngayTao;

    @NotNull(message = "NgayCapNhat is required")
    @Schema(description = "Last update date of the employee record", example = "2025-05-26T13:11:00")
    private LocalDateTime ngayCapNhat;

    @Schema(description = "ID of the employee who created this record", example = "2")
    private Integer nguoiTaoId;

    @Schema(description = "ID of the employee who last updated this record", example = "3")
    private Integer nguoiCapNhatId;
}