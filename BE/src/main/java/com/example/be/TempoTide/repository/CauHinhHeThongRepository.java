package com.example.be.TempoTide.repository;

import com.example.be.TempoTide.entity.CauHinhHeThong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CauHinhHeThongRepository extends JpaRepository<CauHinhHeThong, Integer> {
}