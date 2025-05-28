package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DonHangRepository extends JpaRepository<DonHang, Integer> {
    List<DonHang> findByMakhachhang_Makhachhang(Integer makhachhang);
    List<DonHang> findByNgaydathangBetween(LocalDateTime start, LocalDateTime end);
}