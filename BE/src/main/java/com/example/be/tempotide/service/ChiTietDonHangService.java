package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.ChiTietDonHangDTO;

import java.util.List;

public interface ChiTietDonHangService {
    List<ChiTietDonHangDTO> getAllChiTietDonHangs();
    ChiTietDonHangDTO getChiTietDonHangById(Integer id);
    ChiTietDonHangDTO createChiTietDonHang(ChiTietDonHangDTO chiTietDonHangDTO);
    ChiTietDonHangDTO updateChiTietDonHang(Integer id, ChiTietDonHangDTO chiTietDonHangDTO);
    void deleteChiTietDonHang(Integer id);
    List<ChiTietDonHangDTO> getChiTietDonHangByDonHangId(Integer madonhang);
}