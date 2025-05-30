package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "chitietgiohang")
@Data
public class ChiTietGioHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer machitietgiohang;

    @ManyToOne
    @JoinColumn(name = "magiohang", nullable = false)
    private GioHang magiohang;

    @ManyToOne
    @JoinColumn(name = "machitietsanpham", nullable = false)
    private ChiTietSanPham machitietsanpham;

    @Column(nullable = false)
    private Integer soluong;

    @Column(nullable = false)
    private BigDecimal dongia;

    @Column
    private LocalDateTime ngaytao = LocalDateTime.now();

    @Column
    private Boolean trangthai = true;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;
}