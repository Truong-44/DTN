package com.noithat.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "phuongthucvanchuyen")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhuongThucVanChuyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String ten;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String mota;

    private String thoigiandukien;

    private BigDecimal phivanchuyen;
    
    @Builder.Default
    private Boolean trangthai = true;
}
