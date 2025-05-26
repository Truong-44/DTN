package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.giaodichtichdiem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface giaodichtichdiemrepository extends JpaRepository<giaodichtichdiem, Integer> {
}
