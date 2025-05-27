package com.example.be.tempotide.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KhachHangDTO {
    private Integer makhachhang;

    @NotBlank(message = "Họ không được để trống")
    private String ho;

    @NotBlank(message = "Tên không được để trống")
    private String ten;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$|^$", message = "Số điện thoại phải là 10 chữ số hoặc để trống")
    private String sodienthoai;

    private String diachi;

    private String matkhau;

    private Boolean trangthai;
}