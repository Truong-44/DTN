package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "khachhang")
@Data
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer makhachhang;

    @Column(nullable = false, length = 50)
    private String ho;

    @Column(nullable = false, length = 50)
    private String ten;

    @Column(length = 100)
    private String email;

    @Column(length = 15)
    private String sodienthoai;

    @Column(nullable = false, length = 255)
    private String matkhau;

    @Column
    private Integer diemtichluy = 0;

    @Column
    private Boolean nhanthongbao = false;

    @Column
    private LocalDateTime ngaytao = LocalDateTime.now();

    @Column
    private Boolean trangthai = true;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;

    @ManyToOne
    @JoinColumn(name = "nguoicapnhat")
    private NhanVien nguoicapnhat;
}