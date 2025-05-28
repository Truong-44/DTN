package com.example.tempotide.repository;

import com.example.tempotide.entity.ThuocTinhSanPham;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ThuocTinhSanPhamRepository extends JpaRepository<ThuocTinhSanPham, Integer> {
    List<ThuocTinhSanPham> findByTrangthaiTrue();
    Optional<ThuocTinhSanPham> findByTenthuoctinh(String tenthuoctinh);
}