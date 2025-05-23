package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "LichSuThanhToan")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LichSuThanhToan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maLichSu;

    @ManyToOne
    @JoinColumn(name = "MaThanhToan", nullable = false)
    private ThanhToan thanhToan;

    @Column(nullable = false)
    private String trangThai;

    private BigDecimal soTien;

    private LocalDateTime ngayCapNhat = LocalDateTime.now();

    private String ghiChu;

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiCapNhat")
    private NhanVien nguoiCapNhat;
}