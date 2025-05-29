package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "chitietsanpham")
@Getter
@Setter
public class ChiTietSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machitietsanpham")
    private Integer machitietsanpham;

    @ManyToOne
    @JoinColumn(name = "masanpham", nullable = false)
    private SanPham masanpham;

    @ManyToOne
    @JoinColumn(name = "mathuoctinh", nullable = false)
    private ThuocTinhSanPham mathuoctinh;

    @Column(name = "giatri", nullable = false, length = 100)
    private String giatri;

    @Column(name = "gia", nullable = false)
    private BigDecimal gia;

    @Column(name = "soluongton", nullable = false)
    private Integer soluongton;

    @Column(name = "sku", length = 50)
    private String sku;

    @Column(name = "duongdanhinhanh", length = 500)
    private String duongdanhinhanh;

    @Column(name = "lahinhchinh", nullable = false)
    private Boolean lahinhchinh;

    @Column(name = "ngaytao", nullable = false, updatable = false)
    private LocalDateTime ngaytao;

    @Column(name = "ngaycapnhat")
    private LocalDateTime ngaycapnhat;

    @Column(name = "trangthai", nullable = false)
    private Boolean trangthai;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;

    @ManyToOne
    @JoinColumn(name = "nguoicapnhat")
    private NhanVien nguoicapnhat;
}