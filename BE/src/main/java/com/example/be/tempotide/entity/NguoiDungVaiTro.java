package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "nguoidung_vaitro")
@Data
public class NguoiDungVaiTro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column
    private LocalDateTime ngaytao = LocalDateTime.now();

    @Column
    private Boolean trangthai = true;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;
}