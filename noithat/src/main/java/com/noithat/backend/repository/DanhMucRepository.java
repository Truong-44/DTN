package com.noithat.backend.repository;

import com.noithat.backend.entity.DanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DanhMucRepository extends JpaRepository<DanhMuc, Integer> {
    List<DanhMuc> findByDanhmucChaIsNull();
    List<DanhMuc> findByTendanhmucContainingIgnoreCase(String tendanhmuc);
}
