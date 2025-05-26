package com.example.be.TempoTide.service;


import com.example.be.TempoTide.dto.DanhGiaSanPhamDto;

import java.util.List;

public interface DanhGiaSanPhamService {
    DanhGiaSanPhamDto createDanhGiaSanPham(DanhGiaSanPhamDto danhGiaSanPhamDto);
    DanhGiaSanPhamDto getDanhGiaSanPhamById(Integer id);
    List<DanhGiaSanPhamDto> getAllDanhGiaSanPham();
    DanhGiaSanPhamDto updateDanhGiaSanPham(Integer id, DanhGiaSanPhamDto danhGiaSanPhamDto);
    void deleteDanhGiaSanPham(Integer id);
}