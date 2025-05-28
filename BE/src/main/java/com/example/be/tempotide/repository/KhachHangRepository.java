package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    Optional<KhachHang> findByEmail(String email);
    Optional<KhachHang> findBySodienthoai(String sodienthoai);
}