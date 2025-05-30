package com.example.be.tempotide.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ChiTietGioHangDTO {
    private Integer machitietgiohang;
    private Integer magiohang;
    private Integer machitietsanpham;
    private Integer soluong;
    private BigDecimal dongia;
    private LocalDateTime ngaytao;
    private Boolean trangthai;
    private Integer nguoitao;
}