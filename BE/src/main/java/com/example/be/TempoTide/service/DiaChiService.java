package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.DiaChiDto;

import java.util.List;

public interface DiaChiService {
    DiaChiDto createDiaChi(DiaChiDto diaChiDto);
    DiaChiDto getDiaChiById(Integer id);
    List<DiaChiDto> getAllDiaChi();
    DiaChiDto updateDiaChi(Integer id, DiaChiDto diaChiDto);
    void deleteDiaChi(Integer id);
}