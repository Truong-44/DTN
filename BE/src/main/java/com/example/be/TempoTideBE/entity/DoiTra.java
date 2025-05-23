package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "DoiTra")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoiTra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maDoiTra;

    @ManyToOne
    @JoinColumn(name = "MaDonHang", nullable = false)
    private DonHang donHang;

    @ManyToOne
    @JoinColumn(name = "MaChiTietSanPham", nullable = false)
    private ChiTietSanPham chiTietSanPham;

    @ManyToOne
    @JoinColumn(name = "MaKhachHang", nullable = false)
    private KhachHang khachHang;

    @Column(nullable = false)
    private String lyDo;

    private String trangThaiDoiTra;

    private LocalDateTime ngayYeuCau = LocalDateTime.now();

    private LocalDateTime ngayXuLy;

    private String ghiChu;

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiXuLy")
    private NhanVien nguoiXuLy;
}