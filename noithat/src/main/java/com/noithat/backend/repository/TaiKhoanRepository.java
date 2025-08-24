package com.noithat.backend.repository;

import com.noithat.backend.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {
    Optional<TaiKhoan> findByTendangnhap(String tendangnhap);
    boolean existsByTendangnhap(String tendangnhap);
    Optional<TaiKhoan> findByEmail(String email);
    boolean existsByEmail(String email);
    
    // Fetch với cả khách hàng và nhân viên
    @Query("SELECT t FROM TaiKhoan t LEFT JOIN FETCH t.khachhang LEFT JOIN FETCH t.nhanvien WHERE t.tendangnhap = :tendangnhap")
    Optional<TaiKhoan> findByTendangnhapWithDetails(@Param("tendangnhap") String tendangnhap);

    @Query("SELECT t FROM TaiKhoan t LEFT JOIN FETCH t.khachhang LEFT JOIN FETCH t.nhanvien WHERE t.id = :id")
    Optional<TaiKhoan> findByIdWithDetails(@Param("id") Integer id);
}
