package com.example.be.TempoTideBE.repository;

import com.example.be.TempoTideBE.entity.ThuocTinhSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThuocTinhSanPhamRepository extends JpaRepository<ThuocTinhSanPham, Integer> {
}
