package com.example.be.TempoTide.repository;

import com.example.be.TempoTide.entity.KhoHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhoHangRepository extends JpaRepository<KhoHang, Integer> {
}