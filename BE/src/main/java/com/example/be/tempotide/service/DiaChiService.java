package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.diachidto;

import java.util.List;

public interface DiaChiService {
    diachidto createDiaChi(diachidto diaChiDto);
    diachidto getDiaChiById(Integer id);
    List<diachidto> getAllDiaChi();
    diachidto updateDiaChi(Integer id, diachidto diaChiDto);
    void deleteDiaChi(Integer id);
}