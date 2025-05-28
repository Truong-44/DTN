package com.example.temp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "chitietsanpham")
@Getter
public class ChiTietSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long machitietsanpham;

    @ManyToOne
    @JoinColumn(name = "masanpham", nullable = false)
    private SanPham sanpham;

    @ManyToOne
    @JoinColumn(name = "mathuoctinh", nullable = false)
    private ThuocTinhSanPham thuoctinh;

    @Column(name = "giatri", nullable = false, length = 100)
    private String giatri;

    @Column(name = "gia", nullable = false, precision = 19, scale = 4)
    private BigDecimal gia;

    @Column(name = "soluongton", nullable = false)
    private Integer soluongton;

    @Column(name = "sku", nullable = true, length = 50, unique = true)
    private String sku;

    @Column(name = "duongdanhinhanh", length = 500)
    private String duongdanhinhanh;

    @Column(name = "lahinhchinh")
    private Boolean lahinhchinh = false;

    @Column(name = "trangthai", nullable = false)
    private Boolean trangthai = true;

    @Column(name = "ngaytao", nullable = false)
    private LocalDateTime ngaytao = LocalDateTime.now();

    @Column(name = "ngaycapnhat", nullable = false)
    private LocalDateTime ngaycapnhat = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;

    @ManyToOne
    @JoinColumn(name = "nguoicapnhat")
    private NhanVien nguoicapnhat;
}