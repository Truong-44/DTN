package com.example.be.TempoTide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ChiTietPhieuNhap")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietPhieuNhap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaChiTietPhieuNhap")
    private Integer maChiTietPhieuNhap;

    @ManyToOne
    @JoinColumn(name = "MaPhieuNhap", referencedColumnName = "MaPhieuNhap", nullable = false)
    private PhieuNhapHang phieuNhapHang;

    @ManyToOne
    @JoinColumn(name = "MaSanPham", referencedColumnName = "MaSanPham", nullable = false)
    private SanPham sanPham;

    @NotNull(message = "SoLuong is required")
    @Min(value = 1, message = "SoLuong must be at least 1")
    @Column(name = "SoLuong", nullable = false)
    private Integer soLuong;

    @NotNull(message = "DonGia is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "DonGia must be non-negative")
    @Column(name = "DonGia", nullable = false)
    private BigDecimal donGia;
}