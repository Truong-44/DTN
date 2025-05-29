package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.ChiTietDonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietDonHangRepository extends JpaRepository<ChiTietDonHang, Integer> {
    List<ChiTietDonHang> findByMadonhangMadonhang(Integer madonhang); // Thêm phương thức này
}