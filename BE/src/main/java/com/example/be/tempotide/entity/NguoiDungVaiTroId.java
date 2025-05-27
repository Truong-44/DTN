package com.example.be.tempotide.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class NguoiDungVaiTroId implements Serializable {
    @Column(name = "manguoidung")
    private Integer manguoidung;

    @Column(name = "mavaitro")
    private Integer mavaitro;

    @Column(name = "loainguoidung", length = 20)
    private String loainguoidung;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NguoiDungVaiTroId that = (NguoiDungVaiTroId) o;
        return Objects.equals(manguoidung, that.manguoidung) &&
                Objects.equals(mavaitro, that.mavaitro) &&
                Objects.equals(loainguoidung, that.loainguoidung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manguoidung, mavaitro, loainguoidung);
    }
}