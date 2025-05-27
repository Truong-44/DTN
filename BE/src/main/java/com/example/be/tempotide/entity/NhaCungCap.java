package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "nhacungcap")
@Data
public class NhaCungCap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manhacungcap")
    private Integer manhacungcap;

    @Column(name = "tennhacungcap", nullable = false)
    private String tennhacungcap;

    @Column(name = "nguoilienhe")
    private String nguoilienhe;

    @Column(name = "sodienthoai")
    private String sodienthoai;

    @Column(name = "email")
    private String email;

    @Column(name = "diachi")
    private String diachi;

    @Column(name = "mataikhoannganhang")
    private String mataikhoannganhang;

    @Column(name = "trangweb")
    private String trangweb;

    @Column(name = "ngaytao", columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime ngaytao;

    @Column(name = "trangthai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangthai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;
}