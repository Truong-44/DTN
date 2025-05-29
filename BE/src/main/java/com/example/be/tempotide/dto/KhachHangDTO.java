package com.example.be.tempotide.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KhachHangDTO {
    private Integer makhachhang;

    @NotBlank(message = "Họ không được để trống")
    @Size(max = 50, message = "Họ không được vượt quá 50 ký tự")
    private String ho;

    @NotBlank(message = "Tên không được để trống")
    @Size(max = 50, message = "Tên không được vượt quá 50 ký tự")
    private String ten;

    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String matkhau; // Thêm trường này

    @Pattern(regexp = "^\\d{10,11}$", message = "Số điện thoại phải có 10 hoặc 11 chữ số")
    private String sodienthoai;

    @Min(value = 0, message = "Điểm tích lũy không được âm")
    private Integer diemtichluy; // Thêm trường này

    private Boolean nhanthongbao; // Thêm trường này

    @NotNull(message = "Ngày tạo không được để trống")
    private LocalDateTime ngaytao;

    // Xóa trường ngaycapnhat vì bảng khachhang không có cột này
    private Boolean trangthai;

    private Integer nguoitao;
    private Integer nguoicapnhat;
}