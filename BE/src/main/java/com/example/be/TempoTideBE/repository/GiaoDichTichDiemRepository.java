package com.example.be.TempoTideBE.repository;

import com.example.be.TempoTideBE.entity.GiaoDichTichDiem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiaoDichTichDiemRepository extends JpaRepository<GiaoDichTichDiem, Integer> {
}
