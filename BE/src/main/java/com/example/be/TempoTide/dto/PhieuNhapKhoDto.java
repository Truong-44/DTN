package com.example.be.TempoTide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for PhieuNhapKho entity")
public class PhieuNhapKhoDto {

    @Schema(description = "Unique identifier of the import receipt", example = "1")
    private Integer maPhieuNhap;

    @NotNull(message = "MaKho is required")
    @Schema(description = "Warehouse ID", example = "1")
    private Integer maKho;

    @NotNull(message = "MaNhaCungCap is required")
    @Schema(description = "Supplier ID", example = "1")
    private Integer maNhaCungCap;

    @NotNull(message = "TongTien is required")
    @Schema(description = "Total amount", example = "5000000.0")
    private Double tongTien;

    @NotNull(message = "NgayNhap is required")
    @Schema(description = "Import date", example = "2025-05-24T20:45:00")
    private LocalDateTime ngayNhap;

    @Size(max = 500, message = "GhiChu must not exceed 500 characters")
    @Schema(description = "Note", example = "Import successful")
    private String ghiChu;

    @Schema(description = "Status of the receipt", example = "true")
    private Boolean trangThai;

    @Schema(description = "ID of the creator", example = "1")
    private Integer nguoiTao;

    @Schema(description = "ID of the last updater", example = "1")
    private Integer nguoiCapNhat;
}