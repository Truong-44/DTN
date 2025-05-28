package com.example.tempotide.repository;

import com.example.tempotide.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    List<SanPham> findByTrangthaiTrue();
    Optional<SanPham> findByTensanpham(String tensanpham);
}