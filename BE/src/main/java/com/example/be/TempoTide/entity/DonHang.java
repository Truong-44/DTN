package com.example.be.TempoTide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDonHang")
    private Integer maDonHang;

    @ManyToOne
    @JoinColumn(name = "MaKhachHang", referencedColumnName = "MaKhachHang", nullable = false)
    private KhachHang maKhachHang;

    @NotNull(message = "TongGiaTri is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "TongGiaTri must be greater than 0")
    @Column(name = "TongGiaTri", nullable = false)
    private BigDecimal tongGiaTri;

    @NotBlank(message = "TrangThaiDonHang is required")
    @Pattern(regexp = "^(ChoXacNhan|DaXacNhan|DangGiao|HoanThanh|Huy)$", message = "Invalid TrangThaiDonHang")
    @Column(name = "TrangThaiDonHang", nullable = false)
    private String trangThaiDonHang;

    @ManyToOne
    @JoinColumn(name = "MaKhuyenMai", referencedColumnName = "MaKhuyenMai")
    private KhuyenMai maKhuyenMai;

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
