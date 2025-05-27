package com.example.tempotide.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SanPhamDTO {
    private Integer masanpham;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 100, message = "Tên sản phẩm không được vượt quá 100 ký tự")
    private String tensanpham;

    @Size(max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    private String mota;

    @NotNull(message = "Giá niêm yết không được để trống")
    @DecimalMin(value = "0.0", message = "Giá niêm yết phải lớn hơn hoặc bằng 0")
    private BigDecimal gianiemyet;

    @NotNull(message = "Giá bán mặc định không được để trống")
    @DecimalMin(value = "0.0", message = "Giá bán mặc định phải lớn hơn hoặc bằng 0")
    private BigDecimal giabanmacdinh;

    @NotNull(message = "Mã danh mục không được để trống")
    private Integer madanhmuc;

    @NotNull(message = "Mã nhà cung cấp không được để trống")
    private Integer manhacungcap;

    private Integer mathuonghieu;

    @Size(max = 255, message = "Hình ảnh mặc định không được vượt quá 255 ký tự")
    private String hinhanhmacdinh;

    private Boolean trangthai;

    private Boolean trangthaiban;

    private LocalDateTime ngaytao;

    private LocalDateTime ngaycapnhat;

    private Integer nguoitao;

    private Integer nguoicapnhat;

    private Integer nguoiduyet;
}