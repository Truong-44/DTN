package com.example.tempotide.repository;

import com.example.tempotide.entity.ChiTietGioHang;
import com.example.tempotide.entity.ChiTietGioHangId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChiTietGioHangRepository extends JpaRepository<ChiTietGioHang, ChiTietGioHangId> {
    List<ChiTietGioHang> findByGiohangMagiohang(Integer magiohang);
}