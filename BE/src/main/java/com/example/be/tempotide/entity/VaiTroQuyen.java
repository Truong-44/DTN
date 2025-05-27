package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vaitro_quyen")
@Getter
@Setter
public class VaiTroQuyen {
    @EmbeddedId
    private VaiTroQuyenId id;

    @ManyToOne
    @MapsId("mavaitro")
    @JoinColumn(name = "mavaitro")
    private VaiTro vaiTro;

    @ManyToOne
    @MapsId("maquyen")
    @JoinColumn(name = "maquyen")
    private Quyen quyen;
}