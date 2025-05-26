package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "ChiTietSanPham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class chitietsanpham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaChiTietSanPham")
    private Integer maChiTietSanPham;

    @NotNull(message = "MaSanPham is required")
    @Column(name = "MaSanPham", nullable = false)
    private Integer maSanPham;

    @Column(name = "SoLuongTon")
    private Integer soLuongTon;

    @Column(name = "GiaBan")
    private Double giaBan;

    @Column(name = "TrangThai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangThai;

    @ManyToOne
    @JoinColumn(name = "MaSanPham", insertable = false, updatable = false)
    private sanpham sanPham;
}