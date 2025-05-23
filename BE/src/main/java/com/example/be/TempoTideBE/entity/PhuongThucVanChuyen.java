package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PhuongThucVanChuyen")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhuongThucVanChuyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maPhuongThucVanChuyen;

    @Column(nullable = false)
    private String tenPhuongThuc;

    @Column(nullable = false)
    private BigDecimal chiPhi;

    private String thoiGianDuKien;

    private Integer thoiGianToiDa;

    private String moTa;

    private LocalDateTime ngayTao = LocalDateTime.now();

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiTao")
    private NhanVien nguoiTao;
}