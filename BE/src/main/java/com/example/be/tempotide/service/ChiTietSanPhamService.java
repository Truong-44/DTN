package com.example.temp.service;

import com.example.temp.dto.ChiTietSanPhamDTO;

import java.util.List;

public interface ChiTietSanPhamService {
    List<ChiTietSanPhamDTO> getAllActiveProductDetails();
    ChiTietSanPhamDTO getProductDetailById(long id);
    ChiTietSanPhamDTO createProductDetail(ChiTietSanPhamDTO chiTietSanPhamDTO);
    ChiTietSanPhamDTO updateProductDetail(long id, ChiTietSanPhamDTO chiTietSanPhamDTO);
    void deleteProductDetail(long id);
}