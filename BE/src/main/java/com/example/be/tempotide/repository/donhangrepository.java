package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.donhang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface donhangrepository extends JpaRepository<donhang, Integer> {
}
