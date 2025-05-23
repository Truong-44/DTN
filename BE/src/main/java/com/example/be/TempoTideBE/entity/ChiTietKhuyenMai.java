package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ChiTietKhuyenMai")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietKhuyenMai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maChiTietKhuyenMai;

    @ManyToOne
    @JoinColumn(name = "MaKhuyenMai", nullable = false)
    private KhuyenMai khuyenMai;

    @ManyToOne
    @JoinColumn(name = "MaSanPham", nullable = false)
    private SanPham sanPham;

    private Boolean trangThai = true;
}