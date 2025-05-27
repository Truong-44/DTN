package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    boolean existsByTensanpham(String tensanpham);
    Page<SanPham> findByTensanphamContainingIgnoreCase(String tensanpham, Pageable pageable);
}