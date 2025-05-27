package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chitietsanpham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class chitietsanpham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machitetsanpham")
    private Integer machitetsanpham;

    @NotNull(message = "masanpham is required")
    @Column(name = "masanpham", nullable = false)
    private Integer masanpham;

    @Column(name = "soluongton")
    private Integer soluongton;

    @Column(name = "giaban")
    private Double giaban;

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