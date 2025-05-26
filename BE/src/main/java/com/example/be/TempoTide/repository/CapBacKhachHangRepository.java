package com.example.be.TempoTide.repository;

import com.example.be.TempoTide.entity.CapBacKhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapBacKhachHangRepository extends JpaRepository<CapBacKhachHang, Integer> {
}