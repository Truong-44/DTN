package com.example.be.TempoTide.dto;

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
@Schema(description = "Data Transfer Object for KhachHang entity")
public class KhachHangDto {

    @Schema(description = "Unique identifier of the customer", example = "1")
    private Integer maKhachHang;

    @NotBlank(message = "Ho is required")
    @Size(max = 50, message = "Ho must not exceed 50 characters")
    @Schema(description = "Last name of the customer", example = "Tran")
    private String ho;

    @NotBlank(message = "Ten is required")
    @Size(max = 50, message = "Ten must not exceed 50 characters")
    @Schema(description = "First name of the customer", example = "Thi B")
    private String ten;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Schema(description = "Email address of the customer", example = "ttb@example.com")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$|^$", message = "SoDienThoai must be a 10-digit number or null")
    @Size(max = 15, message = "SoDienThoai must not exceed 15 characters")
    @Schema(description = "Phone number of the customer", example = "0987654321")
    private String soDienThoai;

    @Schema(description = "Birth date of the customer", example = "1990-01-01")
    private LocalDate ngaySinh;

    @NotNull(message = "LaKhachVangLai is required")
    @Schema(description = "Whether the customer is a guest", example = "false")
    private Boolean laKhachVangLai;

    @NotNull(message = "DiemTichLuy is required")
    @Min(value = 0, message = "DiemTichLuy must be non-negative")
    @Schema(description = "Loyalty points of the customer", example = "100")
    private Integer diemTichLuy;

    @Schema(description = "ID of the customer rank", example = "1")
    private Integer maCapBacId;

    @NotNull(message = "NgayTao is required")
    @Schema(description = "Creation date of the customer record", example = "2023-01-01T10:00:00")
    private LocalDateTime ngayTao;

    @NotNull(message = "NgayCapNhat is required")
    @Schema(description = "Last update date of the customer record", example = "2023-01-02T10:00:00")
    private LocalDateTime ngayCapNhat;

    @NotNull(message = "TrangThai is required")
    @Schema(description = "Status of the customer", example = "true")
    private Boolean trangThai;

    @Schema(description = "ID of the employee who created this record", example = "2")
    private Integer nguoiTaoId;

    @Schema(description = "ID of the employee who last updated this record", example = "3")
    private Integer nguoiCapNhatId;

}
