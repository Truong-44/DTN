package com.example.be.TempoTide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "DiaChi")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaChi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDiaChi")
    private Integer maDiaChi;

    @NotNull(message = "MaKhachHang is required")
    @Column(name = "MaKhachHang", nullable = false)
    private Integer maKhachHang;

    @NotBlank(message = "DiaChiCuThe is required")
    @Size(max = 500, message = "DiaChiCuThe must not exceed 500 characters")
    @Column(name = "DiaChiCuThe", nullable = false)
    private String diaChiCuThe;

    @NotBlank(message = "PhuongXa is required")
    @Size(max = 100, message = "PhuongXa must not exceed 100 characters")
    @Column(name = "PhuongXa", nullable = false)
    private String phuongXa;

    @NotBlank(message = "QuanHuyen is required")
    @Size(max = 100, message = "QuanHuyen must not exceed 100 characters")
    @Column(name = "QuanHuyen", nullable = false)
    private String quanHuyen;

    @NotBlank(message = "TinhThanh is required")
    @Size(max = 100, message = "TinhThanh must not exceed 100 characters")
    @Column(name = "TinhThanh", nullable = false)
    private String tinhThanh;

    @Column(name = "LaDiaChiMacDinh", columnDefinition = "BIT DEFAULT 0")
    private Boolean laDiaChiMacDinh;

    @Column(name = "TrangThai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangThai;

    @ManyToOne
    @JoinColumn(name = "MaKhachHang", insertable = false, updatable = false)
    private KhachHang khachHang;
}