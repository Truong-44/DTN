package com.example.be.TempoTide.service;

public interface ThuongHieuService {
    ThuongHieuDto createThuongHieu(ThuongHieuDto thuongHieuDto);
    ThuongHieuDto getThuongHieuById(Integer id);
    List<ThuongHieuDto> getAllThuongHieu();
    ThuongHieuDto updateThuongHieu(Integer id, ThuongHieuDto thuongHieuDto);
    void deleteThuongHieu(Integer id);
}
