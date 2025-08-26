package com.noithat.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "taikhoanlienket", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"loaidangnhap", "giatridangnhap"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaiKhoanLienKet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String loaidangnhap;
    private String giatridangnhap;

    @ManyToOne
    @JoinColumn(name = "taikhoanid", nullable = false)
    private TaiKhoan taikhoan;
}
