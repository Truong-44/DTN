package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.ChiTietSanPham;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, Integer> {
    List<ChiTietSanPham> findByMasanpham_Masanpham(Integer masanpham);
    Optional<ChiTietSanPham> findBySku(String sku);
}