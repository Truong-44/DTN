package com.example.temp;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChiTietSanPhamDTO {
    private Long machitietsanpham;

    @NotNull(message = "Mã sản phẩm không được để trống")
    private long masanpham;

    @NotNull(message = "Mã thuộc tính không được để trống")
    private long mangle;

    @NotBlank(message = "Giá trị thuộc tính không được để trống")
    @Size(max = 100, message = "Giá trị thuộc tính không được vượt quá 100 ký tự")
    private String giatri;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.00", inclusive = true, message = "Giá phải lớn hơn hoặc bằng 0")
    private BigDecimal gia;

    @NotNull(message = "Số lượng tồn không được để trống")
    @Min(value = 0, message = "Số lượng tồn phải lớn hơn hoặc bằng 0")
    private Long soluongton;

    @Size(max = 50, message = "SKU không được vượt quá 50 ký tự")
    private String sku;

    @Size(max = 500, message = "Đường dẫn hình ảnh không được vượt quá 500 ký tự")
    private String duongdanhinhanh;

    private Boolean lahinhchinh;

    private Boolean trangthai;
}