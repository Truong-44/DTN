package com.example.be.tempotide.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ThuocTinhSanPhamDTO {
    private Integer mathuoctinh;
    private String tenthuoctinh;
    private String mota;
    private LocalDateTime ngaytao;
    private Boolean trangthai;
    private Integer nguoitao;
}