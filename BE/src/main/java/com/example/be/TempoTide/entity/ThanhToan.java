package com.example.be.TempoTide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ThanhToan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThanhToan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaThanhToan")
    private Integer maThanhToan;

    @OneToOne
    @JoinColumn(name = "MaDonHang", referencedColumnName = "MaDonHang", nullable = false, unique = true)
    private DonHang donHang;

    @NotNull(message = "SoTien is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "SoTien must be non-negative")
    @Column(name = "SoTien", nullable = false)
    private BigDecimal soTien;

    @NotBlank(message = "PhuongThucThanhToan is required")
    @Pattern(regexp = "^(TienMat|ChuyenKhoan|TheNganHang|ViDienTu)$", message = "PhuongThucThanhToan must be TienMat, ChuyenKhoan, TheNganHang, or ViDienTu")
    @Column(name = "PhuongThucThanhToan", nullable = false)
    private String phuongThucThanhToan;

    @NotBlank(message = "TrangThai is required")
    @Pattern(regexp = "^(DaThanhToan|ChuaThanhToan|ThatBai)$", message = "TrangThai must be DaThanhToan, ChuaThanhToan, or ThatBai")
    @Column(name = "TrangThai", nullable = false)
    private String trangThai;

    @NotNull(message = "NgayThanhToan is required")
    @Column(name = "NgayThanhToan", nullable = false)
    private LocalDateTime ngayThanhToan;

    @NotNull(message = "NgayTao is required")
    @Column(name = "NgayTao", nullable = false, updatable = false)
    private LocalDateTime ngayTao = LocalDateTime.now();

    @NotNull(message = "NgayCapNhat is required")
    @Column(name = "NgayCapNhat", nullable = false)
    private LocalDateTime ngayCapNhat = LocalDateTime.now();
}