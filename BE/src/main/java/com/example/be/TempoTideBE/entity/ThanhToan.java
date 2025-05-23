package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ThanhToan")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThanhToan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maThanhToan;

    @ManyToOne
    @JoinColumn(name = "MaDonHang", nullable = false)
    private DonHang donHang;

    @ManyToOne
    @JoinColumn(name = "MaPhuongThucThanhToan", nullable = false)
    private PhuongThucThanhToan phuongThucThanhToan;

    @Column(nullable = false)
    private BigDecimal soTien;

    private LocalDateTime ngayThanhToan = LocalDateTime.now();

    private String trangThaiThanhToan;

    private String maGiaoDich;

    private String ghiChu;

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiTao")
    private NhanVien nguoiTao;
}