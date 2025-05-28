package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.DanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DanhMucRepository extends JpaRepository<DanhMuc, Integer> {
    List<DanhMuc> findByTrangthaiTrue();
    Optional<DanhMuc> findByTendanhmuc(String tendanhmuc);
}