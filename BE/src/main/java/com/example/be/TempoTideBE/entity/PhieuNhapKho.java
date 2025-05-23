package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PhieuNhapKho")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuNhapKho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maPhieuNhapKho;

    @ManyToOne
    @JoinColumn(name = "MaNhaCungCap", nullable = false)
    private NhaCungCap nhaCungCap;

    private LocalDateTime ngayNhapKho = LocalDateTime.now();

    @Column(nullable = false)
    private BigDecimal tongGiaTri;

    private String ghiChu;

    private LocalDateTime ngayTao = LocalDateTime.now();

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiTao")
    private NhanVien nguoiTao;
}