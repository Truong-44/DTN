package com.noithat.backend.repository;

import com.noithat.backend.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    Optional<NhanVien> findByEmail(String email);
    boolean existsByEmail(String email);
    List<NhanVien> findByHotenContainingIgnoreCase(String hoten);
}
