package com.example.be.tempotide.dto;

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
@Schema(description = "Data Transfer Object for PhieuNhapHang entity")
public class phieunhaphangdto {

    @Schema(description = "Unique identifier of the import voucher", example = "1")
    private Integer maPhieuNhap;

    @NotNull(message = "MaNhaCungCap is required")
    @Schema(description = "ID of the supplier", example = "1")
    private Integer maNhaCungCap;

    @NotNull(message = "NgayNhap is required")
    @Schema(description = "Import date", example = "2025-01-01T10:00:00")
    private LocalDateTime ngayNhap;

    @NotNull(message = "TongTien is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "TongTien must be non-negative")
    @Schema(description = "Total amount of the import", example = "5000000.00")
    private BigDecimal tongTien;

    @NotBlank(message = "TrangThai is required")
    @Pattern(regexp = "^(Moi|DaXacNhan|HoanThanh|Huy)$", message = "TrangThai must be Moi, DaXacNhan, HoanThanh, or Huy")
    @Schema(description = "Status of the import voucher", example = "Moi")
    private String trangThai;

    @NotNull(message = "NgayTao is required")
    @Schema(description = "Creation date of the import voucher", example = "2025-01-01T10:00:00")
    private LocalDateTime ngayTao;

    @NotNull(message = "NgayCapNhat is required")
    @Schema(description = "Last update date of the import voucher", example = "2025-01-02T10:00:00")
    private LocalDateTime ngayCapNhat;

    @Schema(description = "ID of the employee who created this record", example = "2")
    private Integer nguoiTaoId;

    @Schema(description = "ID of the employee who last updated this record", example = "3")
    private Integer nguoiCapNhatId;
}
