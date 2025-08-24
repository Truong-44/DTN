package com.noithat.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "danhmuc")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanhMuc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String tendanhmuc;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String mota;

    @ManyToOne
    @JoinColumn(name = "danhmucchaid")
    private DanhMuc danhmucCha;

    @OneToMany(mappedBy = "danhmucCha")
    private List<DanhMuc> danhMucCon;
    
    @OneToMany(mappedBy = "danhmuc")
    private List<SanPham> sanPhams;
}
