package com.example.be.TempoTide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "BaoHanh")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaoHanh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaBaoHanh")
    private Integer maBaoHanh;

    @NotNull(message = "MaSanPham is required")
    @Column(name = "MaSanPham", nullable = false)
    private Integer maSanPham;

    @NotNull(message = "ThoiHanBaoHanh is required")
    @Column(name = "ThoiHanBaoHanh", nullable = false)
    private Integer thoiHanBaoHanh;

    @Column(name = "NgayBatDau")
    private LocalDateTime ngayBatDau;

    @Column(name = "NgayKetThuc")
    private LocalDateTime ngayKetThuc;

    @Size(max = 500, message = "DieuKienBaoHanh must not exceed 500 characters")
    @Column(name = "DieuKienBaoHanh")
    private String dieuKienBaoHanh;

    @Column(name = "TrangThai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangThai;

    @ManyToOne
    @JoinColumn(name = "MaSanPham", insertable = false, updatable = false)
    private SanPham sanPham;
}