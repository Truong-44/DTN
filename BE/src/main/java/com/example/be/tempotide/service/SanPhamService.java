package com.example.be.tempotide.service;

import com.example.tempotide.backend.dto.SanPhamDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SanPhamService {
    Page<SanPhamDTO> getAllSanPham(Pageable pageable);
    SanPhamDTO getSanPhamById(Integer id);
    SanPhamDTO createSanPham(SanPhamDTO dto);
    SanPhamDTO updateSanPham(Integer id, SanPhamDTO dto);
    void deleteSanPham(Integer id);
    Page<SanPhamDTO> searchSanPham(String keyword, Pageable pageable);
}