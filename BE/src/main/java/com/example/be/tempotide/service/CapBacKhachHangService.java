package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.capbackhachhangdto;

import java.util.List;

public interface capbackhachhangservice {
    capbackhachhangdto createCapBacKhachHang(capbackhachhangdto capBacKhachHangDto);
    capbackhachhangdto getCapBacKhachHangById(Integer id);
    List<capbackhachhangdto> getAllCapBacKhachHang();
    capbackhachhangdto updateCapBacKhachHang(Integer id, capbackhachhangdto capBacKhachHangDto);
    void deleteCapBacKhachHang(Integer id);
}