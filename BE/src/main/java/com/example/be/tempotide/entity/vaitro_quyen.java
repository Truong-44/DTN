package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "VaiTro_Quyen")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class vaitro_quyen {

    @EmbeddedId
    private VaiTro_QuyenId id;

    @ManyToOne
    @MapsId("maVaiTro")
    @JoinColumn(name = "MaVaiTro", nullable = false)
    private vaitro vaiTro;

    @ManyToOne
    @MapsId("maQuyen")
    @JoinColumn(name = "MaQuyen", nullable = false)
    private quyen quyen;

    @Column(name = "TrangThai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangThai;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class VaiTro_QuyenId implements Serializable {
        @Column(name = "MaVaiTro")
        private Integer maVaiTro;

        @Column(name = "MaQuyen")
        private Integer maQuyen;
    }
}