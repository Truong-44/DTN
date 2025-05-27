package com.example.be.tempotide.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "nguoidung_vaitro")
@Getter
@Setter
public class NguoiDungVaiTro {
    @EmbeddedId
    private NguoiDungVaiTroId id;

    @ManyToOne
    @MapsId("mavaitro")
    @JoinColumn(name = "mavaitro")
    private VaiTro vaiTro;
}