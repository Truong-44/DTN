package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Table(name = "NhaCungCap")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class nhacungcap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNhaCungCap")
    private Integer maNhaCungCap;

    @NotBlank(message = "TenNhaCungCap is required")
    @Size(max = 100, message = "TenNhaCungCap must not exceed 100 characters")
    @Column(name = "TenNhaCungCap", nullable = false, unique = true)
    private String tenNhaCungCap;

    @NotBlank(message = "NguoiLienHe is required")
    @Size(max = 100, message = "NguoiLienHe must not exceed 100 characters")
    @Column(name = "NguoiLienHe", nullable = false)
    private String nguoiLienHe;

    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Column(name = "Email")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$|^$", message = "SoDienThoai must be a 10-digit number or null")
    @Size(max = 15, message = "SoDienThoai must not exceed 15 characters")
    @Column(name = "SoDienThoai")
    private String soDienThoai;

    @Size(max = 200, message = "DiaChi must not exceed 200 characters")
    @Column(name = "DiaChi")
    private String diaChi;

    @Size(max = 50, message = "MaTaiKhoanNganHang must not exceed 50 characters")
    @Column(name = "MaTaiKhoanNganHang")
    private String maTaiKhoanNganHang;

    @Size(max = 200, message = "TrangWeb must not exceed 200 characters")
    @Column(name = "TrangWeb")
    private String trangWeb;

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
    private nhanvien nguoiTao;

    @ManyToOne
    @JoinColumn(name = "NguoiCapNhat", referencedColumnName = "MaNhanVien")
    private nhanvien nguoiCapNhat;

    public void setMatKhau(String matKhau, PasswordEncoder passwordEncoder) {
        this.matKhau = passwordEncoder.encode(matKhau);
    }
}