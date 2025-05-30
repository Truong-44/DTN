package com.example.be.tempotide.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SanPhamDTO {
    private Integer masanpham;
    private String tensanpham;
    private Integer madanhmuc;
    private String mota;
    private BigDecimal gia;
    private LocalDateTime ngaytao;
    private Boolean trangthai;
    private Integer nguoitao;
    private Integer nguoicapnhat;
}