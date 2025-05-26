package com.example.be.TempoTide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LienHeDatHang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LienHeDatHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaLienHe")
    private Integer maLienHe;

    @NotBlank(message = "HoTen is required")
    @Size(max = 100, message = "HoTen must not exceed 100 characters")
    @Column(name = "HoTen", nullable = false)
    private String hoTen;

    @NotBlank(message = "SoDienThoai is required")
    @Size(max = 15, message = "SoDienThoai must not exceed 15 characters")
    @Column(name = "SoDienThoai", nullable = false)
    private String soDienThoai;

    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Column(name = "Email")
    private String email;

    @NotNull(message = "MaSanPham is required")
    @Column(name = "MaSanPham", nullable = false)
    private Integer maSanPham;

    @NotNull(message = "MaChiTietSanPham is required")
    @Column(name = "MaChiTietSanPham", nullable = false)
    private Integer maChiTietSanPham;

    @NotNull(message = "SoLuong is required")
    @Min(value = 1, message = "SoLuong must be greater than 0")
    @Column(name = "SoLuong", nullable = false)
    private Integer soLuong;

    @Size(max = 500, message = "GhiChu must not exceed 500 characters")
    @Column(name = "GhiChu")
    private String ghiChu;

    @Column(name = "MaDonHang")
    private Integer maDonHang;

    @Column(name = "NgayLienHe", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime ngayLienHe;

    @Column(name = "TrangThai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangThai;

    @ManyToOne
    @JoinColumn(name = "NguoiTao", referencedColumnName = "MaNhanVien")
    private NhanVien nguoiTao;

    @ManyToOne
    @JoinColumn(name = "MaSanPham", insertable = false, updatable = false)
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "MaChiTietSanPham", insertable = false, updatable = false)
    private ChiTietSanPham chiTietSanPham;

    @ManyToOne
    @JoinColumn(name = "MaDonHang", insertable = false, updatable = false)
    private DonHang donHang;
}