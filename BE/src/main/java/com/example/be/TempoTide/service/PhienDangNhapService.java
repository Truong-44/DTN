package com.example.be.TempoTide.service;


import com.example.be.TempoTide.dto.PhienDangNhapDto;

import java.util.List;

public interface PhienDangNhapService {
    PhienDangNhapDto createPhienDangNhap(PhienDangNhapDto phienDangNhapDto);
    PhienDangNhapDto getPhienDangNhapById(Integer id);
    List<PhienDangNhapDto> getAllPhienDangNhap();
    PhienDangNhapDto updatePhienDangNhap(Integer id, PhienDangNhapDto phienDangNhapDto);
    void deletePhienDangNhap(Integer id);
}