package com.example.be.TempoTideBE.repository;

import com.example.be.TempoTideBE.entity.YeuCauBaoHanh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YeuCauBaoHanhRepository extends JpaRepository<YeuCauBaoHanh, Integer> {
}
