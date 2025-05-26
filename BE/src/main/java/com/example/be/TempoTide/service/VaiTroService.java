package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.VaiTroDto;

import java.util.List;

public interface VaiTroService {
    VaiTroDto createVaiTro(VaiTroDto vaiTroDto);
    VaiTroDto getVaiTroById(Integer id);
    List<VaiTroDto> getAllVaiTro();
    VaiTroDto updateVaiTro(Integer id, VaiTroDto vaiTroDto);
    void deleteVaiTro(Integer id);
}