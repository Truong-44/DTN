package com.example.be.TempoTideBE.service;


import com.example.be.TempoTideBE.dto.SanPhamDTO;

import java.util.List;

public interface SanPhamService {
    SanPhamDTO createSanPham(SanPhamDTO sanPhamDTO);
    SanPhamDTO updateSanPham(Integer maSanPham, SanPhamDTO sanPhamDTO);
    void deleteSanPham(Integer maSanPham);
    SanPhamDTO getSanPhamById(Integer maSanPham);
    List<SanPhamDTO> getAllSanPham();
    boolean existsByMaDanhMuc(Integer maDanhMuc);
    boolean existsByMaNhaCungCap(Integer maNhaCungCap);
}
