package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.VaiTro_QuyenDto;

import java.util.List;

public interface VaiTro_QuyenService {
    VaiTro_QuyenDto createVaiTro_Quyen(VaiTro_QuyenDto vaiTro_QuyenDto);
    VaiTro_QuyenDto getVaiTro_QuyenById(Integer maVaiTro, Integer maQuyen);
    List<VaiTro_QuyenDto> getAllVaiTro_Quyen();
    VaiTro_QuyenDto updateVaiTro_Quyen(Integer maVaiTro, Integer maQuyen, VaiTro_QuyenDto vaiTro_QuyenDto);
    void deleteVaiTro_Quyen(Integer maVaiTro, Integer maQuyen);
}