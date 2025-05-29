package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "vaitro_quyen")
@Getter
@Setter
public class VaiTroQuyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mavaitro_quyen")
    private Integer mavaitroQuyen;

    @ManyToOne
    @JoinColumn(name = "mavaitro", nullable = false)
    private VaiTro mavaitro;

    @ManyToOne
    @JoinColumn(name = "maquyen", nullable = false)
    private Quyen maquyen;

    @Column(name = "ngaytao", nullable = false, updatable = false)
    private LocalDateTime ngaytao;

    @Column(name = "trangthai", nullable = false)
    private Boolean trangthai;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;
}