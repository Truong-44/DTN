package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.chitietsanpham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface chitietsanphamrepository extends JpaRepository<chitietsanpham, Integer> {
}
