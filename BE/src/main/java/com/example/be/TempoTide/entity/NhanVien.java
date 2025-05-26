package com.example.be.TempoTide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "NhanVien")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNhanVien")
    private Integer maNhanVien;

    @NotBlank(message = "Ho is required")
    @Size(max = 50, message = "Ho must not exceed 50 characters")
    @Column(name = "Ho", nullable = false)
    private String ho;

    @NotBlank(message = "Ten is required")
    @Size(max = 50, message = "Ten must not exceed 50 characters")
    @Column(name = "Ten", nullable = false)
    private String ten;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "^[0-9]{10}$|^$", message = "SoDienThoai must be a 10-digit number or null")
    @Size(max = 15, message = "SoDienThoai must not exceed 15 characters")
    @Column(name = "SoDienThoai")
    private String soDienThoai;

    @NotNull(message = "NgayTuyenDung is required")
    @Column(name = "NgayTuyenDung", nullable = false)
    private LocalDate ngayTuyenDung;

    @Size(max = 50, message = "MaSoThue must not exceed 50 characters")
    @Column(name = "MaSoThue")
    private String maSoThue;

    @Column(name = "NgayNghiViec")
    private LocalDate ngayNghiViec;

    @NotBlank(message = "MatKhau is required")
    @Size(max = 255, message = "MatKhau must not exceed 255 characters")
    @Column(name = "MatKhau", nullable = false)
    private String matKhau;

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

    public void setMatKhau(String matKhau, PasswordEncoder passwordEncoder) {
        this.matKhau = passwordEncoder.encode(matKhau);
    }
}