package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "KhoHang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class khohang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKho")
    private Integer maKho;

    @NotBlank(message = "TenKho is required")
    @Size(max = 100, message = "TenKho must not exceed 100 characters")
    @Column(name = "TenKho", nullable = false)
    private String tenKho;

    @NotBlank(message = "DiaChi is required")
    @Size(max = 200, message = "DiaChi must not exceed 200 characters")
    @Column(name = "DiaChi", nullable = false)
    private String diaChi;

    @Size(max = 15, message = "SoDienThoai must not exceed 15 characters")
    @Column(name = "SoDienThoai")
    private String soDienThoai;

    @Column(name = "DungLuongToiDa", nullable = false)
    private Integer dungLuongToiDa;

    @Column(name = "DungLuongHienTai", nullable = false)
    private Integer dungLuongHienTai;

    @Column(name = "NgayTao", nullable = false, updatable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime ngayTao;

    @Column(name = "TrangThai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangThai;

    @ManyToOne
    @JoinColumn(name = "NguoiTao", referencedColumnName = "MaNhanVien")
    private nhanvien nguoiTao;

    @ManyToOne
    @JoinColumn(name = "NguoiCapNhat", referencedColumnName = "MaNhanVien")
    private nhanvien nguoiCapNhat;
}