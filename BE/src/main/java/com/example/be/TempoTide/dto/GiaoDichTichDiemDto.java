package com.example.be.TempoTide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for GiaoDichTichDiem entity")
public class GiaoDichTichDiemDto {

    @Schema(description = "Unique identifier of the loyalty transaction", example = "1")
    private Integer maGiaoDich;

    @NotNull(message = "MaKhachHang is required")
    @Schema(description = "ID of the customer", example = "1")
    private Integer maKhachHang;

    @NotNull(message = "SoDiem is required")
    @Schema(description = "Number of points (positive or negative)", example = "100")
    private Integer soDiem;

    @NotBlank(message = "LoaiGiaoDich is required")
    @Pattern(regexp = "^(Tich|Tieu)$", message = "LoaiGiaoDich must be either 'Tich' or 'Tieu'")
    @Schema(description = "Type of transaction (Tich: Earn, Tieu: Spend)", example = "Tich")
    private String loaiGiaoDich;

    @Size(max = 500, message = "MoTa must not exceed 500 characters")
    @Schema(description = "Description of the transaction", example = "Earned points from order #123")
    private String moTa;

    @NotNull(message = "NgayTao is required")
    @Schema(description = "Creation date of the transaction", example = "2023-01-01T10:00:00")
    private LocalDateTime ngayTao;

    @NotNull(message = "NgayCapNhat is required")
    @Schema(description = "Last update date of the transaction", example = "2023-01-02T10:00:00")
    private LocalDateTime ngayCapNhat;

    @NotNull(message = "TrangThai is required")
    @Schema(description = "Status of the transaction", example = "true")
    private Boolean trangThai;

    @Schema(description = "ID of the employee who created this record", example = "2")
    private Integer nguoiTaoId;

    @Schema(description = "ID of the employee who last updated this record", example = "3")
    private Integer nguoiCapNhatId;
}
