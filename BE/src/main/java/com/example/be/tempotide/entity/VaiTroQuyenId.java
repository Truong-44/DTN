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
public class VaiTroQuyenId implements Serializable {
    @Column(name = "mavaitro")
    private Integer mavaitro;

    @Column(name = "maquyen")
    private Integer maquyen;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VaiTroQuyenId that = (VaiTroQuyenId) o;
        return Objects.equals(mavaitro, that.mavaitro) && Objects.equals(maquyen, that.maquyen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mavaitro, maquyen);
    }
}