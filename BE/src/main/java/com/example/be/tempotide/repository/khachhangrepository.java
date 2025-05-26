package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.khachhang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface khachhangrepository extends JpaRepository<khachhang, Integer> {
    Optional<khachhang> findByEmail(String email);
}