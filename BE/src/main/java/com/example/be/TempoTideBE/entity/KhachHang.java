package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "KhachHang")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maKhachHang;

    @Column(nullable = false)
    private String ho;

    @Column(nullable = false)
    private String ten;

    @Column(nullable = false, unique = true)
    private String email;

    private String soDienThoai;

    private LocalDate ngaySinh;

    @Column(nullable = false)
    private String matKhau;

    private Boolean laKhachVangLai = false;

    private Integer diemTichLuy = 0;

    @ManyToOne
    @JoinColumn(name = "MaCapBac")
    private CapBacKhachHang capBac;

    private LocalDateTime ngayTao = LocalDateTime.now();

    private LocalDateTime ngayCapNhat = LocalDateTime.now();

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiTao")
    private NhanVien nguoiTao;

    @ManyToOne
    @JoinColumn(name = "NguoiCapNhat")
    private NhanVien nguoiCapNhat;
}