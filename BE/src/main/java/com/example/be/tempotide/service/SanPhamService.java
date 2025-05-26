package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.sanphamdto;

import java.util.List;

public interface SanPhamService {
    sanphamdto createSanPham(sanphamdto sanPhamDto);
    sanphamdto getSanPhamById(Integer id);
    List<sanphamdto> getAllSanPham();
    sanphamdto updateSanPham(Integer id, sanphamdto sanPhamDto);
    void deleteSanPham(Integer id);
}