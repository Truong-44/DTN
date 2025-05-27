package com.example.be.tempotide.service;


import com.example.be.tempotide.dto.phiendangnhapdto;

import java.util.List;

public interface phiendangnhapservice {
    phiendangnhapdto createPhienDangNhap(phiendangnhapdto phienDangNhapDto);
    phiendangnhapdto getPhienDangNhapById(Integer id);
    List<phiendangnhapdto> getAllPhienDangNhap();
    phiendangnhapdto updatePhienDangNhap(Integer id, phiendangnhapdto phienDangNhapDto);
    void deletePhienDangNhap(Integer id);
}