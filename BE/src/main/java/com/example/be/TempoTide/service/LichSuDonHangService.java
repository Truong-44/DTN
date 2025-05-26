package com.example.be.TempoTide.service;


import com.example.be.TempoTide.dto.LichSuDonHangDto;

import java.util.List;

public interface LichSuDonHangService {
    LichSuDonHangDto createLichSuDonHang(LichSuDonHangDto lichSuDonHangDto);
    LichSuDonHangDto getLichSuDonHangById(Integer id);
    List<LichSuDonHangDto> getAllLichSuDonHang();
    LichSuDonHangDto updateLichSuDonHang(Integer id, LichSuDonHangDto lichSuDonHangDto);
    void deleteLichSuDonHang(Integer id);
}