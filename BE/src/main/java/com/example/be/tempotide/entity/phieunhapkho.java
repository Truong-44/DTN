package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PhieuNhapKho")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class phieunhapkho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaPhieuNhap")
    private Integer maPhieuNhap;

    @NotNull(message = "MaKho is required")
    @Column(name = "MaKho", nullable = false)
    private Integer maKho;

    @NotNull(message = "MaNhaCungCap is required")
    @Column(name = "MaNhaCungCap", nullable = false)
    private Integer maNhaCungCap;

    @NotNull(message = "TongTien is required")
    @Column(name = "TongTien", nullable = false)
    private Double tongTien;

    @Column(name = "NgayNhap", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime ngayNhap;

    @Size(max = 500, message = "GhiChu must not exceed 500 characters")
    @Column(name = "GhiChu")
    private String ghiChu;

    @Column(name = "TrangThai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangThai;

    @ManyToOne
    @JoinColumn(name = "NguoiTao", referencedColumnName = "MaNhanVien")
    private nhanvien nguoiTao;

    @ManyToOne
    @JoinColumn(name = "NguoiCapNhat", referencedColumnName = "MaNhanVien")
    private nhanvien nguoiCapNhat;

    @ManyToOne
    @JoinColumn(name = "MaKho", insertable = false, updatable = false)
    private khohang khoHang;

    @ManyToOne
    @JoinColumn(name = "MaNhaCungCap", insertable = false, updatable = false)
    private nhacungcap nhaCungCap;
}