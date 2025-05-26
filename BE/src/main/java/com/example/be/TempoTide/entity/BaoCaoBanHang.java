package com.example.be.TempoTide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "BaoCaoBanHang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaoCaoBanHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaBaoCao")
    private Integer maBaoCao;

    @NotNull(message = "ThoiGianBatDau is required")
    @Column(name = "ThoiGianBatDau", nullable = false)
    private LocalDateTime thoiGianBatDau;

    @NotNull(message = "ThoiGianKetThuc is required")
    @Column(name = "ThoiGianKetThuc", nullable = false)
    private LocalDateTime thoiGianKetThuc;

    @Column(name = "TongDoanhThu")
    private Double tongDoanhThu;

    @Column(name = "SoDonHang")
    private Integer soDonHang;

    @Column(name = "TrangThai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangThai;
}