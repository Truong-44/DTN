package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for ThanhToan entity")
public class thanhtoandto {

    @Schema(description = "Unique identifier of the payment", example = "1")
    private Integer maThanhToan;

    @NotNull(message = "MaDonHang is required")
    @Schema(description = "ID of the order", example = "1")
    private Integer maDonHang;

    @NotNull(message = "SoTien is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "SoTien must be non-negative")
    @Schema(description = "Payment amount", example = "5000000.00")
    private BigDecimal soTien;

    @NotBlank(message = "PhuongThucThanhToan is required")
    @Pattern(regexp = "^(TienMat|ChuyenKhoan|TheNganHang|ViDienTu)$", message = "PhuongThucThanhToan must be TienMat, ChuyenKhoan, TheNganHang, or ViDienTu")
    @Schema(description = "Payment method", example = "ChuyenKhoan")
    private String phuongThucThanhToan;

    @NotBlank(message = "TrangThai is required")
    @Pattern(regexp = "^(DaThanhToan|ChuaThanhToan|ThatBai)$", message = "TrangThai must be DaThanhToan, ChuaThanhToan, or ThatBai")
    @Schema(description = "Payment status", example = "DaThanhToan")
    private String trangThai;

    @NotNull(message = "NgayThanhToan is required")
    @Schema(description = "Payment date", example = "2025-01-01T10:00:00")
    private LocalDateTime ngayThanhToan;

    @NotNull(message = "NgayTao is required")
    @Schema(description = "Creation date of the payment", example = "2025-01-01T10:00:00")
    private LocalDateTime ngayTao;

    @NotNull(message = "NgayCapNhat is required")
    @Schema(description = "Last update date of the payment", example = "2025-01-02T10:00:00")
    private LocalDateTime ngayCapNhat;
}