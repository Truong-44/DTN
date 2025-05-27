package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "baohanh")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class baohanh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mabaohanh")
    private Integer mabaohanh;

    @NotNull(message = "masanpham is required")
    @Column(name = "masanpham", nullable = false)
    private Integer masanpham;

    @NotNull(message = "thoigianbaohanh is required")
    @Column(name = "thoigianbaohanh", nullable = false)
    @Min(value = 1, message = "thoigianbaohanh must be greater than 0")
    private Integer thoigianbaohanh;

    @Size(max = 500, message = "dieukienbaohanh must not exceed 500 characters")
    @Column(name = "dieukienbaohanh")
    private String dieukienbaohanh;

    @Column(name = "ngaytao")
    private LocalDateTime ngaytao;

    @Column(name = "trangthai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangthai;

    @Column(name = "nguoitao")
    private Integer nguoitao;

    @ManyToOne
    @JoinColumn(name = "masanpham", insertable = false, updatable = false)
    private sanpham sanpham;

    @ManyToOne
    @JoinColumn(name = "nguoitao", insertable = false, updatable = false)
    private nhanvien nhanvien;
}