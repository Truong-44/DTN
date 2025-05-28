package com.example.be.tempotide.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatboxLichSuDTO {
    private Integer machatbox;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\d{10,11}$", message = "Số điện thoại phải có 10 hoặc 11 chữ số")
    private String sodienthoai;

    @NotBlank(message = "Nội dung không được để trống")
    @Size(max = 500, message = "Nội dung không được vượt quá 500 ký tự")
    private String noidung;

    @NotBlank(message = "Loại câu hỏi không được để trống")
    private String loaiCauhoi;

    @NotNull(message = "Ngày tạo không được để trống")
    private LocalDateTime ngaytao;

    private Boolean trangthai;

    private Integer makhachhang;
    private Integer giosanpham;
    private Integer nguoitao; // Thêm trường này
}