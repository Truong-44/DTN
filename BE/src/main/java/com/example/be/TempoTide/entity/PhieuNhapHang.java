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
@Table(name = "PhieuNhapHang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhieuNhapHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaPhieuNhap")
    private Integer maPhieuNhap;

    @ManyToOne
    @JoinColumn(name = "MaNhaCungCap", referencedColumnName = "MaNhaCungCap", nullable = false)
    private NhaCungCap nhaCungCap;

    @NotNull(message = "NgayNhap is required")
    @Column(name = "NgayNhap", nullable = false)
    private LocalDateTime ngayNhap;

    @NotNull(message = "TongTien is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "TongTien must be non-negative")
    @Column(name = "TongTien", nullable = false)
    private BigDecimal tongTien;

    @NotBlank(message = "TrangThai is required")
    @Pattern(regexp = "^(Moi|DaXacNhan|HoanThanh|Huy)$", message = "TrangThai must be Moi, DaXacNhan, HoanThanh, or Huy")
    @Column(name = "TrangThai", nullable = false)
    private String trangThai;

    @Column(name = "NgayTao", nullable = false, updatable = false)
    private LocalDateTime ngayTao = LocalDateTime.now();

    @Column(name = "NgayCapNhat", nullable = false)
    private LocalDateTime ngayCapNhat = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "NguoiTao", referencedColumnName = "MaNhanVien")
    private NhanVien nguoiTao;

    @ManyToOne
    @JoinColumn(name = "NguoiCapNhat", referencedColumnName = "MaNhanVien")
    private NhanVien nguoiCapNhat;
}
