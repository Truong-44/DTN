package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.danhsachyeuthichdto;

import java.util.List;

public interface danhsachyeuthichservice {
    danhsachyeuthichdto createDanhSachYeuThich(danhsachyeuthichdto danhSachYeuThichDto);
    danhsachyeuthichdto getDanhSachYeuThichById(Integer id);
    List<danhsachyeuthichdto> getAllDanhSachYeuThich();
    danhsachyeuthichdto updateDanhSachYeuThich(Integer id, danhsachyeuthichdto danhSachYeuThichDto);
    void deleteDanhSachYeuThich(Integer id);
}