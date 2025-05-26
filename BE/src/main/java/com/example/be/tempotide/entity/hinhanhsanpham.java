package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "HinhAnhSanPham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class hinhanhsanpham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHinhAnh")
    private Integer maHinhAnh;

    @NotNull(message = "MaSanPham is required")
    @Column(name = "MaSanPham", nullable = false)
    private Integer maSanPham;

    @NotBlank(message = "DuongDan is required")
    @Size(max = 500, message = "DuongDan must not exceed 500 characters")
    @Column(name = "DuongDan", nullable = false)
    private String duongDan;

    @Column(name = "LaAnhChinh", columnDefinition = "BIT DEFAULT 0")
    private Boolean laAnhChinh;

    @Column(name = "TrangThai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangThai;

    @ManyToOne
    @JoinColumn(name = "MaSanPham", insertable = false, updatable = false)
    private sanpham sanPham;
}