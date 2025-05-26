package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.nhanvien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface nhanvienrepository extends JpaRepository<nhanvien, Integer> {
    Optional<nhanvien> findByEmail(String email);
}