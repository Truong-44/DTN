package com.example.be.TempoTide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for LichSuDonHang entity")
public class LichSuDonHangDto {

    @Schema(description = "Unique identifier of the order history", example = "1")
    private Integer maLichSu;

    @NotNull(message = "MaDonHang is required")
    @Schema(description = "Order ID", example = "1")
    private Integer maDonHang;

    @Size(max = 50, message = "TrangThaiCu must not exceed 50 characters")
    @Schema(description = "Previous status of the order", example = "Chờ xử lý")
    private String trangThaiCu;

    @Size(max = 50, message = "TrangThaiMoi must not exceed 50 characters")
    @Schema(description = "New status of the order", example = "Đang xử lý")
    private String trangThaiMoi;

    @Size(max = 500, message = "LyDo must not exceed 500 characters")
    @Schema(description = "Reason for the status change", example = "Order confirmed by staff")
    private String lyDo;

    @NotNull(message = "NgayCapNhat is required")
    @Schema(description = "Update date of the order history", example = "2025-05-24T18:49:00")
    private LocalDateTime ngayCapNhat;

    @Schema(description = "ID of the updater", example = "1")
    private Integer nguoiCapNhat;
}