package com.example.be.TempoTide.service;

public interface SanPhamService {
    SanPhamDto createSanPham(SanPhamDto sanPhamDto);
    SanPhamDto getSanPhamById(Integer id);
    List<SanPhamDto> getAllSanPham();
    SanPhamDto updateSanPham(Integer id, SanPhamDto sanPhamDto);
    void deleteSanPham(Integer id);
}