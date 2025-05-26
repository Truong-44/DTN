package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "ThuocTinhSanPham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class thuoctinhsanpham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaThuocTinh")
    private Integer maThuocTinh;

    @NotNull(message = "MaSanPham is required")
    @Column(name = "MaSanPham", nullable = false)
    private Integer maSanPham;

    @NotBlank(message = "TenThuocTinh is required")
    @Size(max = 100, message = "TenThuocTinh must not exceed 100 characters")
    @Column(name = "TenThuocTinh", nullable = false)
    private String tenThuocTinh;

    @NotBlank(message = "GiaTri is required")
    @Size(max = 200, message = "GiaTri must not exceed 200 characters")
    @Column(name = "GiaTri", nullable = false)
    private String giaTri;

    @Column(name = "TrangThai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangThai;

    @ManyToOne
    @JoinColumn(name = "MaSanPham", insertable = false, updatable = false)
    private sanpham sanPham;
}