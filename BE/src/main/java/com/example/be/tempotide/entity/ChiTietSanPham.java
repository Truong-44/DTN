package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "chitietsanpham")
@Data
public class ChiTietSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer machitietsanpham;

    @ManyToOne
    @JoinColumn(name = "masanpham", nullable = false)
    private SanPham masanpham;

    @ManyToOne
    @JoinColumn(name = "mathuoctinh", nullable = false)
    private ThuocTinhSanPham mathuoctinh;

    @Column(nullable = false, length = 100)
    private String giatri;

    @Column(nullable = false)
    private BigDecimal gia;

    @Column(nullable = false)
    private Integer soluongton;

    @Column(length = 50, unique = true)
    private String sku;

    @Column(length = 500)
    private String duongdanhinhanh;

    @Column
    private Boolean lahinhchinh = false;

    @Column
    private LocalDateTime ngaytao = LocalDateTime.now();

    @Column
    private LocalDateTime ngaycapnhat = LocalDateTime.now();

    @Column
    private Boolean trangthai = true;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;

    @ManyToOne
    @JoinColumn(name = "nguoicapnhat")
    private NhanVien nguoicapnhat;
}