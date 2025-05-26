package com.example.be.TempoTide.service;


import com.example.be.TempoTide.dto.KhuyenMaiDto;

import java.util.List;

public interface KhuyenMaiService {
    KhuyenMaiDto createKhuyenMai(KhuyenMaiDto khuyenMaiDto);
    KhuyenMaiDto getKhuyenMaiById(Integer id);
    List<KhuyenMaiDto> getAllKhuyenMai();
    KhuyenMaiDto updateKhuyenMai(Integer id, KhuyenMaiDto khuyenMaiDto);
    void deleteKhuyenMai(Integer id);
}
