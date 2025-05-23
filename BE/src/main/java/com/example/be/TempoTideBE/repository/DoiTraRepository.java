package com.example.be.TempoTideBE.repository;

import com.example.be.TempoTideBE.entity.DoiTra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoiTraRepository extends JpaRepository<DoiTra, Integer> {
}
