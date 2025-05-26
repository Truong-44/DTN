package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.PhuongThucVanChuyenDto;

import java.util.List;

public interface PhuongThucVanChuyenService {
    PhuongThucVanChuyenDto createPhuongThucVanChuyen(PhuongThucVanChuyenDto phuongThucVanChuyenDto);
    PhuongThucVanChuyenDto getPhuongThucVanChuyenById(Integer id);
    List<PhuongThucVanChuyenDto> getAllPhuongThucVanChuyen();
    PhuongThucVanChuyenDto updatePhuongThucVanChuyen(Integer id, PhuongThucVanChuyenDto phuongThucVanChuyenDto);
    void deletePhuongThucVanChuyen(Integer id);
}