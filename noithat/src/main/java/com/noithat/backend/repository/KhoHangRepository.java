package com.noithat.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.noithat.backend.entity.KhoHang;

public interface KhoHangRepository extends JpaRepository<KhoHang, Integer> {
    Optional<KhoHang> findByChiTietSanPham_Id(Integer chitietSanphamId);
    
    @Query("SELECT k FROM KhoHang k WHERE k.soluongton <= :threshold ORDER BY k.soluongton ASC")
    List<KhoHang> findLowStockItems(@Param("threshold") Integer threshold);
}
