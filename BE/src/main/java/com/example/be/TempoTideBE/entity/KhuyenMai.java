package com.example.be.TempoTideBE.entity;

import com.example.be.TempoTideBE.entity.NhanVien;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "KhuyenMai")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhuyenMai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maKhuyenMai;

    @Column(nullable = false)
    private String tenKhuyenMai;

    private String moTa;

    private BigDecimal phanTramGiamGia;

    private String dieuKienApDung;

    private Integer soLuongApDung = 0;

    private Boolean apDungChoDatTruoc = false;

    @Column(nullable = false)
    private LocalDateTime ngayBatDau;

    @Column(nullable = false)
    private LocalDateTime ngayKetThuc;

    private LocalDateTime ngayTao = LocalDateTime.now();

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiTao")
    private NhanVien nguoiTao;
}