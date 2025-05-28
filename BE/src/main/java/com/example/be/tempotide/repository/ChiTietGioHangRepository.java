package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.ChiTietGioHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChiTietGioHangRepository extends JpaRepository<ChiTietGioHang, Integer> {
    List<ChiTietGioHang> findByMagiohang_Magiohang(Integer magiohang);
}