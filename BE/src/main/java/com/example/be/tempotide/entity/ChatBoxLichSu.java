package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "chatbox_lichsu")
@Data
public class ChatBoxLichSu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer machat;

    @ManyToOne
    @JoinColumn(name = "makhachhang")
    private KhachHang makhachhang;

    @Column(length = 15)
    private String sodienthoai;

    @Column(nullable = false, length = 1000)
    private String noidung;

    @Column(length = 50)
    private String loaiCauhoi;

    @ManyToOne
    @JoinColumn(name = "giosanpham")
    private SanPham giosanpham;

    @Column
    private LocalDateTime ngaytao = LocalDateTime.now();

    @Column
    private Boolean trangthai = true;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;
}