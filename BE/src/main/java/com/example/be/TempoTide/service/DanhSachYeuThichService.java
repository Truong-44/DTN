package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.DanhSachYeuThichDto;

import java.util.List;

public interface DanhSachYeuThichService {
    DanhSachYeuThichDto createDanhSachYeuThich(DanhSachYeuThichDto danhSachYeuThichDto);
    DanhSachYeuThichDto getDanhSachYeuThichById(Integer id);
    List<DanhSachYeuThichDto> getAllDanhSachYeuThich();
    DanhSachYeuThichDto updateDanhSachYeuThich(Integer id, DanhSachYeuThichDto danhSachYeuThichDto);
    void deleteDanhSachYeuThich(Integer id);
}