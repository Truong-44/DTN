package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ResetPasswordToken")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class resetpasswordtoken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaToken")
    private Integer maToken;

    @NotNull(message = "MaNhanVien is required")
    @Column(name = "MaNhanVien", nullable = false)
    private Integer maNhanVien;

    @NotNull(message = "MaKhachHang is required")
    @Column(name = "MaKhachHang", nullable = false)
    private Integer maKhachHang;

    @NotBlank(message = "Token is required")
    @Size(max = 500, message = "Token must not exceed 500 characters")
    @Column(name = "Token", nullable = false)
    private String token;

    @Column(name = "NgayTao", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime ngayTao;

    @Column(name = "NgayHetHan", nullable = false)
    private LocalDateTime ngayHetHan;

    @NotNull(message = "TrangThai is required")
    @Column(name = "TrangThai", nullable = false)
    private Boolean trangThai = true;

    @Column(name = "DaSuDung", columnDefinition = "BIT DEFAULT 0")
    private Boolean daSuDung;

    @ManyToOne
    @JoinColumn(name = "MaNhanVien", insertable = false, updatable = false)
    private nhanvien nhanVien;

    @ManyToOne
    @JoinColumn(name = "NguoiTao", referencedColumnName = "MaNhanVien")
    private nhanvien nguoiTao;
}