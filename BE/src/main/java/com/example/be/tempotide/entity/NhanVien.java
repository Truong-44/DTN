package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "nhanvien")
@Getter
@Setter
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manhanvien")
    private Integer manhanvien;

    @Column(name = "ho", nullable = false, length = 50)
    private String ho;

    @Column(name = "ten", nullable = false, length = 50)
    private String ten;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "sodienthoai", length = 15)
    private String sodienthoai;

    @Column(name = "ngaytuyendung", nullable = false)
    private LocalDate ngaytuyendung;

    @Column(name = "matkhau", nullable = false, length = 255)
    private String matkhau;

    @Column(name = "ngaytao", nullable = false, updatable = false)
    private LocalDateTime ngaytao;

    @Column(name = "ngaycapnhat")
    private LocalDateTime ngaycapnhat;

    @Column(name = "trangthai", nullable = false)
    private Boolean trangthai;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;

    @ManyToOne
    @JoinColumn(name = "nguoicapnhat")
    private NhanVien nguoicapnhat;
}