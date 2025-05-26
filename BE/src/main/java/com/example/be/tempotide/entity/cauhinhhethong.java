package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "CauHinhHeThong")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class cauhinhhethong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCauHinh")
    private Integer maCauHinh;

    @NotBlank(message = "TenCauHinh is required")
    @Size(max = 100, message = "TenCauHinh must not exceed 100 characters")
    @Column(name = "TenCauHinh", nullable = false)
    private String tenCauHinh;

    @NotBlank(message = "GiaTri is required")
    @Size(max = 500, message = "GiaTri must not exceed 500 characters")
    @Column(name = "GiaTri", nullable = false)
    private String giaTri;

    @Column(name = "MoTa")
    @Size(max = 1000, message = "MoTa must not exceed 1000 characters")
    private String moTa;

    @Column(name = "TrangThai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangThai;
}