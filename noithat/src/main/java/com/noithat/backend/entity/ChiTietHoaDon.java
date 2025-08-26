package com.noithat.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "chitiethoadon")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietHoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "hoadonid", nullable = false)
    private HoaDon hoaDon;

    private String tensanpham;
    private String tenmau;
    private String chatlieu;
    private String kichthuoc;
    private Integer soluong;

    @Column(nullable = false)
    private BigDecimal dongia;
}
