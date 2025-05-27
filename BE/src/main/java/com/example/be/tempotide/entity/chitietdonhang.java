package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "chitietdonhang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class chitietdonhang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machitietdonhang")
    private Integer machitietdonhang;

    @ManyToOne
    @JoinColumn(name = "madonhang", referencedColumnName = "madonhang", nullable = false)
    private donhang donhang;

    @ManyToOne
    @JoinColumn(name = "machitetsanpham", referencedColumnName = "masanpham", nullable = false)
    private sanpham sanpham;

    @NotNull(message = "soluong is required")
    @Min(value = 1, message = "soluong must be at least 1")
    @Column(name = "soluong", nullable = false)
    private Integer soluong;

    @NotNull(message = "dongia is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "dongia must be non-negative")
    @Column(name = "dongia", nullable = false)
    private BigDecimal dongia;

    @Column(name = "giamgia")
    @DecimalMin(value = "0.0", inclusive = true, message = "giamgia must be non-negative")
    private BigDecimal giamgia;

    @Column(name = "ladattruoc", columnDefinition = "BIT DEFAULT 0")
    private Boolean ladattruoc;

    @Column(name = "ngaytao")
    private LocalDateTime ngaytao;

    @Column(name = "ngaycapnhat")
    private LocalDateTime ngaycapnhat;

    @Column(name = "trangthai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangthai;

    @Column(name = "nguoitao")
    private Integer nguoitao;

    @Column(name = "nguoicapnhat")
    private Integer nguoicapnhat;

    @ManyToOne
    @JoinColumn(name = "nguoitao", insertable = false, updatable = false)
    private nhanvien nguoitao_nhanvien;

    @ManyToOne
    @JoinColumn(name = "nguoicapnhat", insertable = false, updatable = false)
    private nhanvien nguoicapnhat_nhanvien;
}