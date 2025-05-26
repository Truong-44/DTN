package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.vaitro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface vaitrorepository extends JpaRepository<vaitro, Integer> {
    boolean existsByTenVaiTro(String tenVaiTro);
}