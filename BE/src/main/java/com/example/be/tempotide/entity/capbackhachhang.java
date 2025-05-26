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
    @Column(name = "MaCapBac")
    private Integer maCapBac;

    @NotBlank(message = "TenCapBac is required")
    @Size(max = 50, message = "TenCapBac must not exceed 50 characters")
    @Column(name = "TenCapBac", nullable = false)
    private String tenCapBac;

    @NotNull(message = "DiemToiThieu is required")
    @Min(value = 0, message = "DiemToiThieu must be greater than or equal to 0")
    @Column(name = "DiemToiThieu", nullable = false)
    private Integer diemToiThieu;

    @Column(name = "GiamGiaMacDinh")
    @DecimalMin(value = "0.00", message = "GiamGiaMacDinh must be between 0 and 100")
    @DecimalMax(value = "100.00", message = "GiamGiaMacDinh must be between 0 and 100")
    private Float giamGiaMacDinh;

    @Column(name = "NgayTao")
    private LocalDateTime ngayTao;

    @Column(name = "TrangThai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangThai;

    @Column(name = "NguoiTao")
    private Integer nguoiTao;

    @ManyToOne
    @JoinColumn(name = "NguoiTao", insertable = false, updatable = false)
    private nhanvien nguoiTaoNhanvien;
}