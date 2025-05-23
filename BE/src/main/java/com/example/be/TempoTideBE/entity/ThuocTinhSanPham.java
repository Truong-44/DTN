package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ThuocTinhSanPham")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThuocTinhSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maThuocTinh;

    @Column(nullable = false, unique = true)
    private String tenThuocTinh;

    private String moTa;

    private String loaiGiaTri;

    private LocalDateTime ngayTao = LocalDateTime.now();

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiTao")
    private NhanVien nguoiTao;
}