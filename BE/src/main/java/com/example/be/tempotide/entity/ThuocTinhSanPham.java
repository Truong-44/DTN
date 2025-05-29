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

    @Column(name = "tenthuoctinh", nullable = false, length = 50)
    private String tenthuoctinh;

    @Column(name = "mota", length = 200)
    private String mota;

    @Column(name = "ngaytao", nullable = false, updatable = false)
    private LocalDateTime ngaytao;

    @Column(name = "trangthai", nullable = false)
    private Boolean trangthai;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;
}