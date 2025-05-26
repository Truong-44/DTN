package com.example.be.TempoTide.service;


import com.example.be.TempoTide.dto.LienHeDatHangDto;

import java.util.List;

public interface LienHeDatHangService {
    LienHeDatHangDto createLienHeDatHang(LienHeDatHangDto lienHeDatHangDto);
    LienHeDatHangDto getLienHeDatHangById(Integer id);
    List<LienHeDatHangDto> getAllLienHeDatHang();
    LienHeDatHangDto updateLienHeDatHang(Integer id, LienHeDatHangDto lienHeDatHangDto);
    void deleteLienHeDatHang(Integer id);
}