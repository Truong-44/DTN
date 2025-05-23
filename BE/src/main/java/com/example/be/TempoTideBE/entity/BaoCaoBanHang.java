package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "BaoCaoBanHang")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaoCaoBanHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maBaoCao;

    @Column(nullable = false)
    private LocalDateTime thoiGianBatDau;

    @Column(nullable = false)
    private LocalDateTime thoiGianKetThuc;

    @Column(nullable = false)
    private Double tongDoanhThu;

    @Column(nullable = false)
    private Integer soDonHang;

    private LocalDateTime ngayTao = LocalDateTime.now();

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiTao")
    private NhanVien nguoiTao;
}