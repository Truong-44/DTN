package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.SanPhamDto;

import java.util.List;

public interface SanPhamService {
    SanPhamDto createSanPham(SanPhamDto sanPhamDto);
    SanPhamDto getSanPhamById(Integer id);
    List<SanPhamDto> getAllSanPham();
    SanPhamDto updateSanPham(Integer id, SanPhamDto sanPhamDto);
    void deleteSanPham(Integer id);
}