package com.example.be.TempoTideBE.repository;

import com.example.be.TempoTideBE.entity.BaoHanh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaoHanhRepository extends JpaRepository<BaoHanh, Integer> {
}
