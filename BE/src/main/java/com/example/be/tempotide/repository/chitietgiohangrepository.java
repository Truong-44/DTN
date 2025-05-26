package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.chitietgiohang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface chitietgiohangrepository extends JpaRepository<chitietgiohang, Integer> {
}