package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sanpham")
@Data
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer masanpham;

    @Column(nullable = false, length = 100)
    private String tensanpham;

    @ManyToOne
    @JoinColumn(name = "madanhmuc", nullable = false)
    private DanhMuc madanhmuc;

    @Column(length = 500)
    private String mota;

    @Column(nullable = false)
    private BigDecimal gia;

    @Column
    private LocalDateTime ngaytao = LocalDateTime.now();

    @Column
    private Boolean trangthai = true;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;

    @ManyToOne
    @JoinColumn(name = "nguoicapnhat")
    private NhanVien nguoicapnhat;
}