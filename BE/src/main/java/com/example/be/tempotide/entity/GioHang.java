package com.example.tempotide.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "giohang")
@Getter
@Setter
public class GioHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer magiohang;

    @ManyToOne
    @JoinColumn(name = "makhachhang")
    private KhachHang khachhang;

    @Column(name = "sodienthoai", length = 15)
    private String sodienthoai;

    @Column(name = "ngaytao", nullable = false)
    private LocalDateTime ngaytao = LocalDateTime.now();

    @Column(name = "ngaycapnhat", nullable = false)
    private LocalDateTime ngaycapnhat = LocalDateTime.now();

    @Column(name = "trangthai", nullable = false)
    private Boolean trangthai = true;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;
}