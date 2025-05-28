package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.Quyen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuyenRepository extends JpaRepository<Quyen, Integer> {
    Optional<Quyen> findByTenquyen(String tenquyen);
}