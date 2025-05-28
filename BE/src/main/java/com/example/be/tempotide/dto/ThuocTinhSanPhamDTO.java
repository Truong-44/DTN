package com.example.tempotide.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThuocTinhSanPhamDTO {
    private Integer mathuoctinh;

    @NotBlank(message = "Tên thuộc tính không được để trống")
    @Size(max = 50, message = "Tên thuộc tính không được vượt quá 50 ký tự")
    private String tenthuoctinh;

    @Size(max = 200, message = "Mô tả không được vượt quá 200 ký tự")
    private String mota;

    private Boolean trangthai;
}