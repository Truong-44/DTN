package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "LichSuBaoHanh")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LichSuBaoHanh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maLichSu;

    @ManyToOne
    @JoinColumn(name = "MaYeuCauBaoHanh", nullable = false)
    private YeuCauBaoHanh yeuCauBaoHanh;

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