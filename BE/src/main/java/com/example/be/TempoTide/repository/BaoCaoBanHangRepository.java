package com.example.be.TempoTide.repository;

import com.example.be.TempoTide.entity.BaoCaoBanHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaoCaoBanHangRepository extends JpaRepository<BaoCaoBanHang, Integer> {
}