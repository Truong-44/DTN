package com.example.tempotide.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "chitietgiohang")
@Getter
@Setter
@IdClass(ChiTietGioHangId.class)
public class ChiTietGioHang {
    @Id
    @ManyToOne
    @JoinColumn(name = "magiohang", nullable = false)
    private GioHang giohang;

    @Id
    @ManyToOne
    @JoinColumn(name = "machitietsanpham", nullable = false)
    private ChiTietSanPham chitietsanpham;

    @Column(name = "soluong", nullable = false)
    private Integer soluong;

    @Column(name = "gia", nullable = false, precision = 18, scale = 3)
    private BigDecimal gia;
}