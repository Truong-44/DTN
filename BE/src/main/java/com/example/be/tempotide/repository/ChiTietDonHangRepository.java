package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.ChiTietDonHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChiTietDonHangRepository extends JpaRepository<ChiTietDonHang, Integer> {
    List<ChiTietDonHang> findByMadonhang_Madonhang(Integer madonhang);
}