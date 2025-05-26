package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for LienHeDatHang entity")
public class lienhedathangdto {

    @Schema(description = "Unique identifier of the contact", example = "1")
    private Integer maLienHe;

    @NotBlank(message = "HoTen is required")
    @Size(max = 100, message = "HoTen must not exceed 100 characters")
    @Schema(description = "Full name of the contact", example = "Nguyen Van A")
    private String hoTen;

    @NotBlank(message = "SoDienThoai is required")
    @Size(max = 15, message = "SoDienThoai must not exceed 15 characters")
    @Schema(description = "Phone number of the contact", example = "0901234567")
    private String soDienThoai;

    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Schema(description = "Email of the contact", example = "vana@example.com")
    private String email;

    @NotNull(message = "MaSanPham is required")
    @Schema(description = "Product ID", example = "1")
    private Integer maSanPham;

    @NotNull(message = "MaChiTietSanPham is required")
    @Schema(description = "Product detail ID", example = "1")
    private Integer maChiTietSanPham;

    @NotNull(message = "SoLuong is required")
    @Min(value = 1, message = "SoLuong must be greater than 0")
    @Schema(description = "Quantity", example = "5")
    private Integer soLuong;

    @Size(max = 500, message = "GhiChu must not exceed 500 characters")
    @Schema(description = "Note", example = "Please deliver before 5 PM")
    private String ghiChu;

    @Schema(description = "Order ID", example = "1")
    private Integer maDonHang;

    @NotNull(message = "NgayLienHe is required")
    @Schema(description = "Contact date", example = "2025-05-24T19:37:00")
    private LocalDateTime ngayLienHe;

    @Schema(description = "Status of the contact", example = "true")
    private Boolean trangThai;

    @Schema(description = "ID of the creator", example = "1")
    private Integer nguoiTao;
}