package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.ThuocTinhSanPhamDTO;

import java.util.List;

public interface ThuocTinhSanPhamService {
    List<ThuocTinhSanPhamDTO> getAllThuocTinhSanPhams();
    ThuocTinhSanPhamDTO getThuocTinhSanPhamById(Integer id);
    ThuocTinhSanPhamDTO createThuocTinhSanPham(ThuocTinhSanPhamDTO thuocTinhSanPhamDTO);
    ThuocTinhSanPhamDTO updateThuocTinhSanPham(Integer id, ThuocTinhSanPhamDTO thuocTinhSanPhamDTO);
    void deleteThuocTinhSanPham(Integer id);
}