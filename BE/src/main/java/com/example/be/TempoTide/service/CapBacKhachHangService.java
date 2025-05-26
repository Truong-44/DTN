package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.CapBacKhachHangDto;

import java.util.List;

public interface CapBacKhachHangService {
    CapBacKhachHangDto createCapBacKhachHang(CapBacKhachHangDto capBacKhachHangDto);
    CapBacKhachHangDto getCapBacKhachHangById(Integer id);
    List<CapBacKhachHangDto> getAllCapBacKhachHang();
    CapBacKhachHangDto updateCapBacKhachHang(Integer id, CapBacKhachHangDto capBacKhachHangDto);
    void deleteCapBacKhachHang(Integer id);
}