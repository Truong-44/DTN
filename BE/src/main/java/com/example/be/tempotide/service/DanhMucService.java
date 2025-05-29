package com.example.tempotide.service;

import com.example.tempotide.dto.DanhMucDTO;

import java.util.List;

public interface DanhMucService {
    List<DanhMucDTO> getAllDanhMucs();
    DanhMucDTO getDanhMucById(Integer id);
    DanhMucDTO createDanhMuc(DanhMucDTO danhMucDTO);
    DanhMucDTO updateDanhMuc(Integer id, DanhMucDTO danhMucDTO);
    void deleteDanhMuc(Integer id);
}