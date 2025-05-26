package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.khachhangdto;

import java.util.List;

public interface khachhangservice {
    khachhangdto createKhachHang(khachhangdto khachHangDto);
    khachhangdto getKhachHangById(Integer id);
    List<khachhangdto> getAllKhachHang();
    khachhangdto updateKhachHang(Integer id, khachhangdto khachHangDto);
    void deleteKhachHang(Integer id);
}