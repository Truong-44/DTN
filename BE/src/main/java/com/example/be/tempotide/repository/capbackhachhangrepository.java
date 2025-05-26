package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.capbackhachhang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface capbackhachhangrepository extends JpaRepository<capbackhachhang, Integer> {
}