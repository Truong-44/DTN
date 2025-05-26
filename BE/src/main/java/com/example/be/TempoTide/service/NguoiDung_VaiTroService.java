package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.NguoiDung_VaiTroDto;

import java.util.List;

public interface NguoiDung_VaiTroService {
    NguoiDung_VaiTroDto createNguoiDung_VaiTro(NguoiDung_VaiTroDto nguoiDung_VaiTroDto);
    NguoiDung_VaiTroDto getNguoiDung_VaiTroById(Integer maNguoiDung, Integer maVaiTro);
    List<NguoiDung_VaiTroDto> getAllNguoiDung_VaiTro();
    NguoiDung_VaiTroDto updateNguoiDung_VaiTro(Integer maNguoiDung, Integer maVaiTro, NguoiDung_VaiTroDto nguoiDung_VaiTroDto);
    void deleteNguoiDung_VaiTro(Integer maNguoiDung, Integer maVaiTro);
}