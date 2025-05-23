package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ChiTietDonHang")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietDonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maChiTietDonHang;

    @ManyToOne
    @JoinColumn(name = "MaDonHang", nullable = false)
    private DonHang donHang;

    @ManyToOne
    @JoinColumn(name = "MaChiTietSanPham", nullable = false)
    private ChiTietSanPham chiTietSanPham;

    @Column(nullable = false)
    private Integer soLuong;

    @Column(nullable = false)
    private BigDecimal donGia;

    private BigDecimal giamGia = BigDecimal.ZERO;

    private Boolean laDatTruoc = false;

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