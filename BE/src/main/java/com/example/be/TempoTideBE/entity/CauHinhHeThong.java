package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "CauHinhHeThong")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CauHinhHeThong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maCauHinh;

    @Column(nullable = false, unique = true)
    private String tenCauHinh;

    @Column(nullable = false)
    private String giaTri;

    private String moTa;

    private LocalDateTime ngayTao = LocalDateTime.now();

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiTao")
    private NhanVien nguoiTao;
}