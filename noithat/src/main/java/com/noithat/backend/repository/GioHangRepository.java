package com.noithat.backend.repository;

import com.noithat.backend.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GioHangRepository extends JpaRepository<GioHang, Integer> {
    Optional<GioHang> findByKhachHang_Id(Integer khachHangId);
}
