package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.ChatboxLichSu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatboxLichSuRepository extends JpaRepository<ChatboxLichSu, Integer> {
    List<ChatboxLichSu> findByMakhachhang_Makhachhang(Integer makhachhang);
    List<ChatboxLichSu> findBySodienthoai(String sodienthoai);
}