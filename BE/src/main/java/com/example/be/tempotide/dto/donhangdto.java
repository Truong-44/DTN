package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
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
public class donhangdto {

    @Schema(description = "Unique identifier of the order", example = "1")
    private Integer maDonHang;

    @NotNull(message = "MaKhachHang is required")
    @Schema(description = "ID of the customer", example = "1")
    private Integer maKhachHang;

    @NotNull(message = "NgayDatHang is required")
    @Schema(description = "Order date", example = "2025-05-26T11:37:00")
    private LocalDateTime ngayDatHang;

    @NotNull(message = "TongTien is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "TongTien must be greater than 0")
    @Schema(description = "Total amount of the order", example = "5000000.00")
    private BigDecimal tongTien;

    @NotNull(message = "MaPhuongThucVanChuyen is required")
    @Schema(description = "ID of the shipping method", example = "1")
    private Integer maPhuongThucVanChuyen;

    @NotNull(message = "MaDiaChiGiaoHang is required")
    @Schema(description = "ID of the delivery address", example = "1")
    private Integer maDiaChiGiaoHang;

    @NotBlank(message = "TrangThaiDonHang is required")
    @Pattern(regexp = "^(ChoXacNhan|DaXacNhan|DangGiao|HoanThanh|Huy)$", message = "Invalid TrangThaiDonHang")
    @Schema(description = "Status of the order", example = "DaXacNhan")
    private String trangThaiDonHang;

    @Schema(description = "ID of the promotion applied", example = "1")
    private Integer maKhuyenMai;

    @NotNull(message = "NgayTao is required")
    @Schema(description = "Creation date of the order", example = "2025-05-26T11:37:00")
    private LocalDateTime ngayTao;

    @NotNull(message = "NgayCapNhat is required")
    @Schema(description = "Last update date of the order", example = "2025-05-26T11:37:00")
    private LocalDateTime ngayCapNhat;

    @NotNull(message = "TrangThai is required")
    @Schema(description = "Status of the order record", example = "true")
    private Boolean trangThai;

    @Schema(description = "ID of the employee who created this record", example = "2")
    private Integer nguoiTaoId;

    @Schema(description = "ID of the employee who last updated this record", example = "3")
    private Integer nguoiCapNhatId;
}