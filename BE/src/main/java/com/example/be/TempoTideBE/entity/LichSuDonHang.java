package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "LichSuDonHang")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LichSuDonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maLichSu;

    @ManyToOne
    @JoinColumn(name = "MaDonHang", nullable = false)
    private DonHang donHang;

    private String trangThaiCu;

    private String trangThaiMoi;

    private String lyDo;

    private LocalDateTime ngayCapNhat = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "NguoiCapNhat")
    private NhanVien nguoiCapNhat;
}