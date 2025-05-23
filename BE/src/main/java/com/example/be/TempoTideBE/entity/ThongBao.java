package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ThongBao")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongBao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maThongBao;

    @ManyToOne
    @JoinColumn(name = "MaNhanVien", nullable = false)
    private NhanVien nhanVien;

    @Column(nullable = false)
    private String tieuDe;

    @Column(nullable = false)
    private String noiDung;

    private LocalDateTime ngayTao = LocalDateTime.now();

    private Boolean trangThai = true;
}