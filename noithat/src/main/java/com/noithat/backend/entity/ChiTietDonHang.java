package com.noithat.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "chitietdonhang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietDonHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "donhangid", nullable = false)
    private DonHang donHang;

    @ManyToOne
    @JoinColumn(name = "chitietsanphamid", nullable = false)
    private ChiTietSanPham chiTietSanPham;

    private Integer soluong;

    private BigDecimal dongia;
}
