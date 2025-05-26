package com.example.be.TempoTide.service;


import com.example.be.TempoTide.dto.ThanhToanDto;

import java.util.List;

public interface ThanhToanService {
    ThanhToanDto createThanhToan(ThanhToanDto thanhToanDto);
    ThanhToanDto getThanhToanById(Integer id);
    List<ThanhToanDto> getAllThanhToan();
    ThanhToanDto updateThanhToan(Integer id, ThanhToanDto thanhToanDto);
    void deleteThanhToan(Integer id);
}