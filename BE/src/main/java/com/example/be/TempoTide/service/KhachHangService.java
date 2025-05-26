package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.KhachHangDto;

import java.util.List;

public interface KhachHangService {
    KhachHangDto createKhachHang(KhachHangDto khachHangDto);
    KhachHangDto getKhachHangById(Integer id);
    List<KhachHangDto> getAllKhachHang();
    KhachHangDto updateKhachHang(Integer id, KhachHangDto khachHangDto);
    void deleteKhachHang(Integer id);
}
