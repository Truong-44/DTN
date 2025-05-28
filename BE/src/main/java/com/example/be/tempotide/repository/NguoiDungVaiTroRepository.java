package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.NguoiDungVaiTro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NguoiDungVaiTroRepository extends JpaRepository<NguoiDungVaiTro, Integer> {
    List<NguoiDungVaiTro> findByManhanvien_Manhanvien(Integer manhanvien);
    List<NguoiDungVaiTro> findByMavaitro_Mavaitro(Integer mavaitro);
}