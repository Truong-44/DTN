package com.example.be.TempoTide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "GiaoDichTichDiem")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiaoDichTichDiem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaGiaoDich")
    private Integer maGiaoDich;

    @ManyToOne
    @JoinColumn(name = "MaKhachHang", referencedColumnName = "MaKhachHang", nullable = false)
    private KhachHang maKhachHang;

    @NotNull(message = "SoDiem is required")
    @Column(name = "SoDiem", nullable = false)
    private Integer soDiem;

    @NotBlank(message = "LoaiGiaoDich is required")
    @Pattern(regexp = "^(Tich|Tieu)$", message = "LoaiGiaoDich must be either 'Tich' or 'Tieu'")
    @Column(name = "LoaiGiaoDich", nullable = false)
    private String loaiGiaoDich;

    @Size(max = 500, message = "MoTa must not exceed 500 characters")
    @Column(name = "MoTa")
    private String moTa;

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
