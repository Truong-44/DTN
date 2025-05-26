package com.example.be.TempoTide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "NguoiDung_VaiTro")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NguoiDung_VaiTro {

    @EmbeddedId
    private NguoiDung_VaiTroId id;

    @ManyToOne
    @MapsId("maNguoiDung")
    @JoinColumn(name = "MaNguoiDung", nullable = false)
    private NhanVien nguoiDung;

    @ManyToOne
    @MapsId("maVaiTro")
    @JoinColumn(name = "MaVaiTro", nullable = false)
    private VaiTro vaiTro;

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