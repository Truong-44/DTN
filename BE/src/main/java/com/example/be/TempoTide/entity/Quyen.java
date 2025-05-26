package com.example.be.TempoTide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Quyen")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaQuyen")
    private Integer maQuyen;

    @NotBlank(message = "TenQuyen is required")
    @Size(max = 50, message = "TenQuyen must not exceed 50 characters")
    @Column(name = "TenQuyen", nullable = false, unique = true)
    private String tenQuyen;

    @Size(max = 255, message = "MoTa must not exceed 255 characters")
    @Column(name = "MoTa")
    private String moTa;

    @NotNull(message = "NgayTao is required")
    @Column(name = "NgayTao", nullable = false, updatable = false)
    private LocalDateTime ngayTao = LocalDateTime.now();

    @NotNull(message = "NgayCapNhat is required")
    @Column(name = "NgayCapNhat", nullable = false)
    private LocalDateTime ngayCapNhat = LocalDateTime.now();
}