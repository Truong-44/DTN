package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "chatbox_lichsu")
@Getter
@Setter
public class ChatBoxLichSu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machat")
    private Integer machat;

    @ManyToOne
    @JoinColumn(name = "makhachhang")
    private KhachHang makhachhang;

    @Column(name = "sodienthoai", length = 15)
    private String sodienthoai;

    @Column(name = "noidung", nullable = false, length = 1000)
    private String noidung;

    @Column(name = "loai_cauhoi", length = 50)
    private String loaiCauhoi;

    @ManyToOne
    @JoinColumn(name = "giosanpham")
    private SanPham giosanpham;

    @Column(name = "ngaytao", nullable = false, updatable = false)
    private LocalDateTime ngaytao;

    @Column(name = "trangthai", nullable = false)
    private Boolean trangthai;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;
}