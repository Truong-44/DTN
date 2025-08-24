package com.noithat.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "khachhang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String hoten;
    private String diachi;
    private LocalDate ngaysinh;
    private String gioitinh;

    @OneToOne
    @JoinColumn(name = "taikhoanid", unique = true, nullable = false)
    private TaiKhoan taikhoan;
}
