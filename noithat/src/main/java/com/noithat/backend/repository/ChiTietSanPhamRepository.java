package com.noithat.backend.repository;

import com.noithat.backend.entity.ChiTietSanPham;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, Integer> {
    List<ChiTietSanPham> findBySoluongGreaterThan(Integer soluong);
    List<ChiTietSanPham> findBySanpham_Id(Integer sanphamId);
    List<ChiTietSanPham> findBySanpham_IdAndTenmauContainingIgnoreCase(Integer sanphamId, String tenmau);
    List<ChiTietSanPham> findBySanpham_IdAndKichthuoc(Integer sanphamId, String kichthuoc);
}
