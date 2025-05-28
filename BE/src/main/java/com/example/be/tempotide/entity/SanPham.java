package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sanpham")
@Getter
@Setter
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer masanpham;

    @Column(name = "tensanpham", nullable = false, length = 100)
    private String tensanpham;

    @Column(name = "mota", length = 500)
    private String mota;

    @Column(name = "gia", nullable = false, precision = 18, scale = 2)
    private BigDecimal gia;

    @Column(name = "trangthai", nullable = false)
    private Boolean trangthai = true;

    @Column(name = "ngaytao", nullable = false)
    private LocalDateTime ngaytao = LocalDateTime.now();

    @Column(name = "ngaycapnhat", nullable = false)
    private LocalDateTime ngaycapnhat = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;

    @ManyToOne
    @JoinColumn(name = "nguoicapnhat")
    private NhanVien nguoicapnhat;

    @ManyToOne
    @JoinColumn(name = "madanhmuc", nullable = false)
    private DanhMuc danhMuc;
}