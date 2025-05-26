package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.hinhanhsanphamdto;

import java.util.List;

public interface HinhAnhSanPhamService {
    hinhanhsanphamdto createHinhAnhSanPham(hinhanhsanphamdto hinhAnhSanPhamDto);
    hinhanhsanphamdto getHinhAnhSanPhamById(Integer id);
    List<hinhanhsanphamdto> getAllHinhAnhSanPham();
    hinhanhsanphamdto updateHinhAnhSanPham(Integer id, hinhanhsanphamdto hinhAnhSanPhamDto);
    void deleteHinhAnhSanPham(Integer id);
}