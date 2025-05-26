package com.example.be.TempoTide.service;


import com.example.be.TempoTide.dto.ChiTietDonHangDto;

import java.util.List;

public interface ChiTietDonHangService {
    ChiTietDonHangDto createChiTietDonHang(ChiTietDonHangDto chiTietDonHangDto);
    ChiTietDonHangDto getChiTietDonHangById(Integer id);
    List<ChiTietDonHangDto> getAllChiTietDonHang();
    ChiTietDonHangDto updateChiTietDonHang(Integer id, ChiTietDonHangDto chiTietDonHangDto);
    void deleteChiTietDonHang(Integer id);
}