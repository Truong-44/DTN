package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "danhmuc")
@Getter
@Setter
public class DanhMuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "madanhmuc")
    private Integer madanhmuc;

    @Column(name = "tendanhmuc", nullable = false, length = 100)
    private String tendanhmuc;

    @ManyToOne
    @JoinColumn(name = "madanhmuccha")
    private DanhMuc madanhmuccha;

    @Column(name = "mota", length = 200)
    private String mota;

    @Column(name = "ngaytao")
    private LocalDateTime ngaytao;

    @Column(name = "trangthai")
    private Boolean trangthai;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;
}