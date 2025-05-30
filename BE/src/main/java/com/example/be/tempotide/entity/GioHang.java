package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "giohang")
@Data
public class GioHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer magiohang;

    @ManyToOne
    @JoinColumn(name = "makhachhang")
    private KhachHang makhachhang;

    @Column(length = 15)
    private String sodienthoai;

    @Column
    private LocalDateTime ngaytao = LocalDateTime.now();

    @Column
    private LocalDateTime ngaycapnhat = LocalDateTime.now();

    @Column
    private Boolean trangthai = true;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;
}