package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "thuonghieu")
@Data
public class ThuongHieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mathuonghieu")
    private Integer mathuonghieu;

    @Column(name = "tenthuonghieu", nullable = false, unique = true)
    private String tenthuonghieu;

    @Column(name = "mota")
    private String mota;

    @Column(name = "hinhanh")
    private String hinhanh;

    @Column(name = "ngaytao", columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime ngaytao;

    @Column(name = "trangthai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangthai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoicapnhat")
    private NhanVien nguoicapnhat;
}