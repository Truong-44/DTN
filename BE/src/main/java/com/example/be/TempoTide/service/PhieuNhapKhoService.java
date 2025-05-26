package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.PhieuNhapKhoDto;

import java.util.List;

public interface PhieuNhapKhoService {
    PhieuNhapKhoDto createPhieuNhapKho(PhieuNhapKhoDto phieuNhapKhoDto);
    PhieuNhapKhoDto getPhieuNhapKhoById(Integer id);
    List<PhieuNhapKhoDto> getAllPhieuNhapKho();
    PhieuNhapKhoDto updatePhieuNhapKho(Integer id, PhieuNhapKhoDto phieuNhapKhoDto);
    void deletePhieuNhapKho(Integer id);
}