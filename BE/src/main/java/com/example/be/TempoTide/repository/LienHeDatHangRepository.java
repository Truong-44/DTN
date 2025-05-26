package com.example.be.TempoTide.repository;

import com.example.be.TempoTide.entity.LienHeDatHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LienHeDatHangRepository extends JpaRepository<LienHeDatHang, Integer> {
}
