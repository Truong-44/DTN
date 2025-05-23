package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PhieuXuatKho")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuXuatKho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maPhieuXuatKho;

    @ManyToOne
    @JoinColumn(name = "MaDonHang", nullable = false)
    private DonHang donHang;

    private LocalDateTime ngayXuatKho = LocalDateTime.now();

    @Column(nullable = false)
    private BigDecimal tongGiaTri;

    private String ghiChu;

    private LocalDateTime ngayTao = LocalDateTime.now();

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiTao")
    private NhanVien nguoiTao;
}