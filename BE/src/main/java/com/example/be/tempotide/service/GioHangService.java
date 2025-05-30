package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.GioHangDTO;

import java.util.List;

public interface GioHangService {
    List<GioHangDTO> getAllGioHangs();
    GioHangDTO getGioHangById(Integer id);
    GioHangDTO createGioHang(GioHangDTO gioHangDTO);
    GioHangDTO updateGioHang(Integer id, GioHangDTO gioHangDTO);
    void deleteGioHang(Integer id);
}