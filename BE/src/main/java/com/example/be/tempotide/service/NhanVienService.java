package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.nhanviendto;

import java.util.List;

public interface NhanVienService {
    nhanviendto createNhanVien(nhanviendto nhanVienDto);
    nhanviendto getNhanVienById(Integer id);
    List<nhanviendto> getAllNhanVien();
    nhanviendto updateNhanVien(Integer id, nhanviendto nhanVienDto);
    void deleteNhanVien(Integer id);
}