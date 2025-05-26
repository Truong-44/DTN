package com.example.be.TempoTide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "NhaCungCap")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhaCungCap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNhaCungCap")
    private Integer maNhaCungCap;

    @NotBlank(message = "TenNhaCungCap is required")
    @Size(max = 100, message = "TenNhaCungCap must not exceed 100 characters")
    @Column(name = "TenNhaCungCap", nullable = false, unique = true)
    private String tenNhaCungCap;

    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Column(name = "Email")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$|^$", message = "SoDienThoai must be a 10-digit number or null")
    @Size(max = 15, message = "SoDienThoai must not exceed 15 characters")
    @Column(name = "SoDienThoai")
    private String soDienThoai;

    @Size(max = 500, message = "DiaChi must not exceed 500 characters")
    @Column(name = "DiaChi")
    private String diaChi;

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
