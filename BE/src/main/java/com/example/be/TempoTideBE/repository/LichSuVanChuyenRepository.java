package com.example.be.TempoTideBE.repository;

import com.example.be.TempoTideBE.entity.LichSuVanChuyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LichSuVanChuyenRepository extends JpaRepository<LichSuVanChuyen, Integer> {
}
