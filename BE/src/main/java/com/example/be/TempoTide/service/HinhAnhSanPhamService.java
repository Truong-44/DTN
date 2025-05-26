package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.HinhAnhSanPhamDto;

import java.util.List;

public interface HinhAnhSanPhamService {
    HinhAnhSanPhamDto createHinhAnhSanPham(HinhAnhSanPhamDto hinhAnhSanPhamDto);
    HinhAnhSanPhamDto getHinhAnhSanPhamById(Integer id);
    List<HinhAnhSanPhamDto> getAllHinhAnhSanPham();
    HinhAnhSanPhamDto updateHinhAnhSanPham(Integer id, HinhAnhSanPhamDto hinhAnhSanPhamDto);
    void deleteHinhAnhSanPham(Integer id);
}