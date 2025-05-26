package com.example.be.tempotide.repository;


import com.example.be.tempotide.entity.giohang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface giohangrepository extends JpaRepository<giohang, Integer> {
}