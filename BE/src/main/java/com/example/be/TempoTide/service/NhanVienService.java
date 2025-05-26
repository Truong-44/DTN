package com.example.be.TempoTide.service;

public interface NhanVienService {
    NhanVienDto createNhanVien(NhanVienDto nhanVienDto);
    NhanVienDto getNhanVienById(Integer id);
    List<NhanVienDto> getAllNhanVien();
    NhanVienDto updateNhanVien(Integer id, NhanVienDto nhanVienDto);
    void deleteNhanVien(Integer id);
}
