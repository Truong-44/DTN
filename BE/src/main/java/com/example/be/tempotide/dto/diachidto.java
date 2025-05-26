package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for DiaChi entity")
public class diachidto {

    @Schema(description = "Unique identifier of the address", example = "1")
    private Integer maDiaChi;

    @NotNull(message = "MaKhachHang is required")
    @Schema(description = "Customer ID", example = "1")
    private Integer maKhachHang;

    @NotBlank(message = "DiaChiCuThe is required")
    @Size(max = 500, message = "DiaChiCuThe must not exceed 500 characters")
    @Schema(description = "Detailed address", example = "123 Đường Láng")
    private String diaChiCuThe;

    @NotBlank(message = "PhuongXa is required")
    @Size(max = 100, message = "PhuongXa must not exceed 100 characters")
    @Schema(description = "Ward", example = "Phường Láng Thượng")
    private String phuongXa;

    @NotBlank(message = "QuanHuyen is required")
    @Size(max = 100, message = "QuanHuyen must not exceed 100 characters")
    @Schema(description = "District", example = "Quận Đống Đa")
    private String quanHuyen;

    @NotBlank(message = "TinhThanh is required")
    @Size(max = 100, message = "TinhThanh must not exceed 100 characters")
    @Schema(description = "City/Province", example = "Hà Nội")
    private String tinhThanh;

    @Schema(description = "Whether this is the default address", example = "false")
    private Boolean laDiaChiMacDinh;

    @Schema(description = "Status of the address", example = "true")
    private Boolean trangThai;
}