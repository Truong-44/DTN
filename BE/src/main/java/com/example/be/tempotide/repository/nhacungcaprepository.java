package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.nhacungcap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface nhacungcaprepository extends JpaRepository<nhacungcap, Integer> {
}
