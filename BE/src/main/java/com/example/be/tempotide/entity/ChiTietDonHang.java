package com.example.tempotide.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "chitietdonhang")
@Getter
@Setter
@IdClass(ChiTietDonHangId.class)
public class ChiTietDonHang {
    @Id
    @ManyToOne
    @JoinColumn(name = "madonhang", nullable = false)
    private DonHang donhang;

    @Id
    @ManyToOne
    @JoinColumn(name = "machitietsanpham", nullable = false)
    private ChiTietSanPham chitietsanpham;

    @Column(name = "soluong", nullable = false)
    private Integer soluong;

    @Column(name = "gia", nullable = false, precision = 18, scale = 3)
    private BigDecimal gia;
}