package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LichSuDonHang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class lichsudonhang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaLichSu")
    private Integer maLichSu;

    @NotNull(message = "MaDonHang is required")
    @Column(name = "MaDonHang", nullable = false)
    private Integer maDonHang;

    @Size(max = 50, message = "TrangThaiCu must not exceed 50 characters")
    @Column(name = "TrangThaiCu")
    private String trangThaiCu;

    @Size(max = 50, message = "TrangThaiMoi must not exceed 50 characters")
    @Column(name = "TrangThaiMoi")
    private String trangThaiMoi;

    @Size(max = 500, message = "LyDo must not exceed 500 characters")
    @Column(name = "LyDo")
    private String lyDo;

    @Column(name = "NgayCapNhat", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime ngayCapNhat;

    @ManyToOne
    @JoinColumn(name = "NguoiCapNhat", referencedColumnName = "MaNhanVien")
    private nhanvien nguoiCapNhat;

    @ManyToOne
    @JoinColumn(name = "MaDonHang", insertable = false, updatable = false)
    private donhang donHang;
}