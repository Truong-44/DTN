package com.example.be.tempotide.service;


import com.example.be.tempotide.dto.chitietdonhangdto;

import java.util.List;

public interface chitietdonhangservice {
    chitietdonhangdto createChiTietDonHang(chitietdonhangdto chiTietDonHangDto);
    chitietdonhangdto getChiTietDonHangById(Integer id);
    List<chitietdonhangdto> getAllChiTietDonHang();
    chitietdonhangdto updateChiTietDonHang(Integer id, chitietdonhangdto chiTietDonHangDto);
    void deleteChiTietDonHang(Integer id);
}