package com.example.be.TempoTide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "ChiTietGioHang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietGioHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaChiTietGioHang")
    private Integer maChiTietGioHang;

    @ManyToOne
    @JoinColumn(name = "MaGioHang", referencedColumnName = "MaGioHang", nullable = false)
    private GioHang gioHang;

    @ManyToOne
    @JoinColumn(name = "MaSanPham", referencedColumnName = "MaSanPham", nullable = false)
    private SanPham sanPham;

    @NotNull(message = "SoLuong is required")
    @Min(value = 1, message = "SoLuong must be at least 1")
    @Column(name = "SoLuong", nullable = false)
    private Integer soLuong;
}