package com.example.be.TempoTideBE.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class SanPhamDTO {
    @Schema(description = "Mã sản phẩm", example = "1")
    private Integer maSanPham;

    @Schema(description = "Tên sản phẩm", example = "Sofa cao cấp 3 chỗ", required = true)
    private String tenSanPham;

    @Schema(description = "Mô tả sản phẩm", example = "Sofa thiết kế hiện đại")
    private String moTa;

    @Schema(description = "Giá niêm yết", example = "16000000", required = true)
    private Double giaNiemYet;

    @Schema(description = "Giá bán mặc định", example = "15000000", required = true)
    private Double giaBanMacDinh;

    @Schema(description = "Mã danh mục", example = "1", required = true)
    private Integer maDanhMuc;

    @Schema(description = "Mã nhà cung cấp", example = "1", required = true)
    private Integer maNhaCungCap;

    @Schema(description = "Mã thương hiệu", example = "1")
    private Integer maThuongHieu;

    @Schema(description = "Hình ảnh mặc định", example = "images/sofa_xanh_main.jpg")
    private String hinhAnhMacDinh;

    @Schema(description = "Trạng thái", example = "true")
    private Boolean trangThai;

    @Schema(description = "Trạng thái bán", example = "true")
    private Boolean trangThaiBan;

    @Schema(description = "Ngày tạo", example = "2025-05-21T06:42:00")
    private LocalDateTime ngayTao;

    @Schema(description = "Ngày cập nhật", example = "2025-05-21T06:42:00")
    private LocalDateTime ngayCapNhat;

    @Schema(description = "Mã người tạo", example = "1")
    private Integer nguoiTao;

    @Schema(description = "Mã người cập nhật", example = "1")
    private Integer nguoiCapNhat;

    @Schema(description = "Mã người duyệt", example = "1")
    private Integer nguoiDuyet;
}