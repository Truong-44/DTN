package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.diachi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface diachirepository extends JpaRepository<diachi, Integer> {
}