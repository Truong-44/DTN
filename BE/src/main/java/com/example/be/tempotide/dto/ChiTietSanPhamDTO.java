package com.example.be.tempotide.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ChiTietSanPhamDTO {
    private Integer machitietsanpham;
    private Integer masanpham;
    private Integer mathuoctinh;
    private String giatri;
    private BigDecimal gia;
    private Integer soluongton;
    private String sku;
    private String duongdanhinhanh;
    private Boolean lahinhchinh;
    private LocalDateTime ngaytao;
    private LocalDateTime ngaycapnhat;
    private Boolean trangthai;
    private Integer nguoitao;
    private Integer nguoicapnhat;
}