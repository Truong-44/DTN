package com.example.be.TempoTide.service;


import com.example.be.TempoTide.dto.QuyenDto;

import java.util.List;

public interface QuyenService {
    QuyenDto createQuyen(QuyenDto quyenDto);
    QuyenDto getQuyenById(Integer id);
    List<QuyenDto> getAllQuyen();
    QuyenDto updateQuyen(Integer id, QuyenDto quyenDto);
    void deleteQuyen(Integer id);
}