package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "danhmuc")
@Data
public class DanhMuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "madanhmuc")
    private Integer madanhmuc;

    @Column(name = "tendanhmuc", nullable = false)
    private String tendanhmuc;

    @Column(name = "mota")
    private String mota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "madanhmuccha")
    private DanhMuc madanhmuccha; // Quan hệ tự tham chiếu cho danh mục cha

    @Column(name = "thutuhienthi", columnDefinition = "INT DEFAULT 0")
    private Integer thutuhienthi;

    @Column(name = "hinhanh")
    private String hinhanh;

    @Column(name = "ngaytao", columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime ngaytao;

    @Column(name = "trangthai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangthai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;
}