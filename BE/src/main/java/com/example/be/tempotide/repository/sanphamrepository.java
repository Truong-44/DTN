package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.sanpham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface sanphamrepository extends JpaRepository<sanpham, Integer> {
}
