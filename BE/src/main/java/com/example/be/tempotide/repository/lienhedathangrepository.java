package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.lienhedathang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface lienhedathangrepository extends JpaRepository<lienhedathang, Integer> {
}
