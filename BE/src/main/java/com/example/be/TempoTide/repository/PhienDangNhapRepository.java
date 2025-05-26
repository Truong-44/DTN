package com.example.be.TempoTide.repository;

import com.example.be.TempoTide.entity.PhienDangNhap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhienDangNhapRepository extends JpaRepository<PhienDangNhap, Integer> {
}