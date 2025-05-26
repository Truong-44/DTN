package com.example.be.TempoTide.service;

public interface NhaCungCapService {
    NhaCungCapDto createNhaCungCap(NhaCungCapDto nhaCungCapDto);
    NhaCungCapDto getNhaCungCapById(Integer id);
    List<NhaCungCapDto> getAllNhaCungCap();
    NhaCungCapDto updateNhaCungCap(Integer id, NhaCungCapDto nhaCungCapDto);
    void deleteNhaCungCap(Integer id);
}
