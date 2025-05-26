package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.vaitro_quyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface vaitro_quyenrepository extends JpaRepository<vaitro_quyen, vaitro_quyen.VaiTro_QuyenId> {
}