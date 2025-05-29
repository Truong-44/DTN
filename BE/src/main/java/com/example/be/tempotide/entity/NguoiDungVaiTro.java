package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "nguoidung_vaitro")
@Getter
@Setter
public class NguoiDungVaiTro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manguoidung_vaitro")
    private Integer manguoidungVaitro;

    @ManyToOne
    @JoinColumn(name = "manhanvien")
    private NhanVien manhanvien;

    @ManyToOne
    @JoinColumn(name = "makhachhang")
    private KhachHang makhachhang;

    @ManyToOne
    @JoinColumn(name = "mavaitro", nullable = false)
    private VaiTro mavaitro;

    @Column(name = "ngaytao", nullable = false, updatable = false)
    private LocalDateTime ngaytao;

    @Column(name = "trangthai", nullable = false)
    private Boolean trangthai;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;
}