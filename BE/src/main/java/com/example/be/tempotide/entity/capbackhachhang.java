package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "capbackhachhang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class capbackhachhang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "macapbac")
    private Integer macapbac;

    @NotBlank(message = "tencapbac is required")
    @Size(max = 50, message = "tencapbac must not exceed 50 characters")
    @Column(name = "tencapbac", nullable = false)
    private String tencapbac;

    @NotNull(message = "diemtoithieu is required")
    @Min(value = 0, message = "diemtoithieu must be greater than or equal to 0")
    @Column(name = "diemtoithieu", nullable = false)
    private Integer diemtoithieu;

    @Column(name = "giamgiamacdinh")
    @DecimalMin(value = "0.00", message = "giamgiamacdinh must be between 0 and 100")
    @DecimalMax(value = "100.00", message = "giamgiamacdinh must be between 0 and 100")
    private Float giamgiamacdinh;

    @Column(name = "ngaytao")
    private LocalDateTime ngaytao;

    @Column(name = "trangthai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangthai;

    @Column(name = "nguoitao")
    private Integer nguoitao;

    @ManyToOne
    @JoinColumn(name = "nguoitao", insertable = false, updatable = false)
    private nhanvien nhanvien;
}