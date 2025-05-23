package com.example.be.TempoTideBE.repository;

import com.example.be.TempoTideBE.entity.BaoCaoBanHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaoCaoBanHangRepository extends JpaRepository<BaoCaoBanHang, Integer> {
}
