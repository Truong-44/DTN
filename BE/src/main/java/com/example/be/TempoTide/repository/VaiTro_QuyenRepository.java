package com.example.be.TempoTide.repository;

import com.example.be.TempoTide.entity.VaiTro_Quyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaiTro_QuyenRepository extends JpaRepository<VaiTro_Quyen, VaiTro_Quyen.VaiTro_QuyenId> {
}