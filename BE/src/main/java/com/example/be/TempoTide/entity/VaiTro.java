package com.example.be.TempoTide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "VaiTro")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaiTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaVaiTro")
    private Integer maVaiTro;

    @NotBlank(message = "TenVaiTro is required")
    @Size(max = 50, message = "TenVaiTro must not exceed 50 characters")
    @Column(name = "TenVaiTro", nullable = false, unique = true)
    private String tenVaiTro;

    @Size(max = 255, message = "MoTa must not exceed 255 characters")
    @Column(name = "MoTa")
    private String moTa;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "VaiTro_Quyen",
            joinColumns = @JoinColumn(name = "MaVaiTro"),
            inverseJoinColumns = @JoinColumn(name = "MaQuyen")
    )
    private List<Quyen> quyens;

    @NotNull(message = "NgayTao is required")
    @Column(name = "NgayTao", nullable = false, updatable = false)
    private LocalDateTime ngayTao = LocalDateTime.now();

    @NotNull(message = "NgayCapNhat is required")
    @Column(name = "NgayCapNhat", nullable = false)
    private LocalDateTime ngayCapNhat = LocalDateTime.now();
}