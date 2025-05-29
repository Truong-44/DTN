package com.example.tempotide.service;

import com.example.tempotide.dto.ChiTietSanPhamDTO;

import java.util.List;

public interface ChiTietSanPhamService {
    List<ChiTietSanPhamDTO> getAllChiTietSanPhams();
    ChiTietSanPhamDTO getChiTietSanPhamById(Integer id);
    ChiTietSanPhamDTO createChiTietSanPham(ChiTietSanPhamDTO chiTietSanPhamDTO);
    ChiTietSanPhamDTO updateChiTietSanPham(Integer id, ChiTietSanPhamDTO chiTietSanPhamDTO);
    void deleteChiTietSanPham(Integer id);
}