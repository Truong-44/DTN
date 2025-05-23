package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "KhoHang")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhoHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maKhoHang;

    @ManyToOne
    @JoinColumn(name = "MaChiTietSanPham", nullable = false)
    private ChiTietSanPham chiTietSanPham;

    @Column(nullable = false)
    private Integer soLuong;

    private String viTriKho;

    private LocalDateTime ngayNhapKho;

    private LocalDateTime ngayXuatKho;

    private LocalDateTime ngayCapNhat = LocalDateTime.now();

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiCapNhat")
    private NhanVien nguoiCapNhat;
}