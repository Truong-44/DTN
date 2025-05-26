package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for GioHang entity")
public class giohangdto {

    @Schema(description = "Unique identifier of the cart", example = "1")
    private Integer maGioHang;

    @NotNull(message = "MaKhachHang is required")
    @Schema(description = "ID of the customer", example = "1")
    private Integer maKhachHang;

    @NotNull(message = "NgayTao is required")
    @Schema(description = "Creation date of the cart", example = "2025-01-01T10:00:00")
    private LocalDateTime ngayTao;

    @NotNull(message = "NgayCapNhat is required")
    @Schema(description = "Last update date of the cart", example = "2025-01-02T10:00:00")
    private LocalDateTime ngayCapNhat;

    @NotNull(message = "TrangThai is required")
    @Schema(description = "Status of the cart", example = "true")
    private Boolean trangThai;
}