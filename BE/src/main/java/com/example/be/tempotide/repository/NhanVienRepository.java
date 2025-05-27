package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    List<NhanVien> findByTrangthaiTrue();
    Optional<NhanVien> findByEmail(String email);
}