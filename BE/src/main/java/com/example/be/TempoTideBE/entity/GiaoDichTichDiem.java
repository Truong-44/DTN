package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "GiaoDichTichDiem")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiaoDichTichDiem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maGiaoDich;

    @ManyToOne
    @JoinColumn(name = "MaKhachHang", nullable = false)
    private KhachHang khachHang;

    @Column(nullable = false)
    private Integer soDiem;

    @Column(nullable = false)
    private String loaiGiaoDich;

    @ManyToOne
    @JoinColumn(name = "MaDonHang")
    private DonHang donHang;

    private LocalDateTime ngayTao = LocalDateTime.now();

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiTao")
    private NhanVien nguoiTao;
}