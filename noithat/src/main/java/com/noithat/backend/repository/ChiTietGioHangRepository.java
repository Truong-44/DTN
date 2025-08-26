package com.noithat.backend.repository;

import com.noithat.backend.entity.ChiTietGioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChiTietGioHangRepository extends JpaRepository<ChiTietGioHang, Integer> {
    List<ChiTietGioHang> findByGioHang_Id(Integer giohangId);
    
    @Modifying
    @Query("DELETE FROM ChiTietGioHang c WHERE c.gioHang.id = :giohangId")
    void deleteByGioHangId(@Param("giohangId") Integer giohangId);
}
