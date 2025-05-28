package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "thuoctinhsanpham")
@Getter
@Setter
public class ThuocTinhSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mathuoctinh")
    private Integer mathuoctinh;

    @ManyToOne
    @JoinColumn(name = "masanpham", nullable = false)
    private SanPham masanpham;

    @Column(name = "tenthuoctinh", nullable = false, length = 50)
    private String tenthuoctinh;

    @Column(name = "giatri", nullable = false, length = 100)
    private String giatri;

    @Column(name = "ngaytao")
    private LocalDateTime ngaytao;

    @Column(name = "trangthai")
    private Boolean trangthai;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;
}