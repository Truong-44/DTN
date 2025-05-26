package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.DanhMucDto;

import java.util.List;

public interface DanhMucService {
    DanhMucDto createDanhMuc(DanhMucDto danhMucDto);
    DanhMucDto getDanhMucById(Integer id);
    List<DanhMucDto> getAllDanhMuc();
    DanhMucDto updateDanhMuc(Integer id, DanhMucDto danhMucDto);
    void deleteDanhMuc(Integer id);
}
