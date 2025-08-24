package com.noithat.backend.repository;

import com.noithat.backend.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    List<HoaDon> findByNgayxuathoadonBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<HoaDon> findByDonHang_KhachHang_Id(Integer khachHangId);
}
