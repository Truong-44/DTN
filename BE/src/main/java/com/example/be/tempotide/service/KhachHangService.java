package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.KhachHangDTO;

import java.util.List;

public interface KhachHangService {
    List<KhachHangDTO> getAllKhachHangs();
    KhachHangDTO getKhachHangById(Integer id);
    KhachHangDTO createKhachHang(KhachHangDTO khachHangDTO);
    KhachHangDTO updateKhachHang(Integer id, KhachHangDTO khachHangDTO);
    void deleteKhachHang(Integer id);
}