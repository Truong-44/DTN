package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.thuoctinhsanpham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface thuoctinhsanphamrepository extends JpaRepository<thuoctinhsanpham, Integer> {
}