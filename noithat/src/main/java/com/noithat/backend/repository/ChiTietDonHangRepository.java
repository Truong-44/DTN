package com.noithat.backend.repository;

import com.noithat.backend.entity.ChiTietDonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ChiTietDonHangRepository extends JpaRepository<ChiTietDonHang, Integer> {
    List<ChiTietDonHang> findByDonHang_Id(Integer donhangId);
    
    @Query("SELECT SUM(c.soluong * c.dongia) FROM ChiTietDonHang c WHERE c.donHang.id = :donhangId")
    BigDecimal calculateTotalByDonHangId(@Param("donhangId") Integer donhangId);
    
    @Query("SELECT SUM(c.soluong) FROM ChiTietDonHang c WHERE c.donHang.id = :donhangId")
    Integer countItemsByDonHangId(@Param("donhangId") Integer donhangId);
}
