package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "baocaobanhang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class baocaobanhang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mabaocao")
    private Integer ma_bao_cao;

    @NotNull(message = "thoi_gian_bat_dau is required")
    @Column(name = "thoigianbatdau", nullable = false)
    private LocalDateTime thoi_gian_bat_dau;

    @NotNull(message = "thoi_gian_ket_thuc is required")
    @Column(name = "thoigianketthuc", nullable = false)
    private LocalDateTime thoi_gian_ket_thuc;

    @Column(name = "tongdoanhthu")
    private Double tong_doanh_thu;

    @Column(name = "sodonhang")
    private Integer so_don_hang;

    @Column(name = "trangthai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trang_thai;
}