package com.example.be.tempotide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "Data Transfer Object for ThuongHieu entity")
public class thuonghieudto {

    @Schema(description = "Unique identifier of the brand", example = "1")
    private Integer maThuongHieu;

    @NotBlank(message = "TenThuongHieu is required")
    @Size(max = 100, message = "TenThuongHieu must not exceed 100 characters")
    @Schema(description = "Name of the brand", example = "Samsung")
    private String tenThuongHieu;

    @Size(max = 500, message = "MoTa must not exceed 500 characters")
    @Schema(description = "Description of the brand", example = "Leading electronics brand")
    private String moTa;

    @Size(max = 500, message = "HinhAnh must not exceed 500 characters")
    @Schema(description = "Image URL of the brand", example = "http://example.com/brand.jpg")
    private String hinhAnh;

    @NotNull(message = "NgayTao is required")
    @Schema(description = "Creation date of the brand", example = "2023-01-01T10:00:00")
    private LocalDateTime ngayTao;

    @NotNull(message = "NgayCapNhat is required")
    @Schema(description = "Last update date of the brand", example = "2023-01-02T10:00:00")
    private LocalDateTime ngayCapNhat;

    @NotNull(message = "TrangThai is required")
    @Schema(description = "Status of the brand", example = "true")
    private Boolean trangThai;

    @Schema(description = "ID of the employee who created this record", example = "2")
    private Integer nguoiTaoId;

    @Schema(description = "ID of the employee who last updated this record", example = "3")
    private Integer nguoiCapNhatId;
}
