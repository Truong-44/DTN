package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for KhoHang entity")
public class khohangdto {

    @Schema(description = "Unique identifier of the warehouse", example = "1")
    private Integer maKho;

    @NotBlank(message = "TenKho is required")
    @Size(max = 100, message = "TenKho must not exceed 100 characters")
    @Schema(description = "Name of the warehouse", example = "Kho Hà Nội")
    private String tenKho;

    @NotBlank(message = "DiaChi is required")
    @Size(max = 200, message = "DiaChi must not exceed 200 characters")
    @Schema(description = "Address of the warehouse", example = "123 Đường ABC, Hà Nội")
    private String diaChi;

    @Size(max = 15, message = "SoDienThoai must not exceed 15 characters")
    @Schema(description = "Phone number of the warehouse", example = "0901234567")
    private String soDienThoai;

    @NotNull(message = "DungLuongToiDa is required")
    @Schema(description = "Maximum capacity of the warehouse", example = "1000")
    private Integer dungLuongToiDa;

    @NotNull(message = "DungLuongHienTai is required")
    @Schema(description = "Current capacity of the warehouse", example = "500")
    private Integer dungLuongHienTai;

    @NotNull(message = "NgayTao is required")
    @Schema(description = "Creation date of the warehouse", example = "2025-05-24T20:10:00")
    private LocalDateTime ngayTao;

    @Schema(description = "Status of the warehouse", example = "true")
    private Boolean trangThai;

    @Schema(description = "ID of the creator", example = "1")
    private Integer nguoiTao;

    @Schema(description = "ID of the last updater", example = "1")
    private Integer nguoiCapNhat;
}