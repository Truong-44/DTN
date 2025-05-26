package com.example.be.TempoTide.repository;

import com.example.be.TempoTide.entity.ThuocTinhSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThuocTinhSanPhamRepository extends JpaRepository<ThuocTinhSanPham, Integer> {
}