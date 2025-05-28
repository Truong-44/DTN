package com.example.tempotide.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "donhang")
@Getter
@Setter
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer madonhang;

    @ManyToOne
    @JoinColumn(name = "makhachhang")
    private KhachHang khachhang;

    @ManyToOne
    @JoinColumn(name = "manhanvien")
    private NhanVien nhanvien;

    @Column(name = "tennguoinhan", length = 100)
    private String tennguoinhan;

    @Column(name = "sodienthoai", nullable = false, length = 15)
    private String sodienthoai;

    @Column(name = "diachi", nullable = false, length = 500)
    private String diachi;

    @Column(name = "ngaydat")
    private LocalDateTime ngaydat = LocalDateTime.now();

    @Column(name = "ngaygiaohang")
    private LocalDateTime ngaygiaohang;

    @Column(name = "phivanchuyen", precision = 18, scale = 3)
    private BigDecimal phivanchuyen;

    @Column(name = "tongtien", precision = 18, scale = 3)
    private BigDecimal tongtien;

    @Column(name = "ghichu", length = 500)
    private String ghichu;

    @Column(name = "trangthai", nullable = false, length = 50)
    private String trangthai;

    @Column(name = "phuongthucthanhtoan", nullable = false, length = 50)
    private String phuongthucthanhtoan;

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