package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "vaitro_quyen")
@Data
public class VaiTroQuyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mavaitroQuyen;

    @ManyToOne
    @JoinColumn(name = "mavaitro", nullable = false)
    private VaiTro mavaitro;

    @ManyToOne
    @JoinColumn(name = "maquyen", nullable = false)
    private Quyen maquyen;

    @Column
    private LocalDateTime ngaytao = LocalDateTime.now();

    @Column
    private Boolean trangthai = true;

    @ManyToOne
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao;
}