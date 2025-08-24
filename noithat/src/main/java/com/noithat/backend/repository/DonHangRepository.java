package com.noithat.backend.repository;

import com.noithat.backend.entity.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonHangRepository extends JpaRepository<DonHang, Integer> {
    List<DonHang> findByKhachHang_Id(Integer khachHangId);
    List<DonHang> findByTrangthaidonhang(String trangthaidonhang);
}
