package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.phieunhapkhodto;

import java.util.List;

public interface phieunhapkhoservice {
    phieunhapkhodto createPhieuNhapKho(phieunhapkhodto phieuNhapKhoDto);
    phieunhapkhodto getPhieuNhapKhoById(Integer id);
    List<phieunhapkhodto> getAllPhieuNhapKho();
    phieunhapkhodto updatePhieuNhapKho(Integer id, phieunhapkhodto phieuNhapKhoDto);
    void deletePhieuNhapKho(Integer id);
}