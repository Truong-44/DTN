package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.chitietdonhang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface chitietdonhangrepository extends JpaRepository<chitietdonhang, Integer> {
}