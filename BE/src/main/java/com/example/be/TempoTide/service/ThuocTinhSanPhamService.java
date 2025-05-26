package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.ThuocTinhSanPhamDto;

import java.util.List;

public interface ThuocTinhSanPhamService {
    ThuocTinhSanPhamDto createThuocTinhSanPham(ThuocTinhSanPhamDto thuocTinhSanPhamDto);
    ThuocTinhSanPhamDto getThuocTinhSanPhamById(Integer id);
    List<ThuocTinhSanPhamDto> getAllThuocTinhSanPham();
    ThuocTinhSanPhamDto updateThuocTinhSanPham(Integer id, ThuocTinhSanPhamDto thuocTinhSanPhamDto);
    void deleteThuocTinhSanPham(Integer id);
}