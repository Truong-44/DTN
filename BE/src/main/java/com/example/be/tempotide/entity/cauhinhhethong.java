package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cauhinhhethong")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class cauhinhhethong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "macauhinh")
    private Integer macauhinh;

    @NotBlank(message = "tencauhinh is required")
    @Size(max = 100, message = "tencauhinh must not exceed 100 characters")
    @Column(name = "tencauhinh", nullable = false)
    private String tencauhinh;

    @NotBlank(message = "giatri is required")
    @Size(max = 500, message = "giatri must not exceed 500 characters")
    @Column(name = "giatri", nullable = false)
    private String giatri;

    @Column(name = "mota")
    @Size(max = 200, message = "mota must not exceed 200 characters")
    private String mota;

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