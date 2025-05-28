package com.example.tempotide.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GioHangDTO {
    private Integer magiohang;

    private Integer makhachhang;

    @Size(max = 15, message = "Số điện thoại không được vượt quá 15 ký tự")
    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại phải là 10 chữ số")
    private String sodienthoai;

    private Boolean trangthai;
}