package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.phieuxuatkho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface phieunhaphangrepository extends JpaRepository<phieuxuatkho, Integer> {
}
