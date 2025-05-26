package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "DanhGiaSanPham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class danhgiasanpham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDanhGia")
    private Integer maDanhGia;

    @ManyToOne
    @JoinColumn(name = "MaSanPham", referencedColumnName = "MaSanPham", nullable = false)
    private sanpham sanPham;

    @ManyToOne
    @JoinColumn(name = "MaKhachHang", referencedColumnName = "MaKhachHang", nullable = false)
    private khachhang khachHang;

    @NotNull(message = "DiemDanhGia is required")
    @Min(value = 1, message = "DiemDanhGia must be between 1 and 5")
    @Max(value = 5, message = "DiemDanhGia must be between 1 and 5")
    @Column(name = "DiemDanhGia", nullable = false)
    private Integer diemDanhGia;

    @Size(max = 1000, message = "BinhLuan must not exceed 1000 characters")
    @Column(name = "BinhLuan")
    private String binhLuan;

    @NotNull(message = "NgayDanhGia is required")
    @Column(name = "NgayDanhGia", nullable = false)
    private LocalDateTime ngayDanhGia;
}