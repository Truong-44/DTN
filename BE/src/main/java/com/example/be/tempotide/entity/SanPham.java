package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "sanpham")
@Getter
@Setter
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "masanpham")
    private Integer masanpham;

    @Column(name = "tensanpham", nullable = false, length = 100)
    private String tensanpham;

    @ManyToOne
    @JoinColumn(name = "madanhmuc", nullable = false)
    private DanhMuc madanhmuc;

    @Column(name = "mota", length = 500)
    private String mota;

    @Column(name = "gia", nullable = false)
    private Double gia;

    @Column(name = "ngaytao")
    private LocalDateTime ngaytao;

    @Column(name = "trangthai")
    private Boolean trangthai;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;

    @ManyToOne
    @JoinColumn(name = "nguoicapnhat")
    private NhanVien nguoicapnhat;
}