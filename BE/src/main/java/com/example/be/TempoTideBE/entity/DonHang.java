package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "DonHang")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maDonHang;

    @ManyToOne
    @JoinColumn(name = "MaKhachHang")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "MaNhanVienXuLy")
    private NhanVien nhanVienXuLy;

    private LocalDateTime ngayDatHang = LocalDateTime.now();

    @Column(nullable = false)
    private BigDecimal tongTien;

    @ManyToOne
    @JoinColumn(name = "MaPhuongThucVanChuyen", nullable = false)
    private PhuongThucVanChuyen phuongThucVanChuyen;

    @ManyToOne
    @JoinColumn(name = "MaDiaChiGiaoHang", nullable = false)
    private DiaChi diaChiGiaoHang;

    @Column(nullable = false)
    private String trangThaiDonHang;

    @ManyToOne
    @JoinColumn(name = "MaKhuyenMai")
    private KhuyenMai khuyenMai;

    private String ghiChu;

    private Boolean laDonHangVangLai = false;

    private LocalDateTime ngayTao = LocalDateTime.now();

    private LocalDateTime ngayCapNhat = LocalDateTime.now();

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiTao")
    private NhanVien nguoiTao;

    @ManyToOne
    @JoinColumn(name = "NguoiCapNhat")
    private NhanVien nguoiCapNhat;
}