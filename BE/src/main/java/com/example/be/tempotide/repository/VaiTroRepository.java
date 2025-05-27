package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VaiTroRepository extends JpaRepository<VaiTro, Integer> {
    List<VaiTro> findByTrangthaiTrue();
    Optional<VaiTro> findByTenvaitro(String tenvaitro);
}