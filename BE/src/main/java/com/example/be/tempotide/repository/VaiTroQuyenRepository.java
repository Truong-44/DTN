package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.VaiTroQuyen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VaiTroQuyenRepository extends JpaRepository<VaiTroQuyen, VaiTroQuyenId> {
    List<VaiTroQuyen> findByVaiTroMavaitro(Integer mavaitro);
    boolean existsByVaiTroMavaitroAndQuyenMaquyen(Integer mavaitro, Integer maquyen);
}