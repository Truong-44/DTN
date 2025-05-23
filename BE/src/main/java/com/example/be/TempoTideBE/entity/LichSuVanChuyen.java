package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "LichSuVanChuyen")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LichSuVanChuyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maLichSu;

    @ManyToOne
    @JoinColumn(name = "MaDonHang", nullable = false)
    private DonHang donHang;

    @ManyToOne
    @JoinColumn(name = "MaPhuongThucVanChuyen", nullable = false)
    private PhuongThucVanChuyen phuongThucVanChuyen;

    @Column(nullable = false)
    private String trangThaiVanChuyen;

    private String diaDiem;

    private LocalDateTime ngayCapNhat = LocalDateTime.now();

    private String ghiChu;

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiCapNhat")
    private NhanVien nguoiCapNhat;
}