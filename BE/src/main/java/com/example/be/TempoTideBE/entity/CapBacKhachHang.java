package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "CapBacKhachHang")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapBacKhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maCapBac;

    @Column(nullable = false, unique = true)
    private String tenCapBac;

    @Column(nullable = false)
    private Integer diemToiThieu;

    private Double giamGiaMacDinh;

    private LocalDateTime ngayTao = LocalDateTime.now();

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiTao")
    private NhanVien nguoiTao;
}