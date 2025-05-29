package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Integer> {
    List<DonHang> findByNgaytaoBetween(LocalDateTime startDate, LocalDateTime endDate); // Thêm phương thức này
    List<DonHang> findByMakhachhangMakhachhang(Integer makhachhang); // Thêm phương thức này cho getDonHangByKhachHangId
}