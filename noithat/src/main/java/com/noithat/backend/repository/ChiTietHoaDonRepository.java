package com.noithat.backend.repository;

import com.noithat.backend.entity.ChiTietHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ChiTietHoaDonRepository extends JpaRepository<ChiTietHoaDon, Integer> {
    List<ChiTietHoaDon> findByHoaDon_Id(Integer hoadonId);
    
    @Query("SELECT SUM(c.soluong * c.dongia) FROM ChiTietHoaDon c WHERE c.hoaDon.id = :hoadonId")
    BigDecimal calculateTotalByHoaDonId(@Param("hoadonId") Integer hoadonId);
    
    @Query("SELECT SUM(c.soluong) FROM ChiTietHoaDon c WHERE c.hoaDon.id = :hoadonId")
    Integer countItemsByHoaDonId(@Param("hoadonId") Integer hoadonId);
}
