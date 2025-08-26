package com.noithat.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "taikhoan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaiKhoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String tendangnhap;

    @Column(nullable = false, length = 255)
    private String matkhau;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String sodienthoai;

    @Builder.Default
    private Boolean trangthai = true;

    @CreationTimestamp
    private LocalDateTime ngaytao;

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String loaidangnhap = "email";

    @ManyToOne
    @JoinColumn(name = "quyenid")
    private Quyen quyen;

    // Bi-directional relationship với KhachHang
    @OneToOne(mappedBy = "taikhoan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private KhachHang khachhang;

    // Bi-directional relationship với NhanVien
    @OneToOne(mappedBy = "taikhoan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private NhanVien nhanvien;
}
