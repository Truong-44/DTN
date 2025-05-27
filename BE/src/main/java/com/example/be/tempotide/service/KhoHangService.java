package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.khohangdto;

import java.util.List;

public interface khohangservice {
    khohangdto createKhoHang(khohangdto khoHangDto);
    khohangdto getKhoHangById(Integer id);
    List<khohangdto> getAllKhoHang();
    khohangdto updateKhoHang(Integer id, khohangdto khoHangDto);
    void deleteKhoHang(Integer id);
}