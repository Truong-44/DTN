package com.example.tempotide.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class DonHangDTO {
    private Integer madonhang;

    private Integer makhachhang;

    private Integer manhanvien;

    @Size(max = 100, message = "Tên người nhận không được vượt quá 100 ký tự")
    private String tennguoinhan;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Size(max = 15, message = "Số điện thoại không được vượt quá 15 ký tự")
    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại phải là 10 chữ số")
    private String sodienthoai;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(max = 500, message = "Địa chỉ không được vượt quá 500 ký tự")
    private String diachi;

    @DecimalMin(value = "0.0", inclusive = true, message = "Phí vận chuyển phải lớn hơn hoặc bằng 0")
    private BigDecimal phivanchuyen;

    @DecimalMin(value = "0.0", inclusive = true, message = "Tổng tiền phải lớn hơn hoặc bằng 0")
    private BigDecimal tongtien;

    @Size(max = 500, message = "Ghi chú không được vượt quá 500 ký tự")
    private String ghichu;

    @NotBlank(message = "Trạng thái không được để trống")
    @Size(max = 50, message = "Trạng thái không được vượt quá 50 ký tự")
    private String trangthai;

    @NotBlank(message = "Phương thức thanh toán không được để trống")
    @Size(max = 50, message = "Phương thức thanh toán không được vượt quá 50 ký tự")
    private String phuongthucthanhtoan;
}