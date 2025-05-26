package com.example.be.TempoTide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "SanPham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaSanPham")
    private Integer maSanPham;

    @NotBlank(message = "TenSanPham is required")
    @Size(max = 100, message = "TenSanPham must not exceed 100 characters")
    @Column(name = "TenSanPham", nullable = false)
    private String tenSanPham;

    @Size(max = 500, message = "MoTa must not exceed 500 characters")
    @Column(name = "MoTa")
    private String moTa;

    @NotNull(message = "Gia is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Gia must be greater than 0")
    @Column(name = "Gia", nullable = false)
    private BigDecimal gia;

    @Size(max = 500, message = "HinhAnh must not exceed 500 characters")
    @Column(name = "HinhAnh")
    private String hinhAnh;

    @ManyToOne
    @JoinColumn(name = "MaDanhMuc", referencedColumnName = "MaDanhMuc", nullable = false)
    private DanhMuc maDanhMuc;

    @ManyToOne
    @JoinColumn(name = "MaThuongHieu", referencedColumnName = "MaThuongHieu", nullable = false)
    private ThuongHieu maThuongHieu;

    @Column(name = "NgayTao", nullable = false, updatable = false)
    private LocalDateTime ngayTao = LocalDateTime.now();

    @Column(name = "NgayCapNhat", nullable = false)
    private LocalDateTime ngayCapNhat = LocalDateTime.now();

    @Column(name = "TrangThai", nullable = false)
    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiTao", referencedColumnName = "MaNhanVien")
    private NhanVien nguoiTao;

    @ManyToOne
    @JoinColumn(name = "NguoiCapNhat", referencedColumnName = "MaNhanVien")
    private NhanVien nguoiCapNhat;
}