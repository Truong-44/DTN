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
@Schema(description = "Data Transfer Object for KhuyenMai entity")
public class khuyenmaidto {

    @Schema(description = "Unique identifier of the promotion", example = "1")
    private Integer maKhuyenMai;

    @NotBlank(message = "TenKhuyenMai is required")
    @Size(max = 100, message = "TenKhuyenMai must not exceed 100 characters")
    @Schema(description = "Name of the promotion", example = "Giam 20%")
    private String tenKhuyenMai;

    @Size(max = 500, message = "MoTa must not exceed 500 characters")
    @Schema(description = "Description of the promotion", example = "Khuyen mai 20% cho don hang tren 1 trieu")
    private String moTa;

    @NotNull(message = "PhanTramGiamGia is required")
    @Min(value = 0, message = "PhanTramGiamGia must be between 0 and 100")
    @Max(value = 100, message = "PhanTramGiamGia must be between 0 and 100")
    @Schema(description = "Discount percentage", example = "20")
    private BigDecimal phanTramGiamGia;

    @Size(max = 500, message = "DieuKienApDung must not exceed 500 characters")
    @Schema(description = "Conditions for applying the promotion", example = "Don hang tren 1 trieu")
    private String dieuKienApDung;

    @NotNull(message = "SoLuongApDung is required")
    @Min(value = 0, message = "SoLuongApDung must be non-negative")
    @Schema(description = "Quantity of the promotion available", example = "100")
    private Integer soLuongApDung;

    @NotNull(message = "ApDungChoDatTruoc is required")
    @Schema(description = "Whether the promotion applies to pre-orders", example = "false")
    private Boolean apDungChoDatTruoc;

    @NotNull(message = "NgayBatDau is required")
    @Schema(description = "Start date of the promotion", example = "2025-05-26T11:53:00")
    private LocalDateTime ngayBatDau;

    @NotNull(message = "NgayKetThuc is required")
    @Schema(description = "End date of the promotion", example = "2025-06-26T11:53:00")
    private LocalDateTime ngayKetThuc;

    @NotNull(message = "NgayTao is required")
    @Schema(description = "Creation date of the promotion", example = "2025-05-26T11:53:00")
    private LocalDateTime ngayTao;

    @NotNull(message = "NgayCapNhat is required")
    @Schema(description = "Last update date of the promotion", example = "2025-05-26T11:53:00")
    private LocalDateTime ngayCapNhat;

    @NotNull(message = "TrangThai is required")
    @Schema(description = "Status of the promotion", example = "true")
    private Boolean trangThai;

    @Schema(description = "ID of the employee who created this record", example = "1")
    private Integer nguoiTaoId;

    @Schema(description = "ID of the employee who last updated this record", example = "2")
    private Integer nguoiCapNhatId;
}