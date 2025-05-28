package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "khachhang")
@Getter
@Setter
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "makhachhang")
    private Integer makhachhang;

    @Column(name = "ho", nullable = false, length = 50)
    private String ho;

    @Column(name = "ten", nullable = false, length = 50)
    private String ten;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "sodienthoai", length = 15)
    private String sodienthoai;

    @Column(name = "matkhau", nullable = false, length = 255)
    private String matkhau;

    @Column(name = "diemtichluy")
    private Integer diemtichluy;

    @Column(name = "nhanthongbao")
    private Boolean nhanthongbao;

    @Column(name = "ngaytao")
    private LocalDateTime ngaytao;

    @Column(name = "trangthai")
    private Boolean trangthai;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;

    @ManyToOne
    @JoinColumn(name = "nguoicapnhat")
    private NhanVien nguoicapnhat;
}