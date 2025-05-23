package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "LienHeDatHang")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LienHeDatHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maLienHe;

    @Column(nullable = false)
    private String hoTen;

    @Column(nullable = false)
    private String soDienThoai;

    private String email;

    @ManyToOne
    @JoinColumn(name = "MaSanPham", nullable = false)
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "MaChiTietSanPham", nullable = false)
    private ChiTietSanPham chiTietSanPham;

    @Column(nullable = false)
    private Integer soLuong;

    private String ghiChu;

    @ManyToOne
    @JoinColumn(name = "MaDonHang")
    private DonHang donHang;

    private LocalDateTime ngayLienHe = LocalDateTime.now();

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiTao")
    private NhanVien nguoiTao;
}