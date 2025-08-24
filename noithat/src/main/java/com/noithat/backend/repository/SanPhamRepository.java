package com.noithat.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.noithat.backend.entity.SanPham;

public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    List<SanPham> findByTensanphamContainingIgnoreCase(String tensanpham);
    Page<SanPham> findByTensanphamContainingIgnoreCase(String tensanpham, Pageable pageable);
    List<SanPham> findByDanhmuc_Id(Integer danhmucId);

    @Query(value = "SELECT * FROM SanPham s WHERE REPLACE(s.tensanpham, ' ', '') COLLATE Vietnamese_CI_AI LIKE CONCAT('%', REPLACE(:keyword, ' ', ''), '%')", nativeQuery = true)
    List<SanPham> intelligentSearch(@Param("keyword") String keyword);

 // ✅ THÊM METHOD NÀY CHO CHAT BOT
    List<SanPham> findByTensanphamContainingIgnoreCaseAndTrangthaiTrue(String keyword);
    
    // ✅ HOẶC method tổng quát hơn (nếu field tên khác)
    List<SanPham> findByTrangthaiTrue();

}
