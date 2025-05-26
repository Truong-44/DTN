package com.example.be.TempoTide.repository;

import com.example.be.TempoTide.entity.PhieuNhapHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuNhapHangRepository extends JpaRepository<PhieuNhapHang, Integer> {
}
