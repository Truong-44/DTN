package com.noithat.backend.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.noithat.backend.entity.PhuongThucVanChuyen;

public interface PhuongThucVanChuyenRepository extends JpaRepository<PhuongThucVanChuyen, Integer> {
    
    List<PhuongThucVanChuyen> findByTrangthaiTrue();
    
    @Query("SELECT p FROM PhuongThucVanChuyen p WHERE p.phivanchuyen BETWEEN :minPrice AND :maxPrice")
    List<PhuongThucVanChuyen> findByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
    
    @Query("SELECT p FROM PhuongThucVanChuyen p WHERE LOWER(p.ten) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<PhuongThucVanChuyen> searchByKeyword(@Param("keyword") String keyword);
}
