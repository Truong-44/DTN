package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.phieunhaphangdto;

import java.util.List;

public interface phieunhaphangservice {
    phieunhaphangdto createPhieuNhapHang(phieunhaphangdto phieuNhapHangDto);
    phieunhaphangdto getPhieuNhapHangById(Integer id);
    List<phieunhaphangdto> getAllPhieuNhapHang();
    phieunhaphangdto updatePhieuNhapHang(Integer id, phieunhaphangdto phieuNhapHangDto);
    void deletePhieuNhapHang(Integer id);
}
