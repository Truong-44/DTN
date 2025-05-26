package com.example.be.TempoTide.repository;

import com.example.be.TempoTide.entity.BaoHanh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaoHanhRepository extends JpaRepository<BaoHanh, Integer> {
}
