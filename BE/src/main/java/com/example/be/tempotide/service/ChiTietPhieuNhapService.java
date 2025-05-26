package com.example.be.tempotide.service;


import com.example.be.tempotide.dto.chitietphieunhapdto;

import java.util.List;

public interface chitietphieunhapservice {
    chitietphieunhapdto createChiTietPhieuNhap(chitietphieunhapdto chiTietPhieuNhapDto);
    chitietphieunhapdto getChiTietPhieuNhapById(Integer id);
    List<chitietphieunhapdto> getAllChiTietPhieuNhap();
    chitietphieunhapdto updateChiTietPhieuNhap(Integer id, chitietphieunhapdto chiTietPhieuNhapDto);
    void deleteChiTietPhieuNhap(Integer id);
}