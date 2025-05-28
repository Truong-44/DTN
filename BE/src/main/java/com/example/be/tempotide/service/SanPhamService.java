package com.example.tempotide.service;

import com.example.tempotide.dto.SanPhamDTO;

import java.util.List;

public interface SanPhamService {
    List<SanPhamDTO> getAllActiveProducts();
    SanPhamDTO getProductById(Integer id);
    SanPhamDTO createProduct(SanPhamDTO sanPhamDTO);
    SanPhamDTO updateProduct(Integer id, SanPhamDTO sanPhamDTO);
    void deleteProduct(Integer id);
}