package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.thuonghieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface thuonghieurepository extends JpaRepository<thuonghieu, Integer> {
}
