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
    private Integer madanhmuc;

    @Column(name = "tendanhmuc", nullable = false, length = 50, unique = true)
    private String tendanhmuc;

    @Column(name = "mota", length = 200)
    private String mota;

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
    @JoinColumn(name = "danhmuccha")
    private DanhMuc danhmuccha;
}