package com.example.be.TempoTide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for DonHang entity")
public class DonHangDto {

    @Schema(description = "Unique identifier of the order", example = "1")
    private Integer maDonHang;

    @NotNull(message = "MaKhachHang is required")
    @Schema(description = "ID of the customer", example = "1")
    private Integer maKhachHang;

    @NotNull(message = "TongGiaTri is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "TongGiaTri must be greater than 0")
    @Schema(description = "Total value of the order", example = "5000000.00")
    private BigDecimal tongGiaTri;

    @NotBlank(message = "TrangThaiDonHang is required")
    @Pattern(regexp = "^(ChoXacNhan|DaXacNhan|DangGiao|HoanThanh|Huy)$", message = "Invalid TrangThaiDonHang")
    @Schema(description = "Status of the order", example = "DaXacNhan")
    private String trangThaiDonHang;

    @Schema(description = "ID of the promotion applied", example = "1")
    private Integer maKhuyenMaiId;

    @NotNull(message = "NgayTao is required")
    @Schema(description = "Creation date of the order", example = "2023-01-01T10:00:00")
    private LocalDateTime ngayTao;

    @NotNull(message = "NgayCapNhat is required")
    @Schema(description = "Last update date of the order", example = "2023-01-02T10:00:00")
    private LocalDateTime ngayCapNhat;

    @NotNull(message = "TrangThai is required")
    @Schema(description = "Status of the order record", example = "true")
    private Boolean trangThai;

    @Schema(description = "ID of the employee who created this record", example = "2")
    private Integer nguoiTaoId;

    @Schema(description = "ID of the employee who last updated this record", example = "3")
    private Integer nguoiCapNhatId;
}
