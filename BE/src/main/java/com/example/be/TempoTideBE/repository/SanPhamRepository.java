package com.example.be.TempoTideBE.repository;

import com.example.be.TempoTideBE.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    Optional<SanPham> findByTenSanPham(String tenSanPham);

    boolean existsByMaDanhMuc(Integer maDanhMuc);

    boolean existsByMaNhaCungCap(Integer maNhaCungCap);
}
