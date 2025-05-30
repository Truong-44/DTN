package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "donhang")
@Data
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer madonhang;

    @ManyToOne
    @JoinColumn(name = "makhachhang")
    private KhachHang makhachhang;

    @ManyToOne
    @JoinColumn(name = "manhanvienxuly")
    private NhanVien manhanvienxuly;

    @Column
    private LocalDateTime ngaydathang = LocalDateTime.now();

    @Column(nullable = false)
    private BigDecimal tongtien;

    @Column(nullable = false, length = 200)
    private String diachigiaohang;

    @Column(nullable = false, length = 50)
    private String phuongthucvanchuyen = "Giao hàng nhanh";

    @Column(nullable = false, length = 50)
    private String phuongthucthanhtoan = "Tiền mặt";

    @Column(nullable = false)
    private BigDecimal sotien;

    @Column(nullable = false, length = 50)
    private String trangthaithanhtoan = "Chờ xử lý";

    @Column
    private BigDecimal giamgia = BigDecimal.ZERO;

    @Column(nullable = false, length = 50)
    private String trangthaidonhang;

    @Column(length = 100)
    private String tenkhachhang;

    @Column(length = 15)
    private String sodienthoai;

    @Column(length = 100)
    private String email;

    @Column
    private LocalDateTime ngaythanhtoan;

    @Column(length = 500)
    private String ghichu;

    @Column
    private Boolean trangthaiHoadon = true;

    @Column
    private Boolean ladonhangvanglai = false;

    @Column
    private LocalDateTime ngaytao = LocalDateTime.now();

    @Column
    private LocalDateTime ngaycapnhat = LocalDateTime.now();

    @Column
    private Boolean trangthai = true;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;

    @ManyToOne
    @JoinColumn(name = "nguoicapnhat")
    private NhanVien nguoicapnhat;
}