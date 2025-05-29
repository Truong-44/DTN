package com.example.be.tempotide.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatBoxLichSuDTO {
    private Integer machat;
    private Integer makhachhang;
    @Size(max = 15, message = "Số điện thoại không được vượt quá 15 ký tự")
    private String sodienthoai;
    @NotNull(message = "Nội dung không được để trống")
    @Size(max = 1000, message = "Nội dung không được vượt quá 1000 ký tự")
    private String noidung;
    @Size(max = 50, message = "Loại câu hỏi không được vượt quá 50 ký tự")
    private String loaiCauhoi;
    private Integer giosanpham;
    private LocalDateTime ngaytao;
    private Boolean trangthai;
    private Integer nguoitao;
}