package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "donhang")
@Getter
@Setter
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "madonhang")
    private Integer madonhang;

    @ManyToOne
    @JoinColumn(name = "makhachhang")
    private KhachHang makhachhang;

    @ManyToOne
    @JoinColumn(name = "manhanvienxuly")
    private NhanVien manhanvienxuly;

    @Column(name = "ngaydathang")
    private LocalDateTime ngaydathang;

    @Column(name = "tongtien", nullable = false)
    private Double tongtien;

    @Column(name = "diachigiaohang", nullable = false, length = 200)
    private String diachigiaohang;

    @Column(name = "phuongthucvanchuyen", nullable = false, length = 50)
    private String phuongthucvanchuyen;

    @Column(name = "phuongthucthanhtoan", nullable = false, length = 50)
    private String phuongthucthanhtoan;

    @Column(name = "sotien", nullable = false)
    private Double sotien;

    @Column(name = "trangthaithanhtoan", nullable = false, length = 50)
    private String trangthaithanhtoan;

    @Column(name = "giamgia")
    private Double giamgia;

    @Column(name = "trangthaidonhang", nullable = false, length = 50)
    private String trangthaidonhang;

    @Column(name = "tenkhachhang", length = 100)
    private String tenkhachhang;

    @Column(name = "sodienthoai", length = 15)
    private String sodienthoai;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "ngaythanhtoan")
    private LocalDateTime ngaythanhtoan;

    @Column(name = "ghichu", length = 500)
    private String ghichu;

    @Column(name = "trangthai_hoadon")
    private Boolean trangthaiHoadon;

    @Column(name = "ladonhangvanglai")
    private Boolean ladonhangvanglai;

    @Column(name = "ngaytao")
    private LocalDateTime ngaytao;

    @Column(name = "ngaycapnhat")
    private LocalDateTime ngaycapnhat;

    @Column(name = "trangthai")
    private Boolean trangthai;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;

    @ManyToOne
    @JoinColumn(name = "nguoicapnhat")
    private NhanVien nguoicapnhat;
}