package com.example.be.TempoTide.repository;

import com.example.be.TempoTide.entity.NguoiDung_VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NguoiDung_VaiTroRepository extends JpaRepository<NguoiDung_VaiTro, NguoiDung_VaiTro.NguoiDung_VaiTroId> {
}