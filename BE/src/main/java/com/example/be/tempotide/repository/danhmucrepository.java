package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.danhmuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface danhmucrepository extends JpaRepository<danhmuc, Integer> {
}
