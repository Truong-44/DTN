package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.quyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface quyenrepository extends JpaRepository<quyen, Integer> {
    boolean existsByTenQuyen(String tenQuyen);
}