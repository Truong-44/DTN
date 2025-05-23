package com.example.be.TempoTideBE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "NguoiDung_VaiTro")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NguoiDungVaiTro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maNguoiDungVaiTro;

    @ManyToOne
    @JoinColumn(name = "MaNhanVien", nullable = false)
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "MaVaiTro", nullable = false)
    private VaiTro vaiTro;

    private LocalDateTime ngayTao = LocalDateTime.now();

    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "NguoiTao")
    private NhanVien nguoiTao;

    // Đảm bảo constraint UNIQUE (MaNhanVien, MaVaiTro) được xử lý ở mức ứng dụng nếu cần
}