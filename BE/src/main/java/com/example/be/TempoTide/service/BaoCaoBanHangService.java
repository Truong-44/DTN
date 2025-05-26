package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.BaoCaoBanHangDto;

import java.util.List;

public interface BaoCaoBanHangService {
    BaoCaoBanHangDto createBaoCaoBanHang(BaoCaoBanHangDto baoCaoBanHangDto);
    BaoCaoBanHangDto getBaoCaoBanHangById(Integer id);
    List<BaoCaoBanHangDto> getAllBaoCaoBanHang();
    BaoCaoBanHangDto updateBaoCaoBanHang(Integer id, BaoCaoBanHangDto baoCaoBanHangDto);
    void deleteBaoCaoBanHang(Integer id);
}