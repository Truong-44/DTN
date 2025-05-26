package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.phiendangnhap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface phiendangnhaprepository extends JpaRepository<phiendangnhap, Integer> {
}