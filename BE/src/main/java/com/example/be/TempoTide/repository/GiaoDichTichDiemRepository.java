package com.example.be.TempoTide.repository;

import com.example.be.TempoTide.entity.GiaoDichTichDiem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiaoDichTichDiemRepository extends JpaRepository<GiaoDichTichDiem, Integer> {
}
