package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.ChiTietSanPhamDto;

import java.util.List;

public interface ChiTietSanPhamService {
    ChiTietSanPhamDto createChiTietSanPham(ChiTietSanPhamDto chiTietSanPhamDto);
    ChiTietSanPhamDto getChiTietSanPhamById(Integer id);
    List<ChiTietSanPhamDto> getAllChiTietSanPham();
    ChiTietSanPhamDto updateChiTietSanPham(Integer id, ChiTietSanPhamDto chiTietSanPhamDto);
    void deleteChiTietSanPham(Integer id);
}