package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PhienDangNhap")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhienDangNhap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maPhien;

    @ManyToOne
    @JoinColumn(name = "MaNhanVien")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "MaKhachHang")
    private KhachHang khachHang;

    @Column(nullable = false)
    private String token;

    private LocalDateTime ngayDangNhap = LocalDateTime.now();

    private LocalDateTime ngayHetHan;

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiTao")
    private NhanVien nguoiTao;
}