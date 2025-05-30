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
    private Integer madanhmuc;

    @Column(nullable = false, length = 100)
    private String tendanhmuc;

    @ManyToOne
    @JoinColumn(name = "madanhmuccha")
    private DanhMuc madanhmuccha;

    @Column(length = 200)
    private String mota;

    @Column
    private LocalDateTime ngaytao = LocalDateTime.now();

    @Column
    private Boolean trangthai = true;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;
}