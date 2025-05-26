package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "NguoiDung_VaiTro")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class nguoidung_vaitro {

    @EmbeddedId
    private NguoiDung_VaiTroId id;

    @ManyToOne
    @MapsId("maNguoiDung")
    @JoinColumn(name = "MaNguoiDung", nullable = false)
    private nhanvien nguoiDung;

    @ManyToOne
    @MapsId("maVaiTro")
    @JoinColumn(name = "MaVaiTro", nullable = false)
    private vaitro vaiTro;

    @Column(name = "TrangThai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangThai;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class NguoiDung_VaiTroId implements Serializable {
        @Column(name = "MaNguoiDung")
        private Integer maNguoiDung;

        @Column(name = "MaVaiTro")
        private Integer maVaiTro;
    }
}