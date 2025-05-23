package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "YeuCauBaoHanh")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YeuCauBaoHanh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maYeuCau;

    @ManyToOne
    @JoinColumn(name = "MaBaoHanh", nullable = false)
    private BaoHanh baoHanh;

    @ManyToOne
    @JoinColumn(name = "MaKhachHang", nullable = false)
    private KhachHang khachHang;

    @Column(nullable = false)
    private String moTaLoi;

    private String trangThaiYeuCau;

    private LocalDateTime ngayYeuCau = LocalDateTime.now();

    private LocalDateTime ngayXuLy;

    private String ghiChu;

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiXuLy")
    private NhanVien nguoiXuLy;
}