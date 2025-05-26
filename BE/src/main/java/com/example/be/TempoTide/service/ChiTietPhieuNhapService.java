package com.example.be.TempoTide.service;


import com.example.be.TempoTide.dto.ChiTietPhieuNhapDto;

import java.util.List;

public interface ChiTietPhieuNhapService {
    ChiTietPhieuNhapDto createChiTietPhieuNhap(ChiTietPhieuNhapDto chiTietPhieuNhapDto);
    ChiTietPhieuNhapDto getChiTietPhieuNhapById(Integer id);
    List<ChiTietPhieuNhapDto> getAllChiTietPhieuNhap();
    ChiTietPhieuNhapDto updateChiTietPhieuNhap(Integer id, ChiTietPhieuNhapDto chiTietPhieuNhapDto);
    void deleteChiTietPhieuNhap(Integer id);
}