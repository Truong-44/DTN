package com.example.tempotide.repository;

import com.example.tempotide.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GioHangRepository extends JpaRepository<GioHang, Integer> {
    List<GioHang> findByTrangthaiTrue();
    Optional<GioHang> findByKhachhangMakhachhangAndTrangthaiTrue(Integer makhachhang);
    Optional<GioHang> findBySodienthoaiAndTrangthaiTrue(String sodienthoai);
}