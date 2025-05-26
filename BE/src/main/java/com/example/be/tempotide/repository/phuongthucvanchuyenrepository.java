package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.phuongthucvanchuyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface phuongthucvanchuyenrepository extends JpaRepository<phuongthucvanchuyen, Integer> {
}