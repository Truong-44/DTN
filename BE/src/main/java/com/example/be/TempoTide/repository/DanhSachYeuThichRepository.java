package com.example.be.TempoTide.repository;

import com.example.be.TempoTide.entity.DanhSachYeuThich;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanhSachYeuThichRepository extends JpaRepository<DanhSachYeuThich, Integer> {
}