package com.noithat.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "chitietgiohang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietGioHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "giohangid", nullable = false)
    private GioHang gioHang;

    @ManyToOne
    @JoinColumn(name = "chitietsanphamid", nullable = false)
    private ChiTietSanPham chiTietSanPham;

    private Integer soluong;

    @Column(nullable = false)
    private BigDecimal dongia;

    @CreationTimestamp
    private LocalDateTime ngaythem;
}
