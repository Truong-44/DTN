package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "GioHang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class giohang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaGioHang")
    private Integer maGioHang;

    @ManyToOne
    @JoinColumn(name = "MaKhachHang", referencedColumnName = "MaKhachHang", nullable = false)
    private khachhang khachHang;

    @NotNull(message = "NgayTao is required")
    @Column(name = "NgayTao", nullable = false, updatable = false)
    private LocalDateTime ngayTao = LocalDateTime.now();

    @NotNull(message = "NgayCapNhat is required")
    @Column(name = "NgayCapNhat", nullable = false)
    private LocalDateTime ngayCapNhat = LocalDateTime.now();

    @NotNull(message = "TrangThai is required")
    @Column(name = "TrangThai", nullable = false)
    private Boolean trangThai = true;
}