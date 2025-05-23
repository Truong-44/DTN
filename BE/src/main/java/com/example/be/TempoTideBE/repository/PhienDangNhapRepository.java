package com.example.be.TempoTideBE.repository;

import com.example.be.TempoTideBE.entity.PhienDangNhap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhienDangNhapRepository extends JpaRepository<PhienDangNhap, Integer> {
}
