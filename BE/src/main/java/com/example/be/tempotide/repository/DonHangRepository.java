package com.example.tempotide.repository;

import com.example.tempotide.entity.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonHangRepository extends JpaRepository<DonHang, Integer> {
    List<DonHang> findByKhachhangMakhachhang(Integer makhachhang);
}