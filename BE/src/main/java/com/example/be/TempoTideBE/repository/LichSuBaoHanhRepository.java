package com.example.be.TempoTideBE.repository;

import com.example.be.TempoTideBE.entity.LichSuBaoHanh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LichSuBaoHanhRepository extends JpaRepository<LichSuBaoHanh, Integer> {
}
