package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.cauhinhhethong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface cauhinhhethongrepository extends JpaRepository<cauhinhhethong, Integer> {
}