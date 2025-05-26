package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.nguoidung_vaitrodto;

import java.util.List;

public interface NguoiDung_VaiTroService {
    nguoidung_vaitrodto createNguoiDung_VaiTro(nguoidung_vaitrodto nguoiDung_VaiTroDto);
    nguoidung_vaitrodto getNguoiDung_VaiTroById(Integer maNguoiDung, Integer maVaiTro);
    List<nguoidung_vaitrodto> getAllNguoiDung_VaiTro();
    nguoidung_vaitrodto updateNguoiDung_VaiTro(Integer maNguoiDung, Integer maVaiTro, nguoidung_vaitrodto nguoiDung_VaiTroDto);
    void deleteNguoiDung_VaiTro(Integer maNguoiDung, Integer maVaiTro);
}