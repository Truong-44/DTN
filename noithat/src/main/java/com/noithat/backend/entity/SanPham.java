package com.noithat.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.List;

@Entity
@Table(name = "sanpham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String tensanpham;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String mota;

    @ManyToOne
    @JoinColumn(name = "danhmucid", nullable = false)
    private DanhMuc danhmuc;

    @Column(length = 100)
    private String tenloai;

    private BigDecimal giacu;
    private BigDecimal giamoi;

    @CreationTimestamp
    private LocalDateTime ngaytao;

    @Builder.Default
    private Boolean trangthai = true;

    @OneToMany(mappedBy = "sanpham", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChiTietSanPham> chiTietSanPhams;
}
