package com.example.be.TempoTide.repository;

import com.example.be.TempoTide.entity.VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaiTroRepository extends JpaRepository<VaiTro, Integer> {
    boolean existsByTenVaiTro(String tenVaiTro);
}