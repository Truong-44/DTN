package com.example.be.TempoTide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "VaiTro_Quyen")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaiTro_Quyen {

    @EmbeddedId
    private VaiTro_QuyenId id;

    @ManyToOne
    @MapsId("maVaiTro")
    @JoinColumn(name = "MaVaiTro", nullable = false)
    private VaiTro vaiTro;

    @ManyToOne
    @MapsId("maQuyen")
    @JoinColumn(name = "MaQuyen", nullable = false)
    private Quyen quyen;

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