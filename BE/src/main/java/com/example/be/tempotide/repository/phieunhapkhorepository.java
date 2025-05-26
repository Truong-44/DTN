package com.example.be.tempotide.repository;


import com.example.be.tempotide.entity.phieunhapkho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface phieunhapkhorepository extends JpaRepository<phieunhapkho, Integer> {
}