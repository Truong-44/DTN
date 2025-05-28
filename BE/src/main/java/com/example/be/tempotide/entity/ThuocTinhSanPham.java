package com.example.tempotide.entity;

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
    private Integer mathuoctinh;

    @Column(name = "tenthuoctinh", nullable = false, length = 50)
    private String tenthuoctinh;

    @Column(name = "mota", length = 200)
    private String mota;

    @Column(name = "trangthai", nullable = false)
    private Boolean trangthai = true;

    @Column(name = "ngaytao", nullable = false)
    private LocalDateTime ngaytao = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;
}