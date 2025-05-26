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
@Schema(description = "Data Transfer Object for NhaCungCap entity")
public class nhacungcapdto {

    @Schema(description = "Unique identifier of the supplier", example = "1")
    private Integer maNhaCungCap;

    @NotBlank(message = "TenNhaCungCap is required")
    @Size(max = 100, message = "TenNhaCungCap must not exceed 100 characters")
    @Schema(description = "Name of the supplier", example = "ABC Corp")
    private String tenNhaCungCap;

    @NotBlank(message = "NguoiLienHe is required")
    @Size(max = 100, message = "NguoiLienHe must not exceed 100 characters")
    @Schema(description = "Contact person of the supplier", example = "Nguyen Van A")
    private String nguoiLienHe;

    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Schema(description = "Email address of the supplier", example = "abc@corp.com")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$|^$", message = "SoDienThoai must be a 10-digit number or null")
    @Size(max = 15, message = "SoDienThoai must not exceed 15 characters")
    @Schema(description = "Phone number of the supplier", example = "0123456789")
    private String soDienThoai;

    @Size(max = 200, message = "DiaChi must not exceed 200 characters")
    @Schema(description = "Address of the supplier", example = "123 Supplier St, Hanoi")
    private String diaChi;

    @Size(max = 50, message = "MaTaiKhoanNganHang must not exceed 50 characters")
    @Schema(description = "Bank account number of the supplier", example = "1234567890")
    private String maTaiKhoanNganHang;

    @Size(max = 200, message = "TrangWeb must not exceed 200 characters")
    @Schema(description = "Website of the supplier", example = "www.abccorp.com")
    private String trangWeb;

    @NotBlank(message = "MatKhau is required")
    @Size(max = 255, message = "MatKhau must not exceed 255 characters")
    @Schema(description = "Password of the supplier", example = "password123")
    private String matKhau;

    @NotNull(message = "NgayTao is required")
    @Schema(description = "Creation date of the supplier", example = "2025-05-26T12:02:00")
    private LocalDateTime ngayTao;

    @NotNull(message = "NgayCapNhat is required")
    @Schema(description = "Last update date of the supplier", example = "2025-05-26T12:02:00")
    private LocalDateTime ngayCapNhat;

    @NotNull(message = "TrangThai is required")
    @Schema(description = "Status of the supplier", example = "true")
    private Boolean trangThai;

    @Schema(description = "ID of the employee who created this record", example = "2")
    private Integer nguoiTaoId;

    @Schema(description = "ID of the employee who last updated this record", example = "3")
    private Integer nguoiCapNhatId;
}