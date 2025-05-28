package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.NguoiDungVaiTro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NguoiDungVaiTroRepository extends JpaRepository<NguoiDungVaiTro, NguoiDungVaiTroId> {
    List<NguoiDungVaiTro> findByIdManguoidungAndIdLoainguoidung(Integer manguoidung, String loainguoidung);
    boolean existsByIdManguoidungAndIdMavaitroAndIdLoainguoidung(Integer manguoidung, Integer mavaitro, String loainguoidung);
}