package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.NhaCungCapDto;

import java.util.List;

public interface NhaCungCapService {
    NhaCungCapDto createNhaCungCap(NhaCungCapDto nhaCungCapDto);
    NhaCungCapDto getNhaCungCapById(Integer id);
    List<NhaCungCapDto> getAllNhaCungCap();
    NhaCungCapDto updateNhaCungCap(Integer id, NhaCungCapDto nhaCungCapDto);
    void deleteNhaCungCap(Integer id);
}
