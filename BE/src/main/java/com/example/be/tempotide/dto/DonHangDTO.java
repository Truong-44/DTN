package com.example.be.tempotide.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DonHangDTO {
    private Integer madonhang;

    @NotNull(message = "Mã khách hàng không được để trống")
    private Integer makhachhang;

    @NotNull(message = "Ngày đặt hàng không được để trống")
    private LocalDateTime ngaydathang;

    @NotBlank(message = "Địa chỉ giao hàng không được để trống")
    @Size(max = 200, message = "Địa chỉ giao hàng không được vượt quá 200 ký tự")
    private String diachigiaohang;

    @NotBlank(message = "Phương thức thanh toán không được để trống")
    private String phuongthucthanhtoan;

    @NotNull(message = "Tổng tiền không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Tổng tiền phải lớn hơn 0")
    private Double tongtien;

    @NotBlank(message = "Phương thức vận chuyển không được để trống")
    @Size(max = 50, message = "Phương thức vận chuyển không được vượt quá 50 ký tự")
    private String phuongthucvanchuyen;

    @NotNull(message = "Số tiền không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Số tiền phải lớn hơn 0")
    private Double sotien;

    @NotBlank(message = "Trạng thái thanh toán không được để trống")
    @Size(max = 50, message = "Trạng thái thanh toán không được vượt quá 50 ký tự")
    private String trangthaithanhtoan;

    @NotNull(message = "Giảm giá không được để trống")
    @DecimalMin(value = "0.0", message = "Giảm giá phải lớn hơn hoặc bằng 0")
    private Double giamgia;

    @NotBlank(message = "Trạng thái đơn hàng không được để trống")
    @Size(max = 50, message = "Trạng thái đơn hàng không được vượt quá 50 ký tự")
    private String trangthaidonhang;

    @Size(max = 100, message = "Tên khách hàng không được vượt quá 100 ký tự")
    private String tenkhachhang;

    @Pattern(regexp = "^\\d{10,11}$", message = "Số điện thoại phải có 10 hoặc 11 chữ số")
    private String sodienthoai;

    @Email(message = "Email không hợp lệ")
    private String email;

    private LocalDateTime ngaythanhtoan;

    @Size(max = 500, message = "Ghi chú không được vượt quá 500 ký tự")
    private String ghichu;

    private Boolean trangthaiHoadon; // Sửa thành Boolean

    private Boolean ladonhangvanglai; // Sửa thành Boolean

    @NotNull(message = "Ngày tạo không được để trống")
    private LocalDateTime ngaytao;

    private LocalDateTime ngaycapnhat;

    private Boolean trangthai;

    private Integer manhanvienxuly;

    private Integer nguoitao;
    private Integer nguoicapnhat;
}