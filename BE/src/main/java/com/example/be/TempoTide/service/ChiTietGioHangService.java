package com.example.be.TempoTide.service;


import com.example.be.TempoTide.dto.ChiTietGioHangDto;

import java.util.List;

public interface ChiTietGioHangService {
    ChiTietGioHangDto createChiTietGioHang(ChiTietGioHangDto chiTietGioHangDto);
    ChiTietGioHangDto getChiTietGioHangById(Integer id);
    List<ChiTietGioHangDto> getAllChiTietGioHang();
    ChiTietGioHangDto updateChiTietGioHang(Integer id, ChiTietGioHangDto chiTietGioHangDto);
    void deleteChiTietGioHang(Integer id);
}