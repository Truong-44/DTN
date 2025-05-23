package com.example.be.TempoTideBE.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ChiTietSanPham")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maChiTietSanPham;

    @ManyToOne
    @JoinColumn(name = "MaSanPham", nullable = false)
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "MaThuocTinh", nullable = false)
    private ThuocTinhSanPham thuocTinh;

    @Column(nullable = false)
    private String giaTri;

    @Column(nullable = false)
    private Double gia;

    @Column(nullable = false)
    private Integer soLuongTon;

    @Column(unique = true)
    private String sku;

    @ManyToOne
    @JoinColumn(name = "MaBaoHanh")
    private BaoHanh baoHanh;

    private LocalDateTime ngayHetHan;

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