package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.hinhanhsanpham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface hinhanhsanphamrepository extends JpaRepository<hinhanhsanpham, Integer> {
}