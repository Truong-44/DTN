package com.example.be.tempotide.service;


import com.example.be.tempotide.dto.chitietgiohangdto;

import java.util.List;

public interface ChiTietGioHangService {
    chitietgiohangdto createChiTietGioHang(chitietgiohangdto chiTietGioHangDto);
    chitietgiohangdto getChiTietGioHangById(Integer id);
    List<chitietgiohangdto> getAllChiTietGioHang();
    chitietgiohangdto updateChiTietGioHang(Integer id, chitietgiohangdto chiTietGioHangDto);
    void deleteChiTietGioHang(Integer id);
}