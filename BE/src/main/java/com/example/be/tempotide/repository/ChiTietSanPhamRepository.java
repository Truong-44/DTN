package com.example.temp.repository;

import com.example.temp.entity.ChiTietSanPham;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, Long> {
    List<ChiTietSanPham> findByTrangthaiTrue();
    Optional<ChiTietSanPham> findBySku(long sku);
}