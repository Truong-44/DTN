package com.noithat.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chitietsanpham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietSanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sanphamid", nullable = false)
    private SanPham sanpham;

    private String tenmau;
    private String mamau;
    private String chatlieu;
    private String kichthuoc;
    private Double trongluong;
    private Integer soluong;
    private String hinhchinh;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String hinhphu;
}
