package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "nhanvien")
@Data
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer manhanvien;

    @Column(nullable = false, length = 50)
    private String ho;

    @Column(nullable = false, length = 50)
    private String ten;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(length = 15)
    private String sodienthoai;

    @Column(nullable = false)
    private LocalDate ngaytuyendung;

    @Column(nullable = false, length = 255)
    private String matkhau;

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