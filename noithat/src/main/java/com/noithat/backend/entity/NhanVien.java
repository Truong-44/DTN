package com.noithat.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "nhanvien")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String hoten;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 20)
    private String sodienthoai;

    @Column(length = 100)
    private String chucvu;

    @CreationTimestamp
    private LocalDateTime ngayvaolam;

    @OneToOne
    @JoinColumn(name = "taikhoanid", unique = true)
    private TaiKhoan taikhoan;
}
