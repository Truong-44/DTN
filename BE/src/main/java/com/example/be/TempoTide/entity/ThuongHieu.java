package com.example.be.TempoTide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ThuongHieu")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThuongHieu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaThuongHieu")
    private Integer maThuongHieu;

    @NotBlank(message = "TenThuongHieu is required")
    @Size(max = 100, message = "TenThuongHieu must not exceed 100 characters")
    @Column(name = "TenThuongHieu", nullable = false, unique = true)
    private String tenThuongHieu;

    @Size(max = 500, message = "MoTa must not exceed 500 characters")
    @Column(name = "MoTa")
    private String moTa;

    @Size(max = 500, message = "HinhAnh must not exceed 500 characters")
    @Column(name = "HinhAnh")
    private String hinhAnh;

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
