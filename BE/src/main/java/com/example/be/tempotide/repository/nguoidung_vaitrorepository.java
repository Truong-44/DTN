package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.nguoidung_vaitro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface nguoidung_vaitrorepository extends JpaRepository<nguoidung_vaitro, nguoidung_vaitro.NguoiDung_VaiTroId> {
}