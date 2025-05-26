package com.example.be.TempoTide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PhuongThucVanChuyen")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhuongThucVanChuyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaPhuongThuc")
    private Integer maPhuongThuc;

    @NotBlank(message = "TenPhuongThuc is required")
    @Size(max = 100, message = "TenPhuongThuc must not exceed 100 characters")
    @Column(name = "TenPhuongThuc", nullable = false)
    private String tenPhuongThuc;

    @Size(max = 500, message = "MoTa must not exceed 500 characters")
    @Column(name = "MoTa")
    private String moTa;

    @NotNull(message = "ChiPhi is required")
    @Column(name = "ChiPhi", nullable = false)
    private Double chiPhi;

    @NotNull(message = "ThoiGianDuKien is required")
    @Column(name = "ThoiGianDuKien", nullable = false)
    private Integer thoiGianDuKien;

    @Column(name = "NgayTao", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime ngayTao;

    @Column(name = "TrangThai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangThai;

    @ManyToOne
    @JoinColumn(name = "NguoiTao", referencedColumnName = "MaNhanVien")
    private NhanVien nguoiTao;

    @ManyToOne
    @JoinColumn(name = "NguoiCapNhat", referencedColumnName = "MaNhanVien")
    private NhanVien nguoiCapNhat;
}