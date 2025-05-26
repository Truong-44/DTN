package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.PhieuNhapHangDto;

import java.util.List;

public interface PhieuNhapHangService {
    PhieuNhapHangDto createPhieuNhapHang(PhieuNhapHangDto phieuNhapHangDto);
    PhieuNhapHangDto getPhieuNhapHangById(Integer id);
    List<PhieuNhapHangDto> getAllPhieuNhapHang();
    PhieuNhapHangDto updatePhieuNhapHang(Integer id, PhieuNhapHangDto phieuNhapHangDto);
    void deletePhieuNhapHang(Integer id);
}
