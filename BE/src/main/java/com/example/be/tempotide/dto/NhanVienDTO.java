package com.example.be.tempotide.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class NhanVienDTO {
    private Integer manhanvien;
    @NotNull(message = "Họ không được để trống")
    @Size(max = 50, message = "Họ không được vượt quá 50 ký tự")
    private String ho;
    @NotNull(message = "Tên không được để trống")
    @Size(max = 50, message = "Tên không được vượt quá 50 ký tự")
    private String ten;
    @NotNull(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(max = 100, message = "Email không được vượt quá 100 ký tự")
    private String email;
    @Size(max = 15, message = "Số điện thoại không được vượt quá 15 ký tự")
    private String sodienthoai;
    @NotNull(message = "Ngày tuyển dụng không được để trống")
    private LocalDate ngaytuyendung;
    @NotNull(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String matkhau;
    private LocalDateTime ngaytao;
    private LocalDateTime ngaycapnhat;
    private Boolean trangthai;
    private Integer nguoitao;
    private Integer nguoicapnhat;
}