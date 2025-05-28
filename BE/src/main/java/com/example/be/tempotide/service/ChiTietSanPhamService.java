package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.ChiTietSanPhamDTO;

import java.util.List;

public interface ChiTietSanPhamService {
    List<ChiTietSanPhamDTO> getAllChiTietSanPhams();
    ChiTietSanPhamDTO getChiTietSanPhamById(Integer id);
    ChiTietSanPhamDTO createChiTietSanPham(ChiTietSanPhamDTO chiTietSanPhamDTO);
    ChiTietSanPhamDTO updateChiTietSanPham(Integer id, ChiTietSanPhamDTO chiTietSanPhamDTO);
    void deleteChiTietSanPham(Integer id);
    List<ChiTietSanPhamDTO> getChiTietSanPhamBySanPhamId(Integer masanpham);
}