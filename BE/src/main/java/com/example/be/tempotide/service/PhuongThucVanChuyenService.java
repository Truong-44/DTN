package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.phuongthucvanchuyendto;

import java.util.List;

public interface PhuongThucVanChuyenService {
    phuongthucvanchuyendto createPhuongThucVanChuyen(phuongthucvanchuyendto phuongThucVanChuyenDto);
    phuongthucvanchuyendto getPhuongThucVanChuyenById(Integer id);
    List<phuongthucvanchuyendto> getAllPhuongThucVanChuyen();
    phuongthucvanchuyendto updatePhuongThucVanChuyen(Integer id, phuongthucvanchuyendto phuongThucVanChuyenDto);
    void deletePhuongThucVanChuyen(Integer id);
}