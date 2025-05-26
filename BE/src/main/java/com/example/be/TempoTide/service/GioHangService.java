package com.example.be.TempoTide.service;


import com.example.be.TempoTide.dto.GioHangDto;

import java.util.List;

public interface GioHangService {
    GioHangDto createGioHang(GioHangDto gioHangDto);
    GioHangDto getGioHangById(Integer id);
    List<GioHangDto> getAllGioHang();
    GioHangDto updateGioHang(Integer id, GioHangDto gioHangDto);
    void deleteGioHang(Integer id);
}