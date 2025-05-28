package com.example.tempotide.repository;

import com.example.tempotide.entity.ChiTietDonHang;
import com.example.tempotide.entity.ChiTietDonHangId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChiTietDonHangRepository extends JpaRepository<ChiTietDonHang, ChiTietDonHangId> {
    List<ChiTietDonHang> findByDonhangMadonhang(Integer madonhang);
}