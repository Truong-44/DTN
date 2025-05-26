package com.example.be.tempotide.service;


import com.example.be.tempotide.dto.khuyenmaidto;

import java.util.List;

public interface KhuyenMaiService {
    khuyenmaidto createKhuyenMai(khuyenmaidto khuyenMaiDto);
    khuyenmaidto getKhuyenMaiById(Integer id);
    List<khuyenmaidto> getAllKhuyenMai();
    khuyenmaidto updateKhuyenMai(Integer id, khuyenmaidto khuyenMaiDto);
    void deleteKhuyenMai(Integer id);
}
