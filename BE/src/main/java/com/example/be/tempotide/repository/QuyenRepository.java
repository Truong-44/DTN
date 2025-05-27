package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.Quyen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuyenRepository extends JpaRepository<Quyen, Integer> {
    List<Quyen> findByTrangthaiTrue();
    Optional<Quyen> findByTenquyen(String tenquyen);
}