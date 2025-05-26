package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.KhoHangDto;

import java.util.List;

public interface KhoHangService {
    KhoHangDto createKhoHang(KhoHangDto khoHangDto);
    KhoHangDto getKhoHangById(Integer id);
    List<KhoHangDto> getAllKhoHang();
    KhoHangDto updateKhoHang(Integer id, KhoHangDto khoHangDto);
    void deleteKhoHang(Integer id);
}