package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.baocaobanhang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface baocaobanhangrepository extends JpaRepository<baocaobanhang, Integer> {
}