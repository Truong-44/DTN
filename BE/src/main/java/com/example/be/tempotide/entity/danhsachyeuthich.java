package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "DanhSachYeuThich")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class danhsachyeuthich {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaYeuThich")
    private Integer maYeuThich;

    @NotNull(message = "MaKhachHang is required")
    @Column(name = "MaKhachHang", nullable = false)
    private Integer maKhachHang;

    @NotNull(message = "MaSanPham is required")
    @Column(name = "MaSanPham", nullable = false)
    private Integer maSanPham;

    @Column(name = "TrangThai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangThai;

    @ManyToOne
    @JoinColumn(name = "MaKhachHang", insertable = false, updatable = false)
    private khachhang khachHang;

    @ManyToOne
    @JoinColumn(name = "MaSanPham", insertable = false, updatable = false)
    private sanpham sanPham;
}