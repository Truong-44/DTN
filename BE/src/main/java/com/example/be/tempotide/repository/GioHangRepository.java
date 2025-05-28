package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GioHangRepository extends JpaRepository<GioHang, Integer> {
    Optional<GioHang> findByMakhachhang_Makhachhang(Integer makhachhang);
    Optional<GioHang> findBySodienthoai(String sodienthoai);
}