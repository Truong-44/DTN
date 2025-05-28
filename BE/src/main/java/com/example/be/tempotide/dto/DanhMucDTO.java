package com.example.be.tempotide.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DanhMucDTO {
    private Integer madanhmuc;

    @NotBlank(message = "Tên danh mục không được để trống")
    private String tendanhmuc;

    private String mota;

    private Boolean trangthai;

    private Integer danhmucchaId;
}