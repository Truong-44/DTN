package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "LichSuDoiTra")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LichSuDoiTra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maLichSu;

    @ManyToOne
    @JoinColumn(name = "MaDoiTra", nullable = false)
    private DoiTra doiTra;

    @Column(nullable = false)
    private String trangThai;

    private String moTa;

    private LocalDateTime ngayCapNhat = LocalDateTime.now();

    private String ghiChu;

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiCapNhat")
    private NhanVien nguoiCapNhat;
}