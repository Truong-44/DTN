package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.danhmucdto;

import java.util.List;

public interface DanhMucService {
    danhmucdto createDanhMuc(danhmucdto danhMucDto);
    danhmucdto getDanhMucById(Integer id);
    List<danhmucdto> getAllDanhMuc();
    danhmucdto updateDanhMuc(Integer id, danhmucdto danhMucDto);
    void deleteDanhMuc(Integer id);
}
