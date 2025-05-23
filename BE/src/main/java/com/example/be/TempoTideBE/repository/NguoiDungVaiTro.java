package com.example.be.TempoTideBE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NguoiDungVaiTro extends JpaRepository<NguoiDungVaiTro, Integer> {
}
