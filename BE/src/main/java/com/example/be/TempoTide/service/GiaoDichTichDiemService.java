package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.GiaoDichTichDiemDto;

import java.util.List;

public interface GiaoDichTichDiemService {
    GiaoDichTichDiemDto createGiaoDichTichDiem(GiaoDichTichDiemDto giaoDichTichDiemDto);
    GiaoDichTichDiemDto getGiaoDichTichDiemById(Integer id);
    List<GiaoDichTichDiemDto> getAllGiaoDichTichDiem();
    GiaoDichTichDiemDto updateGiaoDichTichDiem(Integer id, GiaoDichTichDiemDto giaoDichTichDiemDto);
    void deleteGiaoDichTichDiem(Integer id);
}
