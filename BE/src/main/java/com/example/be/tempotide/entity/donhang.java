package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "DonHang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class donhang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDonHang")
    private Integer maDonHang;

    @ManyToOne
    @JoinColumn(name = "MaKhachHang", referencedColumnName = "MaKhachHang", nullable = false)
    private khachhang maKhachhang;

    @NotNull(message = "NgayDatHang is required")
    @Column(name = "NgayDatHang", nullable = false)
    private LocalDateTime ngayDatHang;

    @NotNull(message = "TongTien is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "TongTien must be greater than 0")
    @Column(name = "TongTien", nullable = false)
    private BigDecimal tongTien;

    @NotNull(message = "MaPhuongThucVanChuyen is required")
    @Column(name = "MaPhuongThucVanChuyen", nullable = false)
    private Integer maPhuongThucVanChuyen;

    @NotNull(message = "MaDiaChiGiaoHang is required")
    @Column(name = "MaDiaChiGiaoHang", nullable = false)
    private Integer maDiaChiGiaoHang;

    @NotBlank(message = "TrangThaiDonHang is required")
    @Pattern(regexp = "^(ChoXacNhan|DaXacNhan|DangGiao|HoanThanh|Huy)$", message = "Invalid TrangThaiDonHang")
    @Column(name = "TrangThaiDonHang", nullable = false)
    private String trangThaiDonHang;

    @ManyToOne
    @JoinColumn(name = "MaKhuyenMai", referencedColumnName = "MaKhuyenMai")
    private khuyenmai maKhuyenmai;

    @Column(name = "NgayTao", nullable = false, updatable = false)
    private LocalDateTime ngayTao = LocalDateTime.now();

    @Column(name = "NgayCapNhat", nullable = false)
    private LocalDateTime ngayCapNhat = LocalDateTime.now();

    @Column(name = "TrangThai", nullable = false)
    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiTao", referencedColumnName = "MaNhanVien")
    private nhanvien nguoiTao;

    @ManyToOne
    @JoinColumn(name = "NguoiCapNhat", referencedColumnName = "MaNhanVien")
    private nhanvien nguoiCapNhat;
}