package com.example.be.tempotide.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DonHangDTO {
    private Integer madonhang;

    private Integer makhachhang;
    private Integer manhanvienxuly;

    private LocalDateTime ngaydathang;

    @NotNull(message = "Tổng tiền không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Tổng tiền phải lớn hơn hoặc bằng 0")
    private Double tongtien;

    @NotBlank(message = "Địa chỉ giao hàng không được để trống")
    @Size(max = 200, message = "Địa chỉ giao hàng không được vượt quá 200 ký tự")
    private String diachigiaohang;

    @NotBlank(message = "Phương thức vận chuyển không được để trống")
    @Size(max = 50, message = "Phương thức vận chuyển không được vượt quá 50 ký tự")
    private String phuongthucvanchuyen;

    @NotBlank(message = "Phương thức thanh toán không được để trống")
    @Size(max = 50, message = "Phương thức thanh toán không được vượt quá 50 ký tự")
    private String phuongthucthanhtoan;

    @NotNull(message = "Số tiền không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Số tiền phải lớn hơn hoặc bằng 0")
    private Double sotien;

    @NotBlank(message = "Trạng thái thanh toán không được để trống")
    @Size(max = 50, message = "Trạng thái thanh toán không được vượt quá 50 ký tự")
    @Pattern(regexp = "^(Chờ xử lý|Hoàn thành|Thất bại)$", message = "Trạng thái thanh toán không hợp lệ")
    private String trangthaithanhtoan;

    @DecimalMin(value = "0.0", inclusive = true, message = "Giảm giá phải lớn hơn hoặc bằng 0")
    private Double giamgia;

    @NotBlank(message = "Trạng thái đơn hàng không được để trống")
    @Size(max = 50, message = "Trạng thái đơn hàng không được vượt quá 50 ký tự")
    @Pattern(regexp = "^(Chờ xử lý|Đang xử lý|Đã giao|Hoàn thành|Đã hủy|Đặt trước)$", message = "Trạng thái đơn hàng không hợp lệ")
    private String trangthaidonhang;

    @Size(max = 100, message = "Tên khách hàng không được vượt quá 100 ký tự")
    private String tenkhachhang;

    @Size(max = 15, message = "Số điện thoại không được vượt quá 15 ký tự")
    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại phải là 10 chữ số")
    private String sodienthoai;

    @Email(message = "Email không hợp lệ")
    @Size(max = 100, message = "Email không được vượt quá 100 ký tự")
    private String email;

    private LocalDateTime ngaythanhtoan;

    @Size(max = 500, message = "Ghi chú không được vượt quá 500 ký tự")
    private String ghichu;

    private Boolean trangthaiHoadon;
    private Boolean ladonhangvanglai;

    private LocalDateTime ngaytao;
    private LocalDateTime ngaycapnhat;
    private Boolean trangthai;
}