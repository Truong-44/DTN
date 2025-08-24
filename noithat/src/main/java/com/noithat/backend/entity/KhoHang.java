package com.noithat.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "khohang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhoHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "chitietsanphamid", nullable = false, unique = true)
    private ChiTietSanPham chiTietSanPham;

    private Integer soluongton;

    @UpdateTimestamp
    private LocalDateTime ngaycapnhat;
}
