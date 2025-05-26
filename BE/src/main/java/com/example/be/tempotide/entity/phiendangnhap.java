package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PhienDangNhap")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class phiendangnhap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaPhien")
    private Integer maPhien;

    @NotNull(message = "MaNhanVien is required")
    @Column(name = "MaNhanVien", nullable = false)
    private Integer maNhanVien;

    @NotBlank(message = "Token is required")
    @Size(max = 500, message = "Token must not exceed 500 characters")
    @Column(name = "Token", nullable = false)
    private String token;

    @Column(name = "NgayDangNhap", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime ngayDangNhap;

    @Column(name = "NgayHetHan", nullable = false)
    private LocalDateTime ngayHetHan;

    @Size(max = 50, message = "DiaChiIP must not exceed 50 characters")
    @Column(name = "DiaChiIP")
    private String diaChiIP;

    @Column(name = "TrangThai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangThai;

    @ManyToOne
    @JoinColumn(name = "MaNhanVien", insertable = false, updatable = false)
    private nhanvien nhanVien;
}