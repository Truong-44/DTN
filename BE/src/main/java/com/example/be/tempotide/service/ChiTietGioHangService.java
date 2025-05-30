package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.ChiTietGioHangDTO;

import java.util.List;

public interface ChiTietGioHangService {
    List<ChiTietGioHangDTO> getAllChiTietGioHangs();
    ChiTietGioHangDTO getChiTietGioHangById(Integer id);
    ChiTietGioHangDTO createChiTietGioHang(ChiTietGioHangDTO chiTietGioHangDTO);
    ChiTietGioHangDTO updateChiTietGioHang(Integer id, ChiTietGioHangDTO chiTietGioHangDTO);
    void deleteChiTietGioHang(Integer id);
}