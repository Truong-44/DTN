package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.danhsachyeuthich;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface danhsachyeuthichrepository extends JpaRepository<danhsachyeuthich, Integer> {
}