package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.NhanVienDto;

import java.util.List;

public interface NhanVienService {
    NhanVienDto createNhanVien(NhanVienDto nhanVienDto);
    NhanVienDto getNhanVienById(Integer id);
    List<NhanVienDto> getAllNhanVien();
    NhanVienDto updateNhanVien(Integer id, NhanVienDto nhanVienDto);
    void deleteNhanVien(Integer id);
}
