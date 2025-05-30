package com.example.be.tempotide.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ChiTietDonHangDTO {
    private Integer machitietdonhang;
    private Integer madonhang;
    private Integer machitietsanpham;
    private Integer soluong;
    private BigDecimal dongia;
    private LocalDateTime ngaytao;
    private Boolean trangthai;
    private Integer nguoitao;
}