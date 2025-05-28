package com.example.be.tempotide.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatboxLichSuDTO {
    private Integer machat;

    private Integer makhachhang;

    @Size(max = 15, message = "Số điện thoại không được vượt quá 15 ký tự")
    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại phải là 10 chữ số")
    private String sodienthoai;

    @NotBlank(message = "Nội dung không được để trống")
    @Size(max = 1000, message = "Nội dung không được vượt quá 1000 ký tự")
    private String noidung;

    @Size(max = 50, message = "Loại câu hỏi không được vượt quá 50 ký tự")
    @Pattern(regexp = "^(Sản phẩm|Tài khoản|Câu hỏi đơn giản)$", message = "Loại câu hỏi không hợp lệ")
    private String loaiCauhoi;

    private Integer giosanpham;

    private LocalDateTime ngaytao;
    private Boolean trangthai;
}