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
@Table(name = "KhuyenMai")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class khuyenmai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKhuyenMai")
    private Integer maKhuyenMai;

    @NotBlank(message = "TenKhuyenMai is required")
    @Size(max = 100, message = "TenKhuyenMai must not exceed 100 characters")
    @Column(name = "TenKhuyenMai", nullable = false)
    private String tenKhuyenMai;

    @Size(max = 500, message = "MoTa must not exceed 500 characters")
    @Column(name = "MoTa")
    private String moTa;

    @NotNull(message = "PhanTramGiamGia is required")
    @DecimalMin(value = "0.0", message = "PhanTramGiamGia must be between 0 and 100")
    @DecimalMax(value = "100.0", message = "PhanTramGiamGia must be between 0 and 100")
    @Column(name = "PhanTramGiamGia", nullable = false)
    private BigDecimal phanTramGiamGia;

    @Size(max = 500, message = "DieuKienApDung must not exceed 500 characters")
    @Column(name = "DieuKienApDung")
    private String dieuKienApDung;

    @NotNull(message = "SoLuongApDung is required")
    @Min(value = 0, message = "SoLuongApDung must be non-negative")
    @Column(name = "SoLuongApDung", nullable = false)
    private Integer soLuongApDung;

    @NotNull(message = "ApDungChoDatTruoc is required")
    @Column(name = "ApDungChoDatTruoc", nullable = false)
    private Boolean apDungChoDatTruoc;

    @NotNull(message = "NgayBatDau is required")
    @Column(name = "NgayBatDau", nullable = false)
    private LocalDateTime ngayBatDau;

    @NotNull(message = "NgayKetThuc is required")
    @Column(name = "NgayKetThuc", nullable = false)
    private LocalDateTime ngayKetThuc;

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

    @PrePersist
    @PreUpdate
    private void validateDates() {
        if (ngayKetThuc.isBefore(ngayBatDau)) {
            throw new IllegalArgumentException("NgayKetThuc must be after NgayBatDau");
        }
    }
}