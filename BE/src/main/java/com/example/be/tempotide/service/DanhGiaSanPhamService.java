package com.example.be.tempotide.service;


import com.example.be.tempotide.dto.danhgiasanphamdto;

import java.util.List;

public interface DanhGiaSanPhamService {
    danhgiasanphamdto createDanhGiaSanPham(danhgiasanphamdto danhGiaSanPhamDto);
    danhgiasanphamdto getDanhGiaSanPhamById(Integer id);
    List<danhgiasanphamdto> getAllDanhGiaSanPham();
    danhgiasanphamdto updateDanhGiaSanPham(Integer id, danhgiasanphamdto danhGiaSanPhamDto);
    void deleteDanhGiaSanPham(Integer id);
}