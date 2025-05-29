package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "chitietdonhang")
@Getter
@Setter
public class ChiTietDonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machitietdonhang")
    private Integer machitietdonhang;

    @ManyToOne
    @JoinColumn(name = "madonhang", nullable = false)
    private DonHang madonhang;

    @ManyToOne
    @JoinColumn(name = "machitietsanpham", nullable = false)
    private ChiTietSanPham machitietsanpham;

    @Column(name = "soluong", nullable = false)
    private Integer soluong;

    @Column(name = "dongia", nullable = false)
    private BigDecimal dongia;

    @Column(name = "ngaytao", nullable = false, updatable = false)
    private LocalDateTime ngaytao;

    @Column(name = "trangthai", nullable = false)
    private Boolean trangthai;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;
}