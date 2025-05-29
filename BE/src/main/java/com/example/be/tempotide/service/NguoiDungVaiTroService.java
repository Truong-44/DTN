package com.example.tempotide.service;

import com.example.tempotide.dto.NguoiDungVaiTroDTO;

import java.util.List;

public interface NguoiDungVaiTroService {
    List<NguoiDungVaiTroDTO> getAllNguoiDungVaiTros();
    NguoiDungVaiTroDTO getNguoiDungVaiTroById(Integer id);
    NguoiDungVaiTroDTO createNguoiDungVaiTro(NguoiDungVaiTroDTO nguoiDungVaiTroDTO);
    NguoiDungVaiTroDTO updateNguoiDungVaiTro(Integer id, NguoiDungVaiTroDTO nguoiDungVaiTroDTO);
    void deleteNguoiDungVaiTro(Integer id);
}