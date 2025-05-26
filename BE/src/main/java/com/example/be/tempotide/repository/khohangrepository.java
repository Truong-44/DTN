package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.khohang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface khohangrepository extends JpaRepository<khohang, Integer> {
}