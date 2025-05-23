package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "BaoHanh")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaoHanh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maBaoHanh;

    @ManyToOne
    @JoinColumn(name = "MaChiTietSanPham", nullable = false)
    private ChiTietSanPham chiTietSanPham;

    @ManyToOne
    @JoinColumn(name = "MaDonHang", nullable = false)
    private DonHang donHang;

    @Column(nullable = false)
    private LocalDateTime ngayBatDau;

    @Column(nullable = false)
    private LocalDateTime ngayKetThuc;

    private String moTa;

    private String dieuKienBaoHanh;

    private LocalDateTime ngayTao = LocalDateTime.now();

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiTao")
    private NhanVien nguoiTao;
}