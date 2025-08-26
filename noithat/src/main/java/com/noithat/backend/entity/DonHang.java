package com.noithat.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "donhang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "khachhangid", nullable = false)
    private KhachHang khachHang;

    @CreationTimestamp
    private LocalDateTime ngaydat;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String diachinhan;

    private String trangthaidonhang;

    private BigDecimal tongtien;

    private String phuongthucthanhtoan;

    private Boolean trangthaithanhtoan;

    private LocalDateTime ngaythanhtoan;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String ghichu;
}
