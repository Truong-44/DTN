package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.thuoctinhsanphamdto;

import java.util.List;

public interface ThuocTinhSanPhamService {
    thuoctinhsanphamdto createThuocTinhSanPham(thuoctinhsanphamdto thuocTinhSanPhamDto);
    thuoctinhsanphamdto getThuocTinhSanPhamById(Integer id);
    List<thuoctinhsanphamdto> getAllThuocTinhSanPham();
    thuoctinhsanphamdto updateThuocTinhSanPham(Integer id, thuoctinhsanphamdto thuocTinhSanPhamDto);
    void deleteThuocTinhSanPham(Integer id);
}