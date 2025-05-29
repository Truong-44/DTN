package com.example.be.tempotide.service;


import com.example.be.tempotide.dto.NhanVienDTO;

import java.util.List;

public interface NhanVienService {
    List<NhanVienDTO> getAllNhanViens();
    NhanVienDTO getNhanVienById(Integer id);
    NhanVienDTO createNhanVien(NhanVienDTO nhanVienDTO);
    NhanVienDTO updateNhanVien(Integer id, NhanVienDTO nhanVienDTO);
    void deleteNhanVien(Integer id);
}