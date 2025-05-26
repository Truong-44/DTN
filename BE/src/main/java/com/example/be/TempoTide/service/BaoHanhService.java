package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.BaoHanhDto;

import java.util.List;

public interface BaoHanhService {
    BaoHanhDto createBaoHanh(BaoHanhDto baoHanhDto);
    BaoHanhDto getBaoHanhById(Integer id);
    List<BaoHanhDto> getAllBaoHanh();
    BaoHanhDto updateBaoHanh(Integer id, BaoHanhDto baoHanhDto);
    void deleteBaoHanh(Integer id);
}