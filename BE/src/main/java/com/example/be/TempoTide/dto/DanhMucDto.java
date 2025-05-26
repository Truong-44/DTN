package com.example.be.TempoTide.dto;

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
@Schema(description = "Data Transfer Object for DanhMuc entity")
public class DanhMucDto {

    @Schema(description = "Unique identifier of the category", example = "1")
    private Integer maDanhMuc;

    @NotBlank(message = "TenDanhMuc is required")
    @Size(max = 100, message = "TenDanhMuc must not exceed 100 characters")
    @Schema(description = "Name of the category", example = "Electronics")
    private String tenDanhMuc;

    @Schema(description = "ID of the parent category", example = "0")
    private Integer maDanhMucChaId;

    @NotNull(message = "NgayTao is required")
    @Schema(description = "Creation date of the category", example = "2023-01-01T10:00:00")
    private LocalDateTime ngayTao;

    @NotNull(message = "NgayCapNhat is required")
    @Schema(description = "Last update date of the category", example = "2023-01-02T10:00:00")
    private LocalDateTime ngayCapNhat;

    @NotNull(message = "TrangThai is required")
    @Schema(description = "Status of the category", example = "true")
    private Boolean trangThai;

    @Schema(description = "ID of the employee who created this record", example = "2")
    private Integer nguoiTaoId;

    @Schema(description = "ID of the employee who last updated this record", example = "3")
    private Integer nguoiCapNhatId;
}
