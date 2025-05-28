package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.SanPhamDTO;

import java.util.List;

public interface SanPhamService {
    List<SanPhamDTO> getAllSanPhams();
    SanPhamDTO getSanPhamById(Integer id);
    SanPhamDTO createSanPham(SanPhamDTO sanPhamDTO);
    SanPhamDTO updateSanPham(Integer id, SanPhamDTO sanPhamDTO);
    void deleteSanPham(Integer id);
}